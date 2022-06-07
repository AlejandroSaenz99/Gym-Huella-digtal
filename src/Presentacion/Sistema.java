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
import Datos.DMensaje;
import Datos.DPagos;
import Datos.DSegundos;
import Metodos.Metodos_Altas;
import Metodos.Metodos_Consulta_Rapida;
import static Metodos.Metodos_sql.resultado;
import Modelo.ModeloExcel;
import static Presentacion.AltaClientes.TEMPLATE_PROPERTY;
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
import com.toedter.calendar.JDateChooser;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import static java.lang.Thread.sleep;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author AlejandroSaenz
 */
public class Sistema extends javax.swing.JFrame {
    String FECHA="";
 Statement sentenciaSQL;
  int segundo;
  Timer timer;
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
     * Creates new form Sistema
     */
    public Sistema() {
         try {
         UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
         } catch (Exception e) {
         JOptionPane.showMessageDialog(null, "Imposible modificar el tema visual", "Lookandfeel inválido.",
         JOptionPane.ERROR_MESSAGE);
         }
       
        initComponents();
//        txtArea.setEditable(false);
     Mensaje();
     segundos();
       txttId.setVisible(false);
        FechaChooser.setDate(Fechaa());
  
        //consultar();
        ID.setText(""+obtenerIdAsistencia());
        bi.requestFocus();
        txtFecha.setText(Fecha());
        txtFecha.setEnabled(false);
        FechaChooser.setEnabled(false);
        
        //txtFecha.setDate(Fecha());
      //  txtFecha.setEnabled(false);
       // MostrarTablaAsistencia();
       //Fecha2.setDate(Fechaa());
       // TablaAsistencia.setVisible(true);
       //hora();
        setLocationRelativeTo(null);
    }
    
    
    public  void start(){
	Lector.startCapture();
	EnviarTexto("Utilizando el Lector de Huella Dactilar ");
}
public void EnviarTexto(String string) {
//        txtArea.append(string + "\n");
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
               JOptionPane.showMessageDialog(Sistema.this, "La Plantilla de la Huella no pudo ser creada, Repita el Proceso", "Inscripcion de Huellas Dactilares", JOptionPane.ERROR_MESSAGE);
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

public  void EstadoHuellas(){
	EnviarTexto("Plantillas necesarias para guardar una nueva huella : "+ Reclutador.getFeaturesNeeded());
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
          
           bi.setText(""+IDH);
           segundo=Integer.parseInt(Segundos.getText());
        lbMostrarImagen.setIcon(null);
Dias.setText("");
Estado.setText("Esperando...");
Fecha.setDate(null);
ProximoPago.setDate(null);
IdCliente.setText("");
txtNombre.setText("");
txtApellidos.setText("");
txtHora.setText("");
        try{
        txtFecha.setText(Fecha());



  
   
        IdCliente.setText(bi.getText()); 
        if (!this.bi.getText().isEmpty()) {
            
         
             this.lbMostrarImagen.setIcon((Icon)null);
     
     
      
      
      
      int clv = Integer.parseInt(this.bi.getText());
      ImageIcon img = (new PuenteAltas()).puenteObtenerImagen(clv);
      Icon icono = new ImageIcon(img.getImage().getScaledInstance(this.lbMostrarImagen.getWidth(), this.lbMostrarImagen.getHeight(), 1));
      if (img != null) {
        this.lbMostrarImagen.setIcon(icono);
      }
    DPagos Prov = new DPagos();
      Prov.setId(Integer.parseInt(this.bi.getText()));
      (new Metodos_Consulta_Rapida()).consultaFolio(Prov);
      this.Fecha.setDate(Prov.getFecha());
      this.ProximoPago.setDate(Prov.getProximo_Pago());
      
     
      DAltas prov = new DAltas();
      prov.setId(Integer.parseInt(this.bi.getText()));
      (new PuenteAltas()).PuenteBuscarCliente(prov);
      txtNombre.setText("" + prov.getNombre());
      txtApellidos.setText("" + prov.getApellidos());
     txtHora.setText(hora().toString());
      bi.setText("");
      
      bi.requestFocus();
       
      
      //**********************Guardar**************************
      
      DAsistencia prod = new DAsistencia();
      prod.setId(Integer.parseInt(ID.getText()));
      prod.setId_Cliente(Integer.parseInt(IdCliente.getText()));
      prod.setNombre(txtNombre.getText());
      prod.setApellidos(txtApellidos.getText());
      prod.setFecha(txtFecha.getText());
       prod.setHora(txtHora.getText());
      
      (new PuenteAltas()).PuenteRegistroAsistencia(prod);
     consultar();
    
     CalcularDias(FechaChooser,ProximoPago);  
     Noexiste.setText("");
  stop();
    ID.setText("" + obtenerIdAsistencia());
    
          
        
    } else {
      JOptionPane.showMessageDialog(null, "Ingresa un Id", "Atencion", 1);
    }}catch(Exception e){System.out.println(e);}

   
  simlpetimer();
        timer.start();
        bi.setEnabled(false);
    
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
       Sistema s=new Sistema();
       s.user.setText(user.getText());
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
    
     void MostrarTablaAsistencia(JTable TablaAsistencia,JTextField txtFecha) {
    (new PuenteAltas()).PuenteMostrarTablaAsistencia(TablaAsistencia,txtFecha);
  }
     public void Busqueda(){
     segundo=Integer.parseInt(Segundos.getText());
        lbMostrarImagen.setIcon(null);
Dias.setText("");
Estado.setText("Esperando...");
Fecha.setDate(null);
ProximoPago.setDate(null);
IdCliente.setText("");
txtNombre.setText("");
txtApellidos.setText("");
txtHora.setText("");
        try{
        txtFecha.setText(Fecha());



  
   
        IdCliente.setText(bi.getText()); 
        if (!this.bi.getText().isEmpty()) {
            
         
             this.lbMostrarImagen.setIcon((Icon)null);
     
     
      
      
      
      int clv = Integer.parseInt(this.bi.getText());
      ImageIcon img = (new PuenteAltas()).puenteObtenerImagen(clv);
      Icon icono = new ImageIcon(img.getImage().getScaledInstance(this.lbMostrarImagen.getWidth(), this.lbMostrarImagen.getHeight(), 1));
      if (img != null) {
        this.lbMostrarImagen.setIcon(icono);
      }
    DPagos Prov = new DPagos();
      Prov.setId(Integer.parseInt(this.bi.getText()));
      (new Metodos_Consulta_Rapida()).consultaFolio(Prov);
      this.Fecha.setDate(Prov.getFecha());
      this.ProximoPago.setDate(Prov.getProximo_Pago());
      
     
      DAltas prov = new DAltas();
      prov.setId(Integer.parseInt(this.bi.getText()));
      (new PuenteAltas()).PuenteBuscarCliente(prov);
      txtNombre.setText("" + prov.getNombre());
      txtApellidos.setText("" + prov.getApellidos());
     txtHora.setText(hora().toString());
      bi.setText("");
      
      bi.requestFocus();
       
      
      //**********************Guardar**************************
      
      DAsistencia prod = new DAsistencia();
      prod.setId(Integer.parseInt(ID.getText()));
      prod.setId_Cliente(Integer.parseInt(IdCliente.getText()));
      prod.setNombre(txtNombre.getText());
      prod.setApellidos(txtApellidos.getText());
      prod.setFecha(txtFecha.getText());
       prod.setHora(txtHora.getText());
      
      (new PuenteAltas()).PuenteRegistroAsistencia(prod);
     consultar();
    
     CalcularDias(FechaChooser,ProximoPago);  
     
    ID.setText("" + obtenerIdAsistencia());
    
          
        
    } else {
      JOptionPane.showMessageDialog(null, "Ingresa un Id", "Atencion", 1);
    }}catch(Exception e){System.out.println(e);}


  simlpetimer();
        timer.start();
        bi.setEnabled(false);
    
     }
     
  public static Date Fechaa() {
    Date FechaA = new Date();
    SimpleDateFormat FormatoFecha = new SimpleDateFormat("dd/MM/YYYY");
    return FechaA;
  }

 public String Fecha() {
    Date Fecha = new Date() {};
    SimpleDateFormat FormatoFecha = new SimpleDateFormat("dd/MM/YYYY");
    return FormatoFecha.format(Fecha);
  }
 public static String Mensaje(JLabel txttId,JLabel M) {
/*  68 */     String Mensaje1 = null;
/*  69 */     Connection conexion = null;
/*     */     try {
/*  71 */       ConexionBD con = new ConexionBD();
/*  72 */       String Sentencia_Buscar = "SELECT * FROM mensajetabla WHERE Id=  '" + txttId + "'";
/*     */       
/*  74 */       PreparedStatement sentencia_preparada = con.Conectarse().prepareStatement(Sentencia_Buscar);
/*  75 */       resultado = sentencia_preparada.executeQuery();
/*  76 */       if (resultado.next()) {
/*     */         
/*  78 */         int Id = resultado.getInt("Id");
/*  79 */         String Mensaje = resultado.getString("Mensaje");

/*  80 */         Mensaje1 = Mensaje;
/*     */       } 
/*  82 */       sentencia_preparada.close();
/*  83 */       conexion.close();
/*  84 */     } catch (Exception exception) {}
/*     */     
/*  86 */     return Mensaje1;
/*     */   }
 
 public LocalTime hora(){
     LocalTime hora= LocalTime.now();
        
        return hora;
 }
 public void Mensaje(){
     DMensaje prov = new DMensaje();
      prov.setId(Integer.parseInt(this.txttId.getText()));
      (new PuenteAltas()).PuenteBuscarMensaje(prov);
      M.setText("" + prov.getMensaje());
 }
 public void segundos(){
     DSegundos prov = new DSegundos();
      prov.setId(Integer.parseInt(this.segI.getText()));
      (new PuenteAltas()).PuenteBuscarsegundos(prov);
      Segundos.setText("" + prov.getSegundos());
 }
 public void CalcularDias(JDateChooser FechaChooser, JDateChooser ProximoPago){
     if (FechaChooser.getDate() != null && ProximoPago.getDate() != null) {
      Calendar inicio = FechaChooser.getCalendar();
      Calendar Termino = ProximoPago.getCalendar();
      int dias = 0;
     
        while (inicio.before(Termino) || inicio.equals(Termino)) {
          dias++;
         inicio.add(Calendar.DATE,1);          
        }  
        if(dias>=20){Estado.setForeground(Color.green); Estado.setText("Activo, su membresia vence en: +"+dias+"dias"); Dias.setForeground(Color.green);}
          if(dias>=7&&dias<=19){Estado.setForeground(Color.green); Estado.setText("Activo su membresia vence en: "+dias+"dias");Dias.setForeground(Color.green);}
          if(dias>=1&&dias<=6){Estado.setForeground(Color.yellow); Estado.setText("Atencion, su membresia vence en: "+dias+"dias");Dias.setForeground(Color.yellow);}
          if(dias<=0){Estado.setForeground(Color.red); Estado.setText("Vencido: porfavor pague su membresia");
          Dias.setForeground(Color.red);}
           Dias.setText(""+dias);
        System.out.println(dias);}}
 
 
 
 public void consultar(){
     txtFecha.setText("'"+Fecha()+"'");
        try {
    
       String sql = "SELECT * FROM asistencia WHERE Fecha=" +txtFecha.getText();
   
  
// String sql = "SELECT * FROM asistencia WHERE Fecha=" + Fecha.getText();
      this.con = new ConexionBD();
      this.sentenciaSQL = this.con.Conectarse().createStatement();
      ResultSet rs = this.sentenciaSQL.executeQuery(sql);
      ResultSetMetaData rsm = rs.getMetaData();
      int col = rsm.getColumnCount();
      DefaultTableModel modelo = new DefaultTableModel();
      for (int i = 1; i <= col; i++)
        modelo.addColumn(rsm.getColumnLabel(i)); 
      while (rs.next()) {
        String[] fila = new String[col];
        for (int j = 0; j < col; j++)
          fila[j] = rs.getString(j + 1); 
        modelo.addRow((Object[])fila);
      } 
      TablaAsistencia.setModel(modelo);
      rs.close();
      this.con.CerrarConexion();
    } catch (ClassNotFoundException ex) {
      JOptionPane.showMessageDialog(null, "Clase no encontrada..." + ex);
    } catch (InstantiationException ex) {
      JOptionPane.showMessageDialog(null, "Error de conexion..." + ex);
    } catch (IllegalAccessException ex) {
      JOptionPane.showMessageDialog(null, "Acceso ilegal a la base de datos..." + ex);
    } catch (SQLException ex) {
      JOptionPane.showMessageDialog(null, "Error de sentencia..." + ex);
    }
 }
 
 Metodos_Altas Metodos=new Metodos_Altas();
 
 private int obtenerIdAsistencia() {
     if(FechaChooser.getDate()==null){
    System.out.println("Valor nulo");
}
else{
try{
Date date=new Date();
       
     Date Variable =FechaChooser.getDate(); 
   SimpleDateFormat FormatoFecha = new SimpleDateFormat("dd/MM/YYYY");
       // System.out.println(Variable);
FormatoFecha.format(Variable);
        //System.out.println(FormatoFecha.format(Variable));
        FECHA="'"+FormatoFecha.format(Variable)+"'";
        //fecha.setText("'"+FECHA+"'");
}catch(Exception e){System.out.println(e);}}
     
    int f = this.Metodos.consultaIdAsistencia(FECHA);
    return f;
  }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jToolBar1 = new javax.swing.JToolBar();
        user = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        txtFecha = new javax.swing.JTextField();
        jSeparator4 = new javax.swing.JToolBar.Separator();
        FechaChooser = new com.toedter.calendar.JDateChooser();
        jSeparator3 = new javax.swing.JToolBar.Separator();
        jLabel2 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        ID = new javax.swing.JLabel();
        IdCliente = new javax.swing.JLabel();
        txtNombre = new javax.swing.JLabel();
        txtApellidos = new javax.swing.JLabel();
        txtHora = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        ProximoPago = new com.toedter.calendar.JDateChooser();
        Estado = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        Dias = new javax.swing.JLabel();
        lbMostrarImagen = new javax.swing.JLabel();
        Fecha = new com.toedter.calendar.JDateChooser();
        jLabel9 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        M = new javax.swing.JLabel();
        txttId = new javax.swing.JLabel();
        la = new javax.swing.JLabel();
        segI = new javax.swing.JLabel();
        Segundos = new javax.swing.JLabel();
        Noexiste = new javax.swing.JLabel();
        bi = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TablaAsistencia = new javax.swing.JTable();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem7 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jSeparator6 = new javax.swing.JPopupMenu.Separator();
        jMenuItem8 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem5 = new javax.swing.JMenuItem();
        jSeparator5 = new javax.swing.JPopupMenu.Separator();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenuItem9 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jToolBar1.setRollover(true);

        user.setFont(new java.awt.Font("Tahoma", 3, 14)); // NOI18N
        user.setText("Usuario");
        jToolBar1.add(user);
        jToolBar1.add(jSeparator1);
        jToolBar1.add(txtFecha);
        jToolBar1.add(jSeparator4);

        FechaChooser.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                FechaChooserPropertyChange(evt);
            }
        });
        jToolBar1.add(FechaChooser);
        jToolBar1.add(jSeparator3);

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/Salir_16.png"))); // NOI18N
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel2MouseClicked(evt);
            }
        });
        jToolBar1.add(jLabel2);

        jPanel1.setBackground(new java.awt.Color(0, 0, 0));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setText("Id:");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 340, -1, -1));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setText("Id Cliente:");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 360, -1, -1));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setText("Nombre:");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 390, -1, -1));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setText("Apellidos:");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 420, -1, -1));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setText("Hora:");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 460, -1, -1));

        ID.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jPanel1.add(ID, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 340, -1, -1));

        IdCliente.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jPanel1.add(IdCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 360, -1, -1));

        txtNombre.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jPanel1.add(txtNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 390, -1, -1));

        txtApellidos.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jPanel1.add(txtApellidos, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 420, -1, -1));

        txtHora.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jPanel1.add(txtHora, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 460, -1, -1));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255,220));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 250, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 160, Short.MAX_VALUE)
        );

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 330, 250, 160));

        ProximoPago.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jPanel1.add(ProximoPago, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 440, 200, 40));

        Estado.setBackground(new java.awt.Color(255, 255, 255));
        Estado.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        Estado.setForeground(new java.awt.Color(255, 255, 255));
        Estado.setText("Esperando...");
        jPanel1.add(Estado, new org.netbeans.lib.awtextra.AbsoluteConstraints(990, 320, -1, -1));

        jLabel10.setBackground(new java.awt.Color(255, 255, 255));
        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Estado:");
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 320, -1, -1));

        Dias.setFont(new java.awt.Font("Tahoma", 1, 250)); // NOI18N
        Dias.setForeground(new java.awt.Color(255, 255, 255));
        Dias.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Dias Restantes", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(255, 255, 255))); // NOI18N
        jPanel1.add(Dias, new org.netbeans.lib.awtextra.AbsoluteConstraints(980, 20, 350, 300));

        lbMostrarImagen.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Fotografia", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(255, 255, 255))); // NOI18N
        jPanel1.add(lbMostrarImagen, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 20, 390, 300));

        Fecha.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jPanel1.add(Fecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 380, 200, 40));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Proximo pago:");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 440, -1, -1));

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Ultimo pago:");
        jPanel1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 400, -1, -1));

        M.setFont(new java.awt.Font("Algerian", 1, 24)); // NOI18N
        M.setForeground(new java.awt.Color(218, 165, 32));
        M.setText("Bienvenidos a Gio Gym, donde entrenan los mejores!");
        jPanel1.add(M, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 510, 1320, 60));

        txttId.setText("1");
        jPanel1.add(txttId, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 10, -1, -1));

        la.setFont(new java.awt.Font("Algerian", 1, 100)); // NOI18N
        la.setForeground(new java.awt.Color(218, 165, 32));
        la.setText("0");
        jPanel1.add(la, new org.netbeans.lib.awtextra.AbsoluteConstraints(1090, 380, 140, 120));

        segI.setText("1");
        jPanel1.add(segI, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 190, 20, -1));

        Segundos.setText("0");
        jPanel1.add(Segundos, new org.netbeans.lib.awtextra.AbsoluteConstraints(1160, 30, -1, -1));

        Noexiste.setBackground(new java.awt.Color(255, 51, 51));
        Noexiste.setFont(new java.awt.Font("Colonna MT", 0, 48)); // NOI18N
        Noexiste.setForeground(new java.awt.Color(255, 51, 51));
        jPanel1.add(Noexiste, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 350, 440, 60));

        bi.setBackground(new java.awt.Color(0, 0, 0));
        bi.setForeground(new java.awt.Color(255, 255, 255));
        bi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                biActionPerformed(evt);
            }
        });
        jPanel1.add(bi, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 60, 30));

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/logo2.jpg"))); // NOI18N
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, 450, 470));

        jTabbedPane1.addTab("Información", jPanel1);

        jPanel2.setBackground(new java.awt.Color(0, 0, 0));

        TablaAsistencia.setModel(new javax.swing.table.DefaultTableModel(
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
        TablaAsistencia.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TablaAsistenciaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(TablaAsistencia);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1338, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 523, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(26, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Asistencia diaria", jPanel2);

        jMenu1.setText("Opciones");

        jMenuItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/agregar-usuario.png"))); // NOI18N
        jMenuItem1.setText("Agregar Cliente");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/billete-de-banco.png"))); // NOI18N
        jMenuItem2.setText("Agregar Pago");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);
        jMenu1.add(jSeparator2);

        jMenuItem3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/buscar.png"))); // NOI18N
        jMenuItem3.setText("Consulta Rapida");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem3);

        jMenuItem7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/informacion.png"))); // NOI18N
        jMenuItem7.setText("Mensaje");
        jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem7ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem7);

        jMenuItem4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/temporizador.png"))); // NOI18N
        jMenuItem4.setText("Temporizador");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem4);
        jMenu1.add(jSeparator6);

        jMenuItem8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/administrador.png"))); // NOI18N
        jMenuItem8.setText("Administrador");
        jMenuItem8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem8ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem8);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Ver");

        jMenuItem5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/exercise.png"))); // NOI18N
        jMenuItem5.setText("Clientes");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem5);
        jMenu2.add(jSeparator5);

        jMenuItem6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/Reportes_16.png"))); // NOI18N
        jMenuItem6.setText("Asistencias");
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem6);

        jMenuItem9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/Productos_16.png"))); // NOI18N
        jMenuItem9.setText("Reporte Pagos");
        jMenuItem9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem9ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem9);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1343, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
