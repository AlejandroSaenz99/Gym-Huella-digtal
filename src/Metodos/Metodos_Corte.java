package Metodos;

import Datos.ConexionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class Metodos_Corte {
  Statement sentenciaSQL;
  
  ConexionBD con;
  
  public static PreparedStatement sentencia_preparada;
  
  public static ResultSet resultado;
  
  public static String url;
  
  public static int Resultado_numero = 0;
  
  public JTable MostrarTabla(JTable TablaDatos, JTextField Id) {
    try {
      String sql = "SELECT * FROM pagos where Id=" + Id.getText();
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
  
  public JTable MostrarTabla(JTable TablaDatos) {
    try {
      String sql = "SELECT * FROM corte ";
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
  
  public int Guardar(int Id, String Fecha, Float Entrada, Float Salida, Float Total) {
    int resultado = 0;
    Connection conexion = null;
    String Sentencia_Guardar = "INSERT INTO corte (Id, Fecha, Entrada, Salida, Total) VALUES(?,?,?,?,?)";
    try {
      this.con = new ConexionBD();
      PreparedStatement sentencia_preparada = this.con.Conectarse().prepareStatement(Sentencia_Guardar);
      sentencia_preparada.setInt(1, Id);
      sentencia_preparada.setString(2, Fecha);
      sentencia_preparada.setFloat(3, Entrada.floatValue());
      sentencia_preparada.setFloat(4, Salida.floatValue());
      sentencia_preparada.setFloat(5, Total.floatValue());
      resultado = sentencia_preparada.executeUpdate();
      sentencia_preparada.close();
      conexion.close();
    } catch (Exception e) {
      System.out.println(e);
    } 
    return resultado;
  }
  
  public int consultaId() {
    int Id = 0;
    try {
      String sql = "SELECT * FROM corte";
      this.con = new ConexionBD();
      this.sentenciaSQL = this.con.Conectarse().createStatement();
      ResultSet rs = this.sentenciaSQL.executeQuery(sql);
      if (rs.last())
        Id = rs.getInt("Id"); 
      rs.close();
      this.con.CerrarConexion();
    } catch (ClassNotFoundException|InstantiationException|IllegalAccessException|SQLException ex) {
      JOptionPane.showMessageDialog(null, "Error por clase no encontrada, instancia, acceso ilegal y sentencia: " + ex, "Error", 0);
    } 
    return Id + 1;
  }
  
  public void Actualizar(int Id, String Fecha, Float Entrada, Float Salida, Float Total) {
    String sql = "UPDATE corte SET Id=?, Fecha=?,Entrada=?, Salida=?, Total=? WHERE Id=?";
    try {
      this.con = new ConexionBD();
      PreparedStatement sentencia_preparada = this.con.Conectarse().prepareStatement(sql);
      sentencia_preparada.setInt(1, Id);
      sentencia_preparada.setString(2, Fecha);
      sentencia_preparada.setFloat(3, Entrada.floatValue());
      sentencia_preparada.setFloat(4, Salida.floatValue());
      sentencia_preparada.setFloat(5, Total.floatValue());
      sentencia_preparada.setInt(6, Id);
      int n = sentencia_preparada.executeUpdate();
      if (n >= 0)
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
    } 
  }
  
  public void Eliminar(int Id, String Fecha, Float Entradas, Float Salidas, Float Total) {
    try {
      String sql = "DELETE FROM Corte WHERE Id=" + Id;
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
}
