/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Presentacion;
import Controlador.ControladorExcel;
import Datos.ConexionBD;
import Datos.DAltas;
import Datos.DAsistencia;
import Datos.DPagos;
import Metodos.Metodos_Consulta_Rapida;
import Metodos.Metodos_sql;
import Modelo.ModeloExcel;
import Presentacion.AñadirUsuario;
import static Presentacion.Sistema.TEMPLATE_PROPERTY;
import Puentes.PuenteAltas;
import com.digitalpersona.onetouch.DPFPDataPurpose;
import com.digitalpersona.onetouch.DPFPFeatureSet;
import com.digitalpersona.onetouch.DPFPGlobal;
import com.digitalpersona.onetouch.DPFPSample;
import com.digitalpersona.onetouch.DPFPTemplate;
import com.digitalpersona.onetouch.capture.DPFPCapture;
import com.digitalpersona.onetouch.capture.event.DPFPDataAdapter;
import com.digitalpersona.onetouch.capture.event.DPFPDataEvent;
import com.digitalpersona.onetouch.capture.event.DPFPErrorAdapter;
import com.digitalpersona.onetouch.capture.event.DPFPErrorEvent;
import com.digitalpersona.onetouch.capture.event.DPFPReaderStatusAdapter;
import com.digitalpersona.onetouch.capture.event.DPFPReaderStatusEvent;
import com.digitalpersona.onetouch.capture.event.DPFPSensorAdapter;
import com.digitalpersona.onetouch.capture.event.DPFPSensorEvent;
import com.digitalpersona.onetouch.processing.DPFPEnrollment;
import com.digitalpersona.onetouch.processing.DPFPFeatureExtraction;
import com.digitalpersona.onetouch.processing.DPFPImageQualityException;
import com.digitalpersona.onetouch.verification.DPFPVerification;
import com.digitalpersona.onetouch.verification.DPFPVerificationResult;
import java.awt.Image;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
/**
 *
 * @author AlejandroSaenz
 */
public class Login extends javax.swing.JFrame {
     //Varible que permite iniciar el dispositivo de lector de huella conectado
// con sus distintos metodos.
private DPFPCapture Lector = DPFPGlobal.getCaptureFactory().createCapture();

//Varible que permite establecer las capturas de la huellas, para determina sus caracteristicas
// y poder estimar la creacion de un template de la huella para luego poder guardarla
private DPFPEnrollment Reclutador = DPFPGlobal.getEnrollmentFactory().createEnrollment();

//Esta variable tambien captura una huella del lector y crea sus caracteristcas para auntetificarla
// o verificarla con alguna guardada en la BD
private DPFPVerification Verificador = DPFPGlobal.getVerificationFactory().createVerification();

//Variable que para crear el template de la huella luego de que se hallan creado las caracteriticas
// necesarias de la huella si no ha ocurrido ningun problema
private DPFPTemplate template;
public static String TEMPLATE_PROPERTY = "template";
    
  
  ConexionBD con;
  
  public static PreparedStatement sentencia_preparada;
  
  public static ResultSet resultado;
  
  public static String url;
  