stop();
        Pagos Pago=new Pagos();
Pago.setVisible(true);
Pago.usu.setText(user.getText());
dispose();        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
AltaClientes Cliente=new AltaClientes();
Cliente.setVisible(true);
Cliente.txtRegistradoPor.setText(user.getText());
dispose();// TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseClicked
stop();
        Login lo=new Login();
lo.setVisible(true);
dispose();
// TODO add your handling code here:
    }//GEN-LAST:event_jLabel2MouseClicked
public void simlpetimer(){
timer =new Timer(1000, new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {

la.setText(""+segundo);
segundo--;
if(segundo==-1){ 
    
    start();
lbMostrarImagen.setIcon(null);
Dias.setText("");
Estado.setText("Esperando...");
Estado.setForeground(Color.white);
Fecha.setDate(null);
ProximoPago.setDate(null);
IdCliente.setText("");
txtNombre.setText("");
txtApellidos.setText("");
txtHora.setText("");

timer.stop();
segundo=Integer.parseInt(Segundos.getText());

        bi.setEnabled(true);
bi.requestFocus();}
bi.setText("");
    }
});
}
    private void TablaAsistenciaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TablaAsistenciaMouseClicked
if (evt.getButton() == 1) {
            int fila = TablaAsistencia.getSelectedRow();
            txtFecha.setText(TablaAsistencia.getValueAt(fila, 4).toString());
            
           // this.txtcontraseña.setText(this.TablaUsuario.getValueAt(fila, 4).toString());
            
            

        }         // TODO add your handling code here:
    }//GEN-LAST:event_TablaAsistenciaMouseClicked

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
Asistencias asi=new Asistencias();
asi.setVisible(true);
asi.u.setText(user.getText());
dispose();// TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem6ActionPerformed

    private void FechaChooserPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_FechaChooserPropertyChange
