package Metodos;

import Datos.ConexionBD;
import Datos.DAltas;
import Datos.DMensaje;
import Datos.DPagos;
import Datos.DSegundos;

import com.toedter.calendar.JDateChooser;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class Metodos_Pagos {
  Statement sentenciaSQL;
  
  ConexionBD con;
  
  public static PreparedStatement sentencia_preparada;
  
  public static ResultSet resultado;
  
  public static String url;
  
  public static int Resultado_numero = 0;
  
  public int Guardar(int Id, int Folio, String Nombre, String Apellidos, Float Cantidad, String Fecha, String Tipo) {
    int resultado = 0;
    Connection conexion = null;
    String Sentencia_Guardar = "INSERT INTO pagos (Id,Folio, Nombre, Apellidos,Cantidad,Fecha,Tipo) VALUES(?,?,?,?,?,?,?)";
    try {
      this.con = new ConexionBD();
      PreparedStatement sentencia_preparada = this.con.Conectarse().prepareStatement(Sentencia_Guardar);
      sentencia_preparada.setInt(1, Id);
      sentencia_preparada.setInt(2, Folio);
      sentencia_preparada.setString(3, Nombre);
      sentencia_preparada.setString(4, Apellidos);
      sentencia_preparada.setFloat(5, Cantidad.floatValue());
      sentencia_preparada.setString(6, Fecha);
      sentencia_preparada.setString(7, Tipo);
      resultado = sentencia_preparada.executeUpdate();
      JOptionPane.showMessageDialog(null, "Registro guardado");
      sentencia_preparada.close();
      conexion.close();
    } catch (Exception e) {
      System.out.println(e);
    } 
    return resultado;
  }
   public DAltas ConsultaClientes(DAltas prov) {
    try {
      String sql = "SELECT * FROM clientes WHERE Id=" + prov.getId();
      this.con = new ConexionBD();
      this.sentenciaSQL = this.con.Conectarse().createStatement();
      ResultSet rs = this.sentenciaSQL.executeQuery(sql);
      if (rs.next()) {
        prov.setNombre(rs.getString("Nombre"));
        prov.setApellidos(rs.getString("Apellidos"));
  

       // JOptionPane.showMessageDialog(null, "Datos encontrados", "Atencion", 1);
      } else {
        //JOptionPane.showMessageDialog(null, "Datos no encontrados", "Atencion", 0);
      } 
      this.sentenciaSQL.close();
      this.con.CerrarConexion();
    } catch (ClassNotFoundException|InstantiationException|IllegalAccessException|SQLException ex) {
      JOptionPane.showMessageDialog(null, "Error por clase no encontrada, instancia, acceso ilegal y sentencia: " + ex, "Error", 0);
    } 
    return prov;
   }
    
    public DMensaje ConsultaMensaje(DMensaje prov) {
    try {
      String sql = "SELECT * FROM mensajetabla WHERE Id=" + prov.getId();
      this.con = new ConexionBD();
      this.sentenciaSQL = this.con.Conectarse().createStatement();
      ResultSet rs = this.sentenciaSQL.executeQuery(sql);
      if (rs.next()) {
        prov.setId(rs.getInt("Id"));
        prov.setMensaje(rs.getString("Mensaje"));
  

       // JOptionPane.showMessageDialog(null, "Datos encontrados", "Atencion", 1);
      } else {
        //JOptionPane.showMessageDialog(null, "Datos no encontrados", "Atencion", 0);
      } 
      this.sentenciaSQL.close();
      this.con.CerrarConexion();
    } catch (ClassNotFoundException|InstantiationException|IllegalAccessException|SQLException ex) {
      JOptionPane.showMessageDialog(null, "Error por clase no encontrada, instancia, acceso ilegal y sentencia: " + ex, "Error", 0);
    } 
    return prov;
  }
   public DSegundos ConsultaSegundos(DSegundos prov) {
    try {
      String sql = "SELECT * FROM segundostabla WHERE id=" + prov.getId();
      this.con = new ConexionBD();
      this.sentenciaSQL = this.con.Conectarse().createStatement();
      ResultSet rs = this.sentenciaSQL.executeQuery(sql);
      if (rs.next()) {
        prov.setId(rs.getInt("id"));
        prov.setSegundos(rs.getInt("segundos"));
  

       // JOptionPane.showMessageDialog(null, "Datos encontrados", "Atencion", 1);
      } else {
        //JOptionPane.showMessageDialog(null, "Datos no encontrados", "Atencion", 0);
      } 
      this.sentenciaSQL.close();
      this.con.CerrarConexion();
    } catch (ClassNotFoundException|InstantiationException|IllegalAccessException|SQLException ex) {
      JOptionPane.showMessageDialog(null, "Error por clase no encontrada, instancia, acceso ilegal y sentencia: " + ex, "Error", 0);
    } 
    return prov;
  }
  public JTable MostrarTablaAsistencia(JTable TablaDatos, JTextField txtFecha) {
    try {int o=5;
    String sql="";
    if(o==1){
       sql = "SELECT * FROM asistencia WHERE Fecha=" + txtFecha.getText();
    }else{sql = "SELECT * FROM asistencia ";}
   
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
      TablaDatos.setModel(modelo);
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
    return TablaDatos;
  }
  
  public void Eliminar(int Id, int Folio) {
    try {
      String sql = "DELETE FROM pagos WHERE Id='" + Id + "'AND Folio=" + Folio;
      this.con = new ConexionBD();
      this.sentenciaSQL = this.con.Conectarse().createStatement();
      int n = this.sentenciaSQL.executeUpdate(sql);
      if (n > 0)
        JOptionPane.showMessageDialog(null, "Registro Eliminado: ", "Confimaci", 1); 
      this.con.CerrarConexion();
      this.sentenciaSQL.close();
    } catch (ClassNotFoundException ex) {
      JOptionPane.showMessageDialog(null, "Error 1: " + ex);
    } catch (InstantiationException ex) {
      JOptionPane.showMessageDialog(null, "Error 2: " + ex);
    } catch (IllegalAccessException ex) {
      JOptionPane.showMessageDialog(null, "Error 3: " + ex);
    } catch (SQLException ex) {
      JOptionPane.showMessageDialog(null, "Error 4: " + ex);
    } 
  }
  
  public JTable MostrarTabla(JTable TablaDatos) {
    try {
      String sql = "SELECT * FROM pagos ORDER BY Id ";
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
      TablaDatos.setModel(modelo);
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
    return TablaDatos;
  }
  public JTable MostrarUltimosPagos(JTable TablaDatos) {
    try {
      String sql = "SELECT * FROM pagos ";
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
      TablaDatos.setModel(modelo);
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
    return TablaDatos;
  }
  public void Guardar(DPagos pdto) {
/*     */     try {
/* 135 */       this.con = new ConexionBD();
/*     */ 
/*     */ 
/*     */       
/* 139 */       String insertar = "INSERT INTO pagos (Id,Folio, Nombre, Apellidos,Cantidad, Fecha,Tipo,Proximo_Pago) VALUES(?,?,?,?,?,?,?,?)";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 146 */       PreparedStatement sentencia_preparada = this.con.Conectarse().prepareStatement(insertar);
/* 147 */       sentencia_preparada.setInt(1, pdto.getId());
/* 148 */       sentencia_preparada.setInt(2, pdto.getFolio());
/* 149 */       sentencia_preparada.setString(3, pdto.getNombre());
/* 150 */       sentencia_preparada.setString(4, pdto.getApellidos());
/* 151 */       sentencia_preparada.setInt(5, pdto.getCantidad());
/* 152 */       sentencia_preparada.setDate(6, (Date)pdto.getFecha());
/* 153 */       sentencia_preparada.setString(7, pdto.getTipo());
/* 152 */       sentencia_preparada.setDate(8, (Date)pdto.getProximo_Pago());

/*     */ 
/*     */ 
/*     */       
/* 157 */       int n = sentencia_preparada.executeUpdate();
/* 158 */       if (n > 0)
/*     */       {
/* 160 */         JOptionPane.showMessageDialog(null, "Registro Guardado", "Confirmacion", 1);
/*     */       }
/* 162 */       this.con.CerrarConexion();
/* 163 */     } catch (ClassNotFoundException ex) {
/* 164 */       JOptionPane.showMessageDialog(null, "Clase no encontrada..." + ex);
/* 165 */     } catch (InstantiationException ex) {
/* 166 */       JOptionPane.showMessageDialog(null, "Error de conexion..." + ex);
/* 167 */     } catch (IllegalAccessException ex) {
/* 168 */       JOptionPane.showMessageDialog(null, "Acceso ilegal a la base de datos..." + ex);
/* 169 */     } catch (SQLException ex) {
/* 170 */       JOptionPane.showMessageDialog(null, "Error de sentencia..." + ex);
/*     */     } 
/*     */   }
  
  public void Eliminar2(int Id) {
    try {
      String sql = "DELETE FROM pagos WHERE Id=" + Id;
      this.con = new ConexionBD();
      this.sentenciaSQL = this.con.Conectarse().createStatement();
      int n = this.sentenciaSQL.executeUpdate(sql);
      if (n > 0)
        JOptionPane.showMessageDialog(null, "Registro Eliminado: ", "Confimaci", 1); 
      this.con.CerrarConexion();
      this.sentenciaSQL.close();
    } catch (ClassNotFoundException ex) {
      JOptionPane.showMessageDialog(null, "Error 1: " + ex);
    } catch (InstantiationException ex) {
      JOptionPane.showMessageDialog(null, "Error 2: " + ex);
    } catch (IllegalAccessException ex) {
      JOptionPane.showMessageDialog(null, "Error 3: " + ex);
    } catch (SQLException ex) {
      JOptionPane.showMessageDialog(null, "Error 4: " + ex);
    } 
  }
  
  public int consultaFolioU(JTextField Id) {
    int Folio = 0;
    try {
      String sql = "SELECT Id,Folio FROM pagos where Id=" + Id.getText();
        
      this.con = new ConexionBD();
      this.sentenciaSQL = this.con.Conectarse().createStatement();
      ResultSet rs = this.sentenciaSQL.executeQuery(sql);
      
      if (rs.last())
        Folio = rs.getInt("Folio"); 
      rs.close();
      this.con.CerrarConexion();
    } catch (ClassNotFoundException|InstantiationException|IllegalAccessException|SQLException ex) {
      JOptionPane.showMessageDialog(null, "Error por clase no encontrada, instancia, acceso ilegal y sentencia: " + ex, "Error", 0);
    } 
    return Folio + 1;
  }

   
}