  public static int Resultado_numero = 0;
    
    
    
Metodos_sql Metodos;
    /**
     * Creates new form Login
     */
    public Login() {
         this.Metodos = new Metodos_sql();
    initComponents();
            

    StateHold State=new StateHold("Usuario",txtCorreo);
    StateHold State2=new StateHold("Contraseña",txtContraseña);
    setLocationRelativeTo(null);
    }
public void Limpiar() {
    this.txtCorreo.setText("");
    this.txtContraseña.setText("");
    
  }
public void identificarHuella() throws IOException{
     try {
        
      this.con = new ConexionBD();
       //Establece los valores para la sentencia SQL
       Connection c=con.Conectarse();
         System.out.println(c);
       //Obtiene todas las huellas de la bd
        
       PreparedStatement identificarStmt = c.prepareStatement("SELECT * FROM usuarios");
     
       ResultSet rs = identificarStmt.executeQuery();

       //Si se encuentra el nombre en la base de datos
       while(rs.next()){
       //Lee la plantilla de la base de datos
       byte templateBuffer[] = rs.getBytes("huella");
       String Usuario=rs.getString("Usuario");
       String Contraseña=rs.getString("Contraseña");
       //Crea una nueva plantilla a partir de la guardada en la base de datos
       DPFPTemplate referenceTemplate = DPFPGlobal.getTemplateFactory().createTemplate(templateBuffer);
       //Envia la plantilla creada al objeto contendor de Template del componente de huella digital
       setTemplate(referenceTemplate);

       // Compara las caracteriticas de la huella recientemente capturda con la
       // alguna plantilla guardada en la base de datos que coincide con ese tipo
       DPFPVerificationResult result = Verificador.verify(featuresverificacion, getTemplate());

       //compara las plantilas (actual vs bd)
       //Si encuentra correspondencia dibuja el mapa
       //e indica el nombre de la persona que coincidió.
       if (result.isVerified()){
          // JOptionPane.showMessageDialog(null, "Cliente ya registrado, su Id es: "+IDH);
           txtCorreo.setText(Usuario);
           txtContraseña.setText(Contraseña);
     
           
/* 133 */       Sistema sis = new Sistema();
/* 134 */       String Busqueda_Usuario = Metodos_sql.Buscar_Usuario(this.txtCorreo.getText(), this.txtContraseña.getText());
/* 135 */       if (this.txtCorreo.getText().equals("root") && this.txtContraseña.getText().equals("root")) {
/* 136 */         Administrador admin = new Administrador();
/* 137 */     
JOptionPane.showMessageDialog(this, "Bienvenido a modo administrador");
/* 138 */         admin.setVisible(true);
/* 139 */         dispose();
/*     */       }
/* 141 */       else if (this.txtCorreo.getText().equals("creditos") && this.txtContraseña.getText().equals("creditos")) {
   JOptionPane.showMessageDialog(null, "Autor: Jesus Alejandro Saenz Saldivar\n Fecha de creacion: 06/07/2020\n Fecha de Remasterizacion(Codigo de barras): 27/02/2022\n Fecha de Remasterizacion(Lector de huella): 03/24/2022");
   ModeloExcel modeloE = new ModeloExcel();
        InformePagos vistaE = new InformePagos();
        ControladorExcel contraControladorExcel = new ControladorExcel(vistaE, modeloE);
        vistaE.setVisible(true);

        
      
     
      dispose();
/*     */       }
/* 144 */       else if (Busqueda_Usuario.equals("Usuario encontrado")) {
/* 145 */         String Busqueda_Nombre = Metodos_sql.Buscar_Nombre(this.txtCorreo.getText());
                    
/* 147 */    //     JOptionPane.showMessageDialog(this, "Welcome: \n" + Busqueda_Nombre);
/* 148 */       
        Sistema vistaE = new Sistema();
         
       
                
                
        vistaE.setVisible(true);
        vistaE.user.setText(""+Busqueda_Nombre);
        vistaE.setLocationRelativeTo(null);   
/* 149 */      
/* 150 */         dispose();
/*     */       } else {
/*     */         
/* 153 */         JOptionPane.showMessageDialog(this, "Contraseña Incorrecta","Intenta de nuevo",1);
 txtCorreo.requestFocus();
/* 154 */         Limpiar();
/*     */       } 
/*     */     stop();
           
       Reclutador.clear();
     
       setTemplate(null);
//       lblImagenHuella.setIcon(null);
       //start();
       return;
                               }
       }
       //Si no encuentra alguna huella correspondiente al nombre lo indica con un mensaje
       EnviarTexto("Cliente No Existe");
      JOptionPane.showMessageDialog(this, "Usuario no encontrado" );
     
     stop(); 
     start();
       setTemplate(null);
       } catch (SQLException e) {
       //Si ocurre un error lo indica en la consola
       System.err.println("Error al identificar huella dactilar."+e.getMessage());
       } catch(Exception e){System.out.println(e);}finally{
      // con.CerrarConexion();
       }
   }
 public  void start(){
	Lector.startCapture();
	EnviarTexto("Utilizando el Lector de Huella Dactilar ");
}
public void EnviarTexto(String string) {
//        txtArea.append(string + "\n");
} public  DPFPFeatureSet extraerCaracteristicas(DPFPSample sample, DPFPDataPurpose purpose){
     DPFPFeatureExtraction extractor = DPFPGlobal.getFeatureExtractionFactory().createFeatureExtraction();
     try {
      return extractor.createFeatureSet(sample, purpose);
     } catch (DPFPImageQualityException e) {
      return null;
     }
}
public  Image CrearImagenHuella(DPFPSample sample) {
	return DPFPGlobal.getSampleConversionFactory().createImage(sample);
}
 public void DibujarHuella(Image image) {
//        lblImagenHuella.setIcon(new ImageIcon(
  //      image.getScaledInstance(lblImagenHuella.getWidth(), lblImagenHuella.getHeight(), Image.SCALE_DEFAULT)));
        repaint();
}
 public DPFPTemplate getTemplate() {
    return template;
}

public  void EstadoHuellas(){
	EnviarTexto("Plantillas necesarias para guardar una nueva huella : "+ Reclutador.getFeaturesNeeded());
}
public DPFPFeatureSet featuresinscripcion;
 public DPFPFeatureSet featuresverificacion;
public  void ProcesarCaptura(DPFPSample sample){
    // Procesar la muestra de la huella y crear un conjunto de características con el propósito de inscripción.
    featuresinscripcion = extraerCaracteristicas(sample, DPFPDataPurpose.DATA_PURPOSE_ENROLLMENT);

    // Procesar la muestra de la huella y crear un conjunto de características con el propósito de verificacion.
    featuresverificacion = extraerCaracteristicas(sample, DPFPDataPurpose.DATA_PURPOSE_VERIFICATION);

    // Comprobar la calidad de la muestra de la huella y lo añade a su reclutador si es bueno
    if (featuresinscripcion != null)
        try{
        System.out.println("Las Caracteristicas de la Huella han sido creada");
        Reclutador.addFeatures(featuresinscripcion);// Agregar las caracteristicas de la huella a la plantilla a crear

        // Dibuja la huella dactilar capturada.
        Image image=CrearImagenHuella(sample);
        DibujarHuella(image);

        try {
           
            identificarHuella();
       
        } catch (IOException ex) {
            System.out.println(ex);
            Logger.getLogger(AltaClientes.class.getName()).log(Level.SEVERE, null, ex);
        }


        }catch (DPFPImageQualityException ex) {
        System.err.println("Error: "+ex.getMessage());
        }

        finally {
        EstadoHuellas();
        // Comprueba si la plantilla se ha creado.
           switch(Reclutador.getTemplateStatus())
           {
               case TEMPLATE_STATUS_READY:	// informe de éxito y detiene  la captura de huellas
               stop();
               setTemplate(Reclutador.getTemplate());
               EnviarTexto("La Plantilla de la Huella ha Sido Creada, ya puede Guardarla");
//               btnGuardar.setEnabled(true);
  //             btnGuardar.setText("Huella lista");
    //           btnGuardar.setBackground(Color.green);
      //         btnGuardar.grabFocus();
               break;

               case TEMPLATE_STATUS_FAILED: // informe de fallas y reiniciar la captura de huellas
               Reclutador.clear();
               stop();
               EstadoHuellas();
               setTemplate(null);
               JOptionPane.showMessageDialog(Login.this, "La Plantilla de la Huella no pudo ser creada, Repita el Proceso", "Inscripcion de Huellas Dactilares", JOptionPane.ERROR_MESSAGE);
               start();
               break;
           }
                }
}
protected void Iniciar(){
   Lector.addDataListener(new DPFPDataAdapter() {
    @Override public void dataAcquired(final DPFPDataEvent e) {
    SwingUtilities.invokeLater(new Runnable() {	public void run() {
   EnviarTexto("La Huella Digital ha sido Capturada");
    ProcesarCaptura(e.getSample());
    }});}
   });

   Lector.addReaderStatusListener(new DPFPReaderStatusAdapter() {
    @Override public void readerConnected(final DPFPReaderStatusEvent e) {
    SwingUtilities.invokeLater(new Runnable() {	public void run() {
    EnviarTexto("El Sensor de Huella Digital esta Activado o Conectado");
    }});}
    @Override public void readerDisconnected(final DPFPReaderStatusEvent e) {
    SwingUtilities.invokeLater(new Runnable() {	public void run() {
    EnviarTexto("El Sensor de Huella Digital esta Desactivado o no Conectado");
    }});}
   });

   Lector.addSensorListener(new DPFPSensorAdapter() {
    @Override public void fingerTouched(final DPFPSensorEvent e) {
    SwingUtilities.invokeLater(new Runnable() {	public void run() {
    EnviarTexto("El dedo ha sido colocado sobre el Lector de Huella");
    EnviarTexto("Realizando lectura dactilar...");
    }});}
    @Override public void fingerGone(final DPFPSensorEvent e) {
    SwingUtilities.invokeLater(new Runnable() {	public void run() {
    //EnviarTexto("El dedo ha sido quitado del Lector de Huella");
    }});}
   });

   Lector.addErrorListener(new DPFPErrorAdapter(){
    public void errorReader(final DPFPErrorEvent e){
    SwingUtilities.invokeLater(new Runnable() {  public void run() {
    EnviarTexto("Error: "+e.getError());
    }});}
   });
}

public  void stop(){
        Lector.stopCapture();
        //EnviarTexto("No se está usando el Lector de Huella Dactilar ");
}
public void setTemplate(DPFPTemplate template) {
        DPFPTemplate old = this.template;
	this.template = template;
	firePropertyChange(TEMPLATE_PROPERTY, old, template);
}
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel4 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtContraseña = new javax.swing.JPasswordField();
        jLabel3 = new javax.swing.JLabel();
        txtCorreo = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setFont(new java.awt.Font("Tahoma", 3, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Registrarse");
        jLabel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel4MouseClicked(evt);
            }
        });
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 400, 80, -1));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/contrasena.png"))); // NOI18N
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 360, 40, 30));

        txtContraseña.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtContraseñaActionPerformed(evt);
            }
        });
        getContentPane().add(txtContraseña, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 360, 210, 30));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/usuario-de-perfil.png"))); // NOI18N
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 310, 30, 30));

        txtCorreo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCorreoActionPerformed(evt);
            }
        });
        getContentPane().add(txtCorreo, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 310, 210, 30));

        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/logo2.jpg"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 450, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtCorreoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCorreoActionPerformed
txtContraseña.requestFocus();        // TODO add your handling code here:
    }//GEN-LAST:event_txtCorreoActionPerformed

    private void jLabel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseClicked
stop();
        AñadirUsuario Ventana=new AñadirUsuario();
Ventana.setVisible(true);
dispose();// TODO add your handling code here:
    }//GEN-LAST:event_jLabel4MouseClicked

    private void txtContraseñaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtContraseñaActionPerformed
  if (this.txtCorreo.getText().isEmpty() || this.txtContraseña.getText().isEmpty()) {
/* 130 */       JOptionPane.showMessageDialog(null, "Llene todos los datos", "Faltan datos", 1);
/*     */     } else {
/*     */       
/* 133 */       Sistema sis = new Sistema();
/* 134 */       String Busqueda_Usuario = Metodos_sql.Buscar_Usuario(this.txtCorreo.getText(), this.txtContraseña.getText());
/* 135 */       if (this.txtCorreo.getText().equals("root") && this.txtContraseña.getText().equals("root")) {
/* 136 */         Administrador admin = new Administrador();
/* 137 */     
JOptionPane.showMessageDialog(this, "Bienvenido a modo administrador");
/* 138 */         admin.setVisible(true);
/* 139 */         dispose();
/*     */       }
/* 141 */       else if (this.txtCorreo.getText().equals("creditos") && this.txtContraseña.getText().equals("creditos")) {
   JOptionPane.showMessageDialog(null, "Autor: Jesus Alejandro Saenz Saldivar\n Fecha de creacion: 06/07/2020\n Fecha de Remasterizacion(Codigo de barras): 27/02/2022\n Fecha de Remasterizacion(Lector de huella): 03/24/2022");
   ModeloExcel modeloE = new ModeloExcel();
        InformePagos vistaE = new InformePagos();
        ControladorExcel contraControladorExcel = new ControladorExcel(vistaE, modeloE);
        vistaE.setVisible(true);

        
      
     
/*     */       }
/* 144 */       else if (Busqueda_Usuario.equals("Usuario encontrado")) {
/* 145 */         String Busqueda_Nombre = Metodos_sql.Buscar_Nombre(this.txtCorreo.getText());
                    
/* 147 */    //     JOptionPane.showMessageDialog(this, "Welcome: \n" + Busqueda_Nombre);
/* 148 */       
        Sistema vistaE = new Sistema();
         
       
                stop();
                
        vistaE.setVisible(true);
        vistaE.user.setText(""+Busqueda_Nombre);
        vistaE.setLocationRelativeTo(null);   
/* 149 */      
/* 150 */         dispose();
/*     */       } else {
/*     */         
/* 153 */         JOptionPane.showMessageDialog(this, "Contraseña Incorrecta","Intenta de nuevo",1);
 txtCorreo.requestFocus();
/* 154 */         Limpiar();
/*     */       } 
/*     */     }     // TODO add your handling code here:
    }//GEN-LAST:event_txtContraseñaActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
Iniciar();
	start();
        EstadoHuellas();        // TODO add your handling code here:
    }//GEN-LAST:event_formWindowOpened

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
stop();        // TODO add your handling code here:
    }//GEN-LAST:event_formWindowClosed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Login().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPasswordField txtContraseña;
    private javax.swing.JTextField txtCorreo;
    // End of variables declaration//GEN-END:variables
}