if(FechaChooser.getDate()==null){
    System.out.println("Valor nulo");
}
else{
try{
Date date=new Date();
       
     Date Variable =FechaChooser.getDate(); 
   SimpleDateFormat FormatoFecha = new SimpleDateFormat("dd/MM/YYYY");
       // System.out.println(Variable);
FormatoFecha.format(Variable);
        //System.out.println(FormatoFecha.format(Variable));
        FECHA="'"+FormatoFecha.format(Variable)+"'";
        System.out.println(FECHA);
        //fecha.setText("'"+FECHA+"'");
        ID.setText("" + obtenerIdAsistencia());
}
catch(Exception e){System.out.println(e);}
          try {
    
       String sql = "SELECT * FROM asistencia WHERE Fecha="+ FECHA;
   
  
// String sql = "SELECT * FROM asistencia WHERE Fecha=" + Fecha.getText();
      this.con = new ConexionBD();
      this.sentenciaSQL = this.con.Conectarse().createStatement();
      ResultSet rs = this.sentenciaSQL.executeQuery(sql);
      ResultSetMetaData rsm = rs.getMetaData();
      int col = rsm.getColumnCount();
      DefaultTableModel modelo = new DefaultTableModel();
      for (int i = 1; i <= col; i++)
        modelo.addColumn(rsm.getColumnLabel(i)); 
      while (rs.next()) {
        String[] fila = new String[col];
        for (int j = 0; j < col; j++)
          fila[j] = rs.getString(j + 1); 
        modelo.addRow((Object[])fila);
      } 
      TablaAsistencia.setModel(modelo);
      rs.close();
      this.con.CerrarConexion();
    } catch (ClassNotFoundException ex) {
      JOptionPane.showMessageDialog(null, "Clase no encontrada..." + ex);
    } catch (InstantiationException ex) {
      JOptionPane.showMessageDialog(null, "Error de conexion..." + ex);
    } catch (IllegalAccessException ex) {
      JOptionPane.showMessageDialog(null, "Acceso ilegal a la base de datos..." + ex);
    } catch (SQLException ex) {
      JOptionPane.showMessageDialog(null, "Error de sentencia..." + ex);
    }}  
    
    }//GEN-LAST:event_FechaChooserPropertyChange

    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem7ActionPerformed
