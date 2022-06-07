package Puentes;

import Metodos.Metodos_Consulta_Rapida;
import javax.swing.JTable;
import javax.swing.JTextField;

public class PuenteConsultaRapida {
  Metodos_Consulta_Rapida mp2;
  
  public void PuenteMostrarTabla(JTable TablaDatos, JTextField txtId) {
    this.mp2 = new Metodos_Consulta_Rapida();
    JTable tbl = this.mp2.MostrarTabla(TablaDatos, txtId);
  }
  
  public void PuenteMostrarTabla2(JTable TablaDatos, JTextField txtNom, JTextField txtApe) {
    this.mp2 = new Metodos_Consulta_Rapida();
    JTable tbl = this.mp2.MostrarTabla2(TablaDatos, txtNom, txtApe);
  }
  
  public void PuenteMostrarTabla3(JTable TablaDatos, JTextField txtNom) {
    this.mp2 = new Metodos_Consulta_Rapida();
    JTable tbl = this.mp2.MostrarTabla3(TablaDatos, txtNom);
  }
  
  public void PuenteMostrarTabla4(JTable TablaDatos, JTextField txtApe) {
    this.mp2 = new Metodos_Consulta_Rapida();
    JTable tbl = this.mp2.MostrarTabla4(TablaDatos, txtApe);
  }
}
