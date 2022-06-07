package Puentes;

import Datos.DAltas;
import Datos.DAsistencia;
import Datos.DMensaje;
import Datos.DSegundos;
import Metodos.Metodos_Altas;
import Metodos.Metodos_Pagos;
import com.toedter.calendar.JDateChooser;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class PuenteAltas {
  Metodos_Altas mp;
  
  Metodos_Pagos mp2;
  
  public void PuenteRegistro(DAltas pdto) {
    this.mp = new Metodos_Altas();
    this.mp.Guardar(pdto);
  }
  public void PuenteRegistroAsistencia(DAsistencia pdto) {
    this.mp = new Metodos_Altas();
    this.mp.GuardarAsistencia(pdto);
  }
  
  public void PuenteBuscar(DAltas prov) {
    this.mp = new Metodos_Altas();
    this.mp.Consulta(prov);
  }
  
  public void PuenteBuscar2(DAltas prov) {
    this.mp = new Metodos_Altas();
    this.mp.Consulta2(prov);
  }
  
  public void PuenteBuscar3(DAltas prov) {
    this.mp = new Metodos_Altas();
    this.mp.Consulta3(prov);
  }
  
  public void PuenteBuscar4(DAltas prov) {
    this.mp = new Metodos_Altas();
    this.mp.Consulta4(prov);
  }
  
  public JTable PuenteMostrarTabla(JTable TablaDatos) {
    this.mp2 = new Metodos_Pagos();
    JTable tbl = this.mp2.MostrarTabla(TablaDatos);
    return tbl;
  }
  public JTable PuenteMostrarUltimosPagos(JTable TablaDatos) {
    this.mp2 = new Metodos_Pagos();
    JTable tbl = this.mp2.MostrarUltimosPagos(TablaDatos);
    return tbl;
  }
  public void PuenteBuscarCliente(DAltas prov) {
    this.mp2 = new Metodos_Pagos();
    this.mp2.ConsultaClientes(prov);
  }
   public void PuenteBuscarMensaje(DMensaje prov) {
    this.mp2 = new Metodos_Pagos();
    this.mp2.ConsultaMensaje(prov);
  }
   
   

   public void PuenteBuscarsegundos(DSegundos prov) {
    this.mp2 = new Metodos_Pagos();
    this.mp2.ConsultaSegundos(prov);
  }
   public void PuenteMostrarTablaAsistencia(JTable TablaAsistencia, JTextField txtFecha) {
    this.mp2 = new Metodos_Pagos();
    JTable tbl = this.mp2.MostrarTablaAsistencia(TablaAsistencia, txtFecha);
  }
  public JTable PuenteMostrarTabla2(JTable TablaDatos) {
    this.mp = new Metodos_Altas();
    JTable tbl = this.mp.MostrarTabla2(TablaDatos);
    return tbl;
  }
  
  public ImageIcon puenteObtenerImagen(int clv) {
    this.mp = new Metodos_Altas();
    ImageIcon img = null;
    try {
      img = this.mp.obtenerImagen(clv);
    } catch (Exception ex) {
      JOptionPane.showMessageDialog(null, "Error: " + ex);
    } 
    return img;
  }
  
  public ImageIcon puenteObtenerImagen2(String Nombre, String Apellidos) {
    this.mp = new Metodos_Altas();
    ImageIcon img = null;
    try {
      img = this.mp.obtenerImagen2(Nombre, Apellidos);
    } catch (Exception ex) {
      JOptionPane.showMessageDialog(null, "Error: " + ex);
    } 
    return img;
  }
  
  public ImageIcon puenteObtenerImagen3(String Nombre) {
    this.mp = new Metodos_Altas();
    ImageIcon img = null;
    try {
      img = this.mp.obtenerImagen3(Nombre);
    } catch (Exception ex) {
      JOptionPane.showMessageDialog(null, "Error: " + ex);
    } 
    return img;
  }
  
  public ImageIcon puenteObtenerImagen4(String Apellidos) {
    this.mp = new Metodos_Altas();
    ImageIcon img = null;
    try {
      img = this.mp.obtenerImagen4(Apellidos);
    } catch (Exception ex) {
      JOptionPane.showMessageDialog(null, "Error: " + ex);
    } 
    return img;
  }
  
  public void PuenteActualizar(DAltas prod) {
    this.mp = new Metodos_Altas();
    this.mp.Actualizar(prod);
  }
}
