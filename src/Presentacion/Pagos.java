/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Presentacion;

import Datos.ConexionBD;
import Datos.DAltas;
import Datos.DAsistencia;
import Datos.DPagos;
import Metodos.Metodos_Consulta_Rapida;
import Metodos.Metodos_Pagos;
import static Presentacion.Sistema.TEMPLATE_PROPERTY;
import Puentes.PuenteAltas;
import Puentes.PuentePagos;
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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 *
 * @author AlejandroSaenz
 */
public class Pagos extends javax.swing.JFrame {
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
    /**
     * Creates new form Pagos
     */
    public Pagos() {
        try {
         UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
         } catch (Exception e) {
         JOptionPane.showMessageDialog(null, "Imposible modificar el tema visual", "Lookandfeel inválido.",
         JOptionPane.ERROR_MESSAGE);
         }
        initComponents();
        setLocationRelativeTo(null);
        MostrarTabla();
    }
void MostrarTabla() {
    (new PuenteAltas()).PuenteMostrarTabla(this.TablaDatosP);
  }
void MostrarUltimosPagos() {
    (new PuenteAltas()).PuenteMostrarUltimosPagos(this.TablaDatosP);
  }
  public static Date Fecha() {
    Date FechaA = new Date();
    SimpleDateFormat FormatoFecha = new SimpleDateFormat("MM/dd/YYYY");
    return FechaA;
  }

  private int obtenerFolioU(String Id) {
    Metodos_Pagos Metodos = new Metodos_Pagos();
    int f = Metodos.consultaFolioU(this.txtId);
    return f;
  }
  public void Limpiar() {
    this.txtFolio.setText("");
    this.txtId.setText("");
    this.txtNombre.setText("");
    this.txtApellidos.setText("");
    this.txtCantidad.setText("");
    this.Fecha.setDate(null);
    Fecha1.setDate(null);
    lbMostrarImagen.setIcon(null);
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
  
   public  void start(){
	Lector.startCapture();
	EnviarTexto("Utilizando el Lector de Huella Dactilar ");
}
   public  void EstadoHuellas(){
	EnviarTexto("Plantillas necesarias para guardar una nueva huella : "+ Reclutador.getFeaturesNeeded());
}
   public void EnviarTexto(String string) {
//        txtArea.append(string + "\n");
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
               JOptionPane.showMessageDialog(Pagos.this, "La Plantilla de la Huella no pudo ser creada, Repita el Proceso", "Inscripcion de Huellas Dactilares", JOptionPane.ERROR_MESSAGE);
               start();
               break;
           }
                }
}
   public  DPFPFeatureSet extraerCaracteristicas(DPFPSample sample, DPFPDataPurpose purpose){
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
 public  void stop(){
        Lector.stopCapture();
        //EnviarTexto("No se está usando el Lector de Huella Dactilar ");
}
public void setTemplate(DPFPTemplate template) {
        DPFPTemplate old = this.template;
	this.template = template;
	firePropertyChange(TEMPLATE_PROPERTY, old, template);
}
public void identificarHuella() throws IOException{
     try {
        
      this.con = new ConexionBD();
       //Establece los valores para la sentencia SQL
       Connection c=con.Conectarse();
         System.out.println(c);
       //Obtiene todas las huellas de la bd
        
       PreparedStatement identificarStmt = c.prepareStatement("SELECT * FROM huellas");
     
       ResultSet rs = identificarStmt.executeQuery();

       //Si se encuentra el nombre en la base de datos
       while(rs.next()){
       //Lee la plantilla de la base de datos
       byte templateBuffer[] = rs.getBytes("huehuella");
       int IDH=rs.getInt("id");
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
           System.out.println(IDH);
          Noexiste.setText("");
           txtId.setText(""+IDH);
           
        lbMostrarImagen.setIcon(null);
        
       

try{       if (!this.txtId.getText().isEmpty()) {
            this.lbMostrarImagen.setIcon((Icon)null);
     
     
      
      DAltas prov1 = new DAltas();
      prov1.setId(Integer.parseInt(this.txtId.getText()));
     // (new PuenteAltas()).PuenteBuscar(prov);
      
      int clv = Integer.parseInt(this.txtId.getText());
      ImageIcon img = (new PuenteAltas()).puenteObtenerImagen(clv);
      Icon icono = new ImageIcon(img.getImage().getScaledInstance(this.lbMostrarImagen.getWidth(), this.lbMostrarImagen.getHeight(), 1));
      if (img != null) {
        this.lbMostrarImagen.setIcon(icono);
        
      this.txtFolio.setText("" + obtenerFolioU(this.txtId.getText()));
      DAltas prov = new DAltas();
      prov.setId(Integer.parseInt(this.txtId.getText()));
      (new PuenteAltas()).PuenteBuscar(prov);
      this.txtNombre.setText("" + prov.getNombre());
      this.txtApellidos.setText("" + prov.getApellidos());
      txtCantidad.requestFocus();
    } else {
      JOptionPane.showMessageDialog(null, "Debes introducir un Numero de control", "Aviso", 1);
    }    }}catch(Exception e){System.out.println(e); JOptionPane.showMessageDialog(null, "Cliente no encontrado");} 

  
   
               

   
  
    
       //Envia el registro al servidor
       //apiEnviarRegistro(nombre);
       Reclutador.clear();
     
       setTemplate(null);
//       lblImagenHuella.setIcon(null);
       //start();
       return;
                               }
       }
       //Si no encuentra alguna huella correspondiente al nombre lo indica con un mensaje
       EnviarTexto("Cliente No Existe");
       stop();
       Pagos s=new Pagos();
       s.usu.setText(usu.getText());
      s.setVisible(true);
      s.Noexiste.setText("Huella no encontrada");
      this.dispose();
      
       setTemplate(null);
       } catch (SQLException e) {
       //Si ocurre un error lo indica en la consola
       System.err.println("Error al identificar huella dactilar."+e.getMessage());
       } catch(Exception e){System.out.println(e);}finally{
      // con.CerrarConexion();
       }
   }


