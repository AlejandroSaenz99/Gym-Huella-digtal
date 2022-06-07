package Puentes;

import Metodos.Metodos_Corte;
import javax.swing.JTable;

public class PuenteCorte {
  Metodos_Corte mp2;
  
  public void PuenteMostrarTabla(JTable TablaDatos) {
    this.mp2 = new Metodos_Corte();
    JTable tbl = this.mp2.MostrarTabla(TablaDatos);
  }
}