Mesaje Me=new Mesaje();
Me.setVisible(true);
Me.U.setText(user.getText());
dispose();// TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem7ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
ConsultaRapida con=new ConsultaRapida();
con.setVisible(true);
con.nam.setText(user.getText());
dispose();
// TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
Clientes cli=new Clientes();
cli.setVisible(true);
cli.un.setText(user.getText());
dispose();// TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void jMenuItem8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem8ActionPerformed
Contraseña con=new Contraseña();
con.setVisible(true);
con.Usua.setText(user.getText());
dispose();
    }//GEN-LAST:event_jMenuItem8ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
segundos Me=new segundos();
Me.setVisible(true);
Me.usuar.setText(user.getText());
dispose();        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
Iniciar();
	start();
        EstadoHuellas();       // TODO add your handling code here:
    }//GEN-LAST:event_formWindowOpened

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
stop();        // TODO add your handling code here:
    }//GEN-LAST:event_formWindowClosed

    private void biActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_biActionPerformed
stop();
        Busqueda();        // TODO add your handling code here:
    }//GEN-LAST:event_biActionPerformed

    private void jMenuItem9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem9ActionPerformed
ModeloExcel modeloE = new ModeloExcel();
        InformePagos vistaE = new InformePagos();
        ControladorExcel contraControladorExcel = new ControladorExcel(vistaE, modeloE);
        vistaE.setVisible(true);