public DPFPTemplate getTemplate() {
    return template;
}
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        TablaDatosP = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        Noexiste = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        txtFolio = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JLabel();
        txtApellidos = new javax.swing.JLabel();
        txtId = new javax.swing.JTextField();
        Combito = new javax.swing.JComboBox<>();
        txtCantidad = new javax.swing.JTextField();
        Fecha = new com.toedter.calendar.JDateChooser();
        jButton3 = new javax.swing.JButton();
        Fecha1 = new com.toedter.calendar.JDateChooser();
        jLabel11 = new javax.swing.JLabel();
        lbMostrarImagen = new javax.swing.JLabel();
        usu = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();

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

        TablaDatosP.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(TablaDatosP);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 30, 590, 420));

        jButton2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton2.setText("Regresar");
        jButton2.setToolTipText("Regresar a la ventana principal");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(1180, 460, -1, -1));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("Pagos Guardados");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 10, -1, -1));

        Noexiste.setBackground(new java.awt.Color(255, 51, 51));
        Noexiste.setFont(new java.awt.Font("Segoe UI Semibold", 0, 24)); // NOI18N
        Noexiste.setForeground(new java.awt.Color(255, 51, 51));
        getContentPane().add(Noexiste, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 420, 410, 60));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255,254));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtFolio.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jPanel1.add(txtFolio, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 200, -1, -1));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setText("Id:");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 30, -1, -1));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setText("Nombre:");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 90, -1, -1));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setText("Apellidos:");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 140, -1, -1));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setText("Folio:");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 200, -1, -1));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel8.setText("Tipo:");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 240, -1, -1));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel9.setText("Cantidad:");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 280, -1, -1));

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton1.setText("Registrar Pago");
        jButton1.setToolTipText("Guardar pago");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 410, -1, -1));

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel10.setText("Fecha de pago:");
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 320, -1, -1));

        txtNombre.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jPanel1.add(txtNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 90, -1, -1));

        txtApellidos.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jPanel1.add(txtApellidos, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 140, -1, -1));

        txtId.setToolTipText("Ingrese Id del cliente y despues de un enter.");
        txtId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdActionPerformed(evt);
            }
        });
        txtId.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtIdKeyTyped(evt);
            }
        });
        jPanel1.add(txtId, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 30, 100, -1));

        Combito.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        Combito.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Dia", "Semana", "Mes" }));
        Combito.setToolTipText("Seleccione el tipo de pago(dia, semana, mes)");
        Combito.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                CombitoMouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                CombitoMouseReleased(evt);
            }
        });
        Combito.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CombitoActionPerformed(evt);
            }
        });
        Combito.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                CombitoPropertyChange(evt);
            }
        });
        Combito.addVetoableChangeListener(new java.beans.VetoableChangeListener() {
            public void vetoableChange(java.beans.PropertyChangeEvent evt)throws java.beans.PropertyVetoException {
                CombitoVetoableChange(evt);
            }
        });
        jPanel1.add(Combito, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 240, 100, -1));

        txtCantidad.setToolTipText("Ingrese la cantidad pagada");
        txtCantidad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCantidadKeyTyped(evt);
            }
        });
        jPanel1.add(txtCantidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 280, 140, -1));
        jPanel1.add(Fecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 320, 140, -1));

        jButton3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton3.setText("Obtener Fecha");
        jButton3.setToolTipText("Establecer la fecha de hoy");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 320, -1, -1));
        jPanel1.add(Fecha1, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 350, 140, -1));

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel11.setText("Proximo pago:");
        jPanel1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 350, -1, -1));
        jPanel1.add(lbMostrarImagen, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 30, 300, 200));

        usu.setText("Usuario");
        jPanel1.add(usu, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 0, 310, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, 590, 460));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/imgÑ.jpg"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 640, 500));

        jButton4.setText("Ultimos Pagos");
        jButton4.setToolTipText("Coloca los ultimos pagos al fondo de la tabla");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(1180, 0, -1, -1));

        jButton5.setText("Ordenar");
        jButton5.setToolTipText("Coloca los pagos de manera ordenada por Id");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 0, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdActionPerformed
try{       if (!this.txtId.getText().isEmpty()) {
            this.lbMostrarImagen.setIcon((Icon)null);
     
     
      
      DAltas prov1 = new DAltas();
      prov1.setId(Integer.parseInt(this.txtId.getText()));
     // (new PuenteAltas()).PuenteBuscar(prov);
      
      int clv = Integer.parseInt(this.txtId.getText());
      ImageIcon img = (new PuenteAltas()).puenteObtenerImagen(clv);
      Icon icono = new ImageIcon(img.getImage().getScaledInstance(this.lbMostrarImagen.getWidth(), this.lbMostrarImagen.getHeight(), 1));
      if (img != null) {
        this.lbMostrarImagen.setIcon(icono);
        
      this.txtFolio.setText("" + obtenerFolioU(this.txtId.getText()));
      DAltas prov = new DAltas();
      prov.setId(Integer.parseInt(this.txtId.getText()));
      (new PuenteAltas()).PuenteBuscar(prov);
      this.txtNombre.setText("" + prov.getNombre());
      this.txtApellidos.setText("" + prov.getApellidos());
      txtCantidad.requestFocus();
    } else {
      JOptionPane.showMessageDialog(null, "Debes introducir un Numero de control", "Aviso", 1);
    }    }}catch(Exception e){System.out.println(e); JOptionPane.showMessageDialog(null, "Cliente no encontrado");}   
    }//GEN-LAST:event_txtIdActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
