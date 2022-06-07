package Puentes;

import Metodos.Metodos_sql;
import javax.swing.JTable;

public class PuenteUsuarios {
  Metodos_sql mp;
  
  public JTable PuenteMostrarTabla(JTable tblm) {
    this.mp = new Metodos_sql();
    JTable tbl = this.mp.MostrarTabla(tblm);
    return tbl;
  }
}
