package Metodos;

import Datos.ConexionBD;
import Datos.DAltas;
import Datos.DAsistencia;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class Metodos_Altas {
  Statement sentenciaSQL;
  
  ConexionBD con;
  
  public static PreparedStatement sentencia_preparada;
  
  public static ResultSet resultado;
  
  public static String url;
  
  public static int Resultado_numero = 0;
  
  public int consultaId() {
    int Id = 0;
    try {
      String sql = "SELECT * FROM clientes Order by Id Desc limit 1";;
      this.con = new ConexionBD();
      this.sentenciaSQL = this.con.Conectarse().createStatement();
      ResultSet rs = this.sentenciaSQL.executeQuery(sql);
      if (rs.first())
        Id = rs.getInt("Id"); 
      rs.close();
      this.con.CerrarConexion();
    } catch (ClassNotFoundException|InstantiationException|IllegalAccessException|SQLException ex) {
      JOptionPane.showMessageDialog(null, "Error por clase no encontrada, instancia, acceso ilegal y sentencia: " + ex, "Error", 0);
    } 
    return Id + 1;
  }
   public int consultaIdAsistencia(String FECHA) {
    int Id = 0;
    try {
      String sql = "SELECT * FROM asistencia Where Fecha="+FECHA+"Order by Id Desc limit 1";
      this.con = new ConexionBD();
      this.sentenciaSQL = this.con.Conectarse().createStatement();
      ResultSet rs = this.sentenciaSQL.executeQuery(sql);
      if (rs.first())
        Id = rs.getInt("Id"); 
      rs.close();
      this.con.CerrarConexion();
    } catch (ClassNotFoundException|InstantiationException|IllegalAccessException|SQLException ex) {
      JOptionPane.showMessageDialog(null, "Error por clase no encontrada, instancia, acceso ilegal y sentencia: " + ex, "Error", 0);
    } 
    return Id + 1;
  }
  
  public DAltas Consulta(DAltas prov) {
    try {
      String sql = "SELECT Nombre, Apellidos FROM clientes WHERE Id=" + prov.getId();
      this.con = new ConexionBD();
      this.sentenciaSQL = this.con.Conectarse().createStatement();
      ResultSet rs = this.sentenciaSQL.executeQuery(sql);
      if (rs.next()) {
        prov.setNombre(rs.getString("Nombre"));
        prov.setApellidos(rs.getString("Apellidos"));
        JOptionPane.showMessageDialog(null, "Registro encontrado", "Aviso", 1);
      } else {
        JOptionPane.showMessageDialog(null, "Registro inexistente", "Aviso", 0);
      } 
      this.sentenciaSQL.close();
      this.con.CerrarConexion();
    } catch (ClassNotFoundException|InstantiationException|IllegalAccessException|SQLException ex) {
      JOptionPane.showMessageDialog(null, "Error por clase no encontrada, instancia, acceso ilegal y sentencia: " + ex, "Error", 0);
    } 
    return prov;
  }
  
  public DAltas Consulta2(DAltas prov) {
    try {
      String sql = "SELECT Nombre, Apellidos FROM clientes where Nombre='" + prov.getNombre() + "'AND Apellidos= '" + prov.getApellidos() + "'";
      this.con = new ConexionBD();
      this.sentenciaSQL = this.con.Conectarse().createStatement();
      ResultSet rs = this.sentenciaSQL.executeQuery(sql);
      if (rs.next()) {
        prov.setNombre(rs.getString("Nombre"));
        prov.setApellidos(rs.getString("Apellidos"));
        JOptionPane.showMessageDialog(null, "Registro encontrado", "Aviso", 1);
      } else {
        JOptionPane.showMessageDialog(null, "Registro inexistente", "Aviso", 0);
      } 
      this.sentenciaSQL.close();
      this.con.CerrarConexion();
    } catch (ClassNotFoundException|InstantiationException|IllegalAccessException|SQLException ex) {
      JOptionPane.showMessageDialog(null, "Error por clase no encontrada, instancia, acceso ilegal y sentencia: " + ex, "Error", 0);
    } 
    return prov;
  }
  
  public DAltas Consulta3(DAltas prov) {
    try {
      String sql = "SELECT Nombre, Apellidos FROM clientes where Nombre='" + prov.getNombre() + "'";
      this.con = new ConexionBD();
      this.sentenciaSQL = this.con.Conectarse().createStatement();
      ResultSet rs = this.sentenciaSQL.executeQuery(sql);
      if (rs.next()) {
        prov.setNombre(rs.getString("Nombre"));
        prov.setApellidos(rs.getString("Apellidos"));
        JOptionPane.showMessageDialog(null, "Registro encontrado", "Aviso", 1);
      } else {
        JOptionPane.showMessageDialog(null, "Registro inexistente", "Aviso", 0);
      } 
      this.sentenciaSQL.close();
      this.con.CerrarConexion();
    } catch (ClassNotFoundException|InstantiationException|IllegalAccessException|SQLException ex) {
      JOptionPane.showMessageDialog(null, "Error por clase no encontrada, instancia, acceso ilegal y sentencia: " + ex, "Error", 0);
    } 
    return prov;
  }
  
  public DAltas Consulta4(DAltas prov) {
    try {
      String sql = "SELECT Nombre, Apellidos FROM clientes where Apellidos='" + prov.getApellidos() + "'";
      this.con = new ConexionBD();
      this.sentenciaSQL = this.con.Conectarse().createStatement();
      ResultSet rs = this.sentenciaSQL.executeQuery(sql);
      if (rs.next()) {
        prov.setNombre(rs.getString("Nombre"));
        prov.setApellidos(rs.getString("Apellidos"));
        JOptionPane.showMessageDialog(null, "Registro encontrado", "Aviso", 1);
      } else {
        JOptionPane.showMessageDialog(null, "Registro inexistente", "Aviso", 0);
      } 
      this.sentenciaSQL.close();
      this.con.CerrarConexion();
    } catch (ClassNotFoundException|InstantiationException|IllegalAccessException|SQLException ex) {
      JOptionPane.showMessageDialog(null, "Error por clase no encontrada, instancia, acceso ilegal y sentencia: " + ex, "Error", 0);
    } 
    return prov;
  }
  public void GuardarAsistencia(DAsistencia pdto) {
    try {
      this.con = new ConexionBD();
      String insertar = "INSERT INTO asistencia (Id, Id_Cliente, Nombre, Apellidos,Fecha,Hora) VALUES(?,?,?,?,?,?)";
      
      PreparedStatement sentencia_preparada = this.con.Conectarse().prepareStatement(insertar);
      sentencia_preparada.setInt(1, pdto.getId());
      sentencia_preparada.setInt(2, pdto.getId_Cliente());
      sentencia_preparada.setString(3, pdto.getNombre());
      sentencia_preparada.setString(4, pdto.getApellidos());
      sentencia_preparada.setString(5, pdto.getFecha());
      sentencia_preparada.setString(6, pdto.getHora());
      int n = sentencia_preparada.executeUpdate();
      if (n > 0)
       // JOptionPane.showMessageDialog(null, "Registro Guardado", "Confirmacion", 1); 
      this.con.CerrarConexion();
    } catch (Exception e) {
        System.out.println(e); }
  }
  public int consultaAsistencia(JTextField txtFecha) {
    int ID = 0;
    try {
     // String sql = "SELECT * FROM viaje where Periodo=" + txtperiodo.getText();
            String sql = "SELECT * FROM asistencia where Fecha ='" + txtFecha.getText() ;
      this.con = new ConexionBD();
      this.sentenciaSQL = this.con.Conectarse().createStatement();
      ResultSet rs = this.sentenciaSQL.executeQuery(sql);
      if (rs.last())
        ID = rs.getInt("Id"); 
      rs.close();
      this.con.CerrarConexion();
    } catch (ClassNotFoundException|InstantiationException|IllegalAccessException|SQLException ex) {
      JOptionPane.showMessageDialog(null, "Error por clase no encontrada, instancia, acceso ilegal y sentencia: " + ex, "Error", 0);
    } 
    return ID + 1;
  }
  
  public void Guardar(DAltas pdto) {
    try {
      this.con = new ConexionBD();
      String insertar = "INSERT INTO clientes (Id, Nombre, Apellidos, Fecha,Registrado_Por,imagen) VALUES(?,?,?,?,?,?)";
      FileInputStream archivofoto = new FileInputStream(pdto.getImagen());
      PreparedStatement sentencia_preparada = this.con.Conectarse().prepareStatement(insertar);
      sentencia_preparada.setInt(1, pdto.getId());
      sentencia_preparada.setString(2, pdto.getNombre());
      sentencia_preparada.setString(3, pdto.getApellidos());
      sentencia_preparada.setString(4, pdto.getFecha());
      sentencia_preparada.setString(5, pdto.getRegistrado_Por());
      sentencia_preparada.setBinaryStream(6, archivofoto);
      int n = sentencia_preparada.executeUpdate();
      if (n > 0)
        JOptionPane.showMessageDialog(null, "Registro Guardado", "Confirmacion", 1); 
      this.con.CerrarConexion();
    } catch (ClassNotFoundException ex) {
      JOptionPane.showMessageDialog(null, "Clase no encontrada..." + ex);
    } catch (InstantiationException ex) {
      JOptionPane.showMessageDialog(null, "Error de conexion..." + ex);
    } catch (IllegalAccessException ex) {
      JOptionPane.showMessageDialog(null, "Acceso ilegal a la base de datos..." + ex);
    } catch (SQLException ex) {
      JOptionPane.showMessageDialog(null, "Error de sentencia..." + ex);
    } catch (FileNotFoundException ex) {
      Logger.getLogger(Metodos_Altas.class.getName()).log(Level.SEVERE, (String)null, ex);
    } 
  }
  
  public JTable MostrarTabla2(JTable TablaDatos) {
    try {
      String sql = "SELECT * FROM clientes ";
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
  
  public ImageIcon obtenerImagen(int id) throws SQLException {
    InputStream IS = null;
    ImageIcon II = null;
    String sql = "SELECT imagen FROM clientes WHERE Id=" + id;
    try {
      this.con = new ConexionBD();
      this.sentenciaSQL = this.con.Conectarse().createStatement();
      ResultSet rs = this.sentenciaSQL.executeQuery(sql);
      if (rs.next()) {
        IS = rs.getBinaryStream(1);
        BufferedImage bi = ImageIO.read(IS);
        II = new ImageIcon(bi);
      } 
    } catch (Exception ex) {
      JOptionPane.showMessageDialog(null, "Error: " + ex);
    } 
    this.con.CerrarConexion();
    this.sentenciaSQL.close();
    return II;
  }
  
  public ImageIcon obtenerImagen2(String Nombre, String Apellidos) throws SQLException {
    InputStream IS = null;
    ImageIcon II = null;
    String sql = "SELECT imagen FROM clientes WHERE Nombre='" + Nombre + "'AND Apellidos='" + Apellidos + "'";
    try {
      this.con = new ConexionBD();
      this.sentenciaSQL = this.con.Conectarse().createStatement();
      ResultSet rs = this.sentenciaSQL.executeQuery(sql);
      if (rs.next()) {
        IS = rs.getBinaryStream(1);
        BufferedImage bi = ImageIO.read(IS);
        II = new ImageIcon(bi);
      } 
    } catch (Exception ex) {
      JOptionPane.showMessageDialog(null, "Error: " + ex);
    } 
    this.con.CerrarConexion();
    this.sentenciaSQL.close();
    return II;
  }
  
  public ImageIcon obtenerImagen3(String Nombre) throws SQLException {
    InputStream IS = null;
    ImageIcon II = null;
    String sql = "SELECT imagen FROM clientes WHERE Nombre='" + Nombre + "'";
    try {
      this.con = new ConexionBD();
      this.sentenciaSQL = this.con.Conectarse().createStatement();
      ResultSet rs = this.sentenciaSQL.executeQuery(sql);
      if (rs.next()) {
        IS = rs.getBinaryStream(1);
        BufferedImage bi = ImageIO.read(IS);
        II = new ImageIcon(bi);
      } 
    } catch (Exception ex) {
      JOptionPane.showMessageDialog(null, "Error: " + ex);
    } 
    this.con.CerrarConexion();
    this.sentenciaSQL.close();
    return II;
  }
  
  public ImageIcon obtenerImagen4(String Apellidos) throws SQLException {
    InputStream IS = null;
    ImageIcon II = null;
    String sql = "SELECT imagen FROM clientes WHERE Apellidos='" + Apellidos + "'";
    try {
      this.con = new ConexionBD();
      this.sentenciaSQL = this.con.Conectarse().createStatement();
      ResultSet rs = this.sentenciaSQL.executeQuery(sql);
      if (rs.next()) {
        IS = rs.getBinaryStream(1);
        BufferedImage bi = ImageIO.read(IS);
        II = new ImageIcon(bi);
      } 
    } catch (Exception ex) {
      JOptionPane.showMessageDialog(null, "Error: " + ex);
    } 
    this.con.CerrarConexion();
    this.sentenciaSQL.close();
    return II;
  }
  
  public void Actualizar(DAltas pdto) {
    String sql = "UPDATE clientes SET Id=?,Nombre=?,Apellidos=?,Fecha=?,Registrado_Por=?,Imagen=? WHERE Id=?";
    try {
      FileInputStream archivofoto = new FileInputStream(pdto.getImagen());
      this.con = new ConexionBD();
      PreparedStatement pst = this.con.Conectarse().prepareStatement(sql);
      pst.setInt(1, pdto.getId());
      pst.setString(2, pdto.getNombre());
      pst.setString(3, pdto.getApellidos());
      pst.setString(4, pdto.getFecha());
      pst.setString(5, pdto.getRegistrado_Por());
      pst.setBinaryStream(6, archivofoto);
      pst.setInt(7, pdto.getId());
      int n = pst.executeUpdate();
      if (n > 0)
        JOptionPane.showMessageDialog(null, "Registro Modificado", "Confirmacion", 1); 
      this.con.CerrarConexion();
    } catch (ClassNotFoundException ex) {
      JOptionPane.showMessageDialog(null, "Clase no encontrada..." + ex);
    } catch (InstantiationException ex) {
      JOptionPane.showMessageDialog(null, "Error de conexion..." + ex);
    } catch (IllegalAccessException ex) {
      JOptionPane.showMessageDialog(null, "Acceso ilegal a la base de datos..." + ex);
    } catch (SQLException ex) {
      JOptionPane.showMessageDialog(null, "Error de sentencia..." + ex);
    } catch (FileNotFoundException ex) {
      JOptionPane.showMessageDialog(null, "error: debes cambiar la imagen para actualizar");
    } 
  }
  
  public void Eliminar(int Id) {
    try {
      String sql = "DELETE FROM clientes WHERE Id=" + Id;
      this.con = new ConexionBD();
      this.sentenciaSQL = this.con.Conectarse().createStatement();
      int n = this.sentenciaSQL.executeUpdate(sql);
      if (n > 0)
        JOptionPane.showMessageDialog(null, "Cliente Elimiando: ", "Confimacion", 1); 
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


public void EliminarHuella(int Id) {
    try {
      String sql = "DELETE FROM huellas WHERE Id=" + Id;
      this.con = new ConexionBD();
      this.sentenciaSQL = this.con.Conectarse().createStatement();
      int n = this.sentenciaSQL.executeUpdate(sql);
      if (n > 0)
        JOptionPane.showMessageDialog(null, "Huella eliminada: ", "Confimacion", 1); 
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
}