Fecha.setDate(Fecha()) ;       // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
       if (this.Fecha1.getDate() == null ||this.txtId.getText().isEmpty() || this.txtFolio.getText().isEmpty() || this.txtNombre.getText().isEmpty() || this.txtApellidos.getText().isEmpty() || this.txtCantidad.getText().isEmpty() || this.Fecha.getDate() == null) {
      JOptionPane.showMessageDialog(null, "llenar todos los datos", "falta de datos", 1);
    } else {
      DPagos prod = new DPagos();
      prod.setId(Integer.parseInt(this.txtId.getText()));
      prod.setNombre(this.txtNombre.getText());
      prod.setApellidos(this.txtApellidos.getText());
      prod.setFolio(Integer.parseInt(this.txtFolio.getText()));
      prod.setCantidad(Integer.parseInt(this.txtCantidad.getText()));
      prod.setTipo(this.Combito.getSelectedItem().toString());
        Date fecha=Fecha.getDate();
        long d=fecha.getTime();
        java.sql.Date soloFecha=new java.sql.Date(d);
        prod.setFecha(soloFecha);
        
        Date fecha1=Fecha1.getDate();
        long d1=fecha1.getTime();
        java.sql.Date soloFecha1=new java.sql.Date(d1);
        prod.setProximo_Pago(soloFecha1);
        
      (new PuentePagos()).PuenteRegistro(prod);
      MostrarTabla();
      Limpiar();
    } 
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
stop();
        Sistema si=new Sistema();
si.setVisible(true);
si.user.setText(usu.getText());
dispose();
// TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void txtIdKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIdKeyTyped
char car = evt.getKeyChar();
        if (car < '0' || car > '9')
        evt.consume();          // TODO add your handling code here:
    }//GEN-LAST:event_txtIdKeyTyped

    private void txtCantidadKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantidadKeyTyped
char car = evt.getKeyChar();
        if (car < '0' || car > '9')
        evt.consume();          // TODO add your handling code here:
    }//GEN-LAST:event_txtCantidadKeyTyped

    private void CombitoPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_CombitoPropertyChange
// TODO add your handling code here:
    }//GEN-LAST:event_CombitoPropertyChange

    private void CombitoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CombitoMouseClicked
      // TODO add your handling code here:
    }//GEN-LAST:event_CombitoMouseClicked

    private void CombitoMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CombitoMouseReleased
       // TODO add your handling code here:
    }//GEN-LAST:event_CombitoMouseReleased

    private void CombitoVetoableChange(java.beans.PropertyChangeEvent evt)throws java.beans.PropertyVetoException {//GEN-FIRST:event_CombitoVetoableChange
        // TODO add your handling code here:
    }//GEN-LAST:event_CombitoVetoableChange

    private void CombitoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CombitoActionPerformed
Fecha1.setDate(null);        // TODO add your handling code here:
    }//GEN-LAST:event_CombitoActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
stop();        // TODO add your handling code here:
    }//GEN-LAST:event_formWindowClosed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
Iniciar();
	start();
        EstadoHuellas();         // TODO add your handling code here:
    }//GEN-LAST:event_formWindowOpened

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
MostrarUltimosPagos();   // TODO add your handling code here:
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
MostrarTabla();        // TODO add your handling code here:
    }//GEN-LAST:event_jButton5ActionPerformed

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
            java.util.logging.Logger.getLogger(Pagos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Pagos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Pagos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Pagos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Pagos().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> Combito;
    private com.toedter.calendar.JDateChooser Fecha;
    private com.toedter.calendar.JDateChooser Fecha1;
    private javax.swing.JLabel Noexiste;
    private javax.swing.JTable TablaDatosP;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbMostrarImagen;
    private javax.swing.JLabel txtApellidos;
    private javax.swing.JTextField txtCantidad;
    private javax.swing.JLabel txtFolio;
    private javax.swing.JTextField txtId;
    private javax.swing.JLabel txtNombre;
    public javax.swing.JLabel usu;
    // End of variables declaration//GEN-END:variables
}
