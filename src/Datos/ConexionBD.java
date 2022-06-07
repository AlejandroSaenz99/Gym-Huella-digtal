package Datos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConexionBD {
  private Connection conexion = null;
  
  private Statement sentenciaSQL;
  
  private ResultSet resultado;
  
  public ConexionBD() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
    String controlador = "com.mysql.jdbc.Driver";
    Class.forName(controlador).newInstance();
    String URL = "jdbc:mysql://localhost:3306/gymremake?zeroDateTimeBehavior=convertToNull";
    String usuario = "root";
    String password = "";
    this.conexion = DriverManager.getConnection(URL, usuario, password);
  }
  
  public Connection Conectarse() {
    return this.conexion;
  }
  
  public void CerrarConexion() throws SQLException {
    if (this.resultado != null)
      this.resultado.close(); 
    if (this.sentenciaSQL != null)
      this.sentenciaSQL.close(); 
    if (this.conexion != null)
      this.conexion.close(); 
  }
}