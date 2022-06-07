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
import javax.swing.table.DefaultTableModel;

public class Metodos_sql {
  Statement sentenciaSQL;
  
  ConexionBD con;
  
  public static PreparedStatement sentencia_preparada;
  
  public static ResultSet resultado;
  
  public static String url;
  
  public static int Resultado_numero = 0;
  
   public int Guardar(int Id, String Nombre, String Apellidos, String Usuario, String Contraseña) {
    int resultado = 0;
    Connection conexion = null;
    String Sentencia_Guardar = "INSERT INTO usuarios (Id, Nombre, Apellidos, Usuario, Contraseña)VALUES(?,?,?,?,?) ";
    try {
      this.con = new ConexionBD();
      PreparedStatement sentencia_preparada = this.con.Conectarse().prepareStatement(Sentencia_Guardar);
      sentencia_preparada.setInt(1, Id);
      sentencia_preparada.setString(2, Nombre);
      sentencia_preparada.setString(3, Apellidos);
      sentencia_preparada.setString(4, Usuario);
      sentencia_preparada.setString(5, Contraseña);
      resultado = sentencia_preparada.executeUpdate();
      JOptionPane.showMessageDialog(null, "Welcome " + Nombre);
      sentencia_preparada.close();
      conexion.close();
    } catch (Exception e) {
      System.out.println(e);
    } 
    return resultado;
  }
  
  public static String Buscar_Nombre(String Usuario) {
/*  68 */     String Busqueda_Nombre = null;
/*  69 */     Connection conexion = null;
/*     */     try {
/*  71 */       ConexionBD con = new ConexionBD();
/*  72 */       String Sentencia_Buscar = "SELECT Nombre,Apellidos, Usuario,Contraseña FROM usuarios WHERE Usuario=  '" + Usuario + "'";
/*     */       
/*  74 */       PreparedStatement sentencia_preparada = con.Conectarse().prepareStatement(Sentencia_Buscar);
/*  75 */       resultado = sentencia_preparada.executeQuery();
/*  76 */       if (resultado.next()) {
/*     */         
/*  78 */         String Nombre = resultado.getString("Nombre");
/*  79 */         String Apellidos = resultado.getString("Apellidos");
/*  80 */         Busqueda_Nombre = Nombre + " " + Apellidos;
/*     */       } 
/*  82 */       sentencia_preparada.close();
/*  83 */       conexion.close();
/*  84 */     } catch (Exception exception) {}
/*     */     
/*  86 */     return Busqueda_Nombre;
/*     */   }
  
  public static String Buscar_User(String Usuario) {
/*  99 */     String Busqueda_Usuario = null;
/* 100 */     Connection conexion = null;
/*     */     try {
/* 102 */       ConexionBD con = new ConexionBD();
/* 103 */       String Sentencia_Buscar_Usuario = "SELECT Usuario FROM usuarios WHERE Usuario='" + Usuario +"'";
/*     */       
/* 105 */       PreparedStatement sentencia_preparada = con.Conectarse().prepareStatement(Sentencia_Buscar_Usuario);
/* 106 */       resultado = sentencia_preparada.executeQuery();
/* 107 */       if (resultado.next()) {
/*     */         
/* 109 */         Busqueda_Usuario = "Usuario ya existe";
/*     */       } else {
/*     */         
/* 112 */         Busqueda_Usuario = "Usuario no existe";
/*     */       } 
/* 114 */       sentencia_preparada.close();
/* 115 */       conexion.close();
/* 116 */     } catch (Exception e) {
/* 117 */       System.out.println(e);
/*     */     } 
/* 119 */     return Busqueda_Usuario;
/*     */   }
  
  public static String Buscar_Usuario(String Usuario, String Contraseña) {
/*  99 */     String Busqueda_Usuario = null;
/* 100 */     Connection conexion = null;
/*     */     try {
/* 102 */       ConexionBD con = new ConexionBD();
/* 103 */       String Sentencia_Buscar_Usuario = "SELECT Nombre, Apellidos FROM usuarios WHERE Usuario='" + Usuario + "'&& Contraseña= '" + Contraseña + "'";
/*     */       
/* 105 */       PreparedStatement sentencia_preparada = con.Conectarse().prepareStatement(Sentencia_Buscar_Usuario);
/* 106 */       resultado = sentencia_preparada.executeQuery();
/* 107 */       if (resultado.next()) {
/*     */         
/* 109 */         Busqueda_Usuario = "Usuario encontrado";
/*     */       } else {
/*     */         
/* 112 */         Busqueda_Usuario = "Usuario no encontrado";
/*     */       } 
/* 114 */       sentencia_preparada.close();
/* 115 */       conexion.close();
/* 116 */     } catch (Exception e) {
/* 117 */       System.out.println(e);
/*     */     } 
/* 119 */     return Busqueda_Usuario;
/*     */   }
  
  public void Actualizar(int Id, String Nombre, String Apellidos, String Usuario, String Contrase) {
    String sql = "UPDATE usuarios SET Id=?, Nombre=?,Apellidos=?, Usuario=?, Contraseña=? WHERE Id=?";
    try {
      this.con = new ConexionBD();
      PreparedStatement sentencia_preparada = this.con.Conectarse().prepareStatement(sql);
      sentencia_preparada.setInt(1, Id);
      sentencia_preparada.setString(2, Nombre);
      sentencia_preparada.setString(3, Apellidos);
      sentencia_preparada.setString(4, Usuario);
      sentencia_preparada.setString(5, Contrase);
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
  
  public JTable MostrarTabla(JTable tblm) {
    try {
      String sql = "SELECT Id,Nombre,Apellidos,Usuario FROM usuarios ";
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
      tblm.setModel(modelo);
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
    return tblm;
  }
  
  public void Eliminar(int Id, String Nombre, String Apellidos, String Usuario, String Contrase) {
    try {
      String sql = "DELETE FROM usuarios WHERE Id=" + Id;
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
  
  public int consultaId() {
    int Id = 0;
    try {
      String sql = "SELECT * FROM usuarios ORDER BY Id Desc limit 1";
      
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
}