dispose();
                // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem9ActionPerformed

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
            java.util.logging.Logger.getLogger(Sistema.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Sistema.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Sistema.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Sistema.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Sistema().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Dias;
    private javax.swing.JLabel Estado;
    private com.toedter.calendar.JDateChooser Fecha;
    private com.toedter.calendar.JDateChooser FechaChooser;
    private javax.swing.JLabel ID;
    private javax.swing.JLabel IdCliente;
    public javax.swing.JLabel M;
    private javax.swing.JLabel Noexiste;
    private com.toedter.calendar.JDateChooser ProximoPago;
    private javax.swing.JLabel Segundos;
    private javax.swing.JTable TablaAsistencia;
    private javax.swing.JTextField bi;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JMenuItem jMenuItem9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JToolBar.Separator jSeparator3;
    private javax.swing.JToolBar.Separator jSeparator4;
    private javax.swing.JPopupMenu.Separator jSeparator5;
    private javax.swing.JPopupMenu.Separator jSeparator6;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JLabel la;
    private javax.swing.JLabel lbMostrarImagen;
    private javax.swing.JLabel segI;
    private javax.swing.JLabel txtApellidos;
    private javax.swing.JTextField txtFecha;
    private javax.swing.JLabel txtHora;
    private javax.swing.JLabel txtNombre;
    public javax.swing.JLabel txttId;
    public javax.swing.JLabel user;
    // End of variables declaration//GEN-END:variables
}
