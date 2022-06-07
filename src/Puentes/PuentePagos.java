package Puentes;

import Datos.DPagos;
import Metodos.Metodos_Altas;
import Metodos.Metodos_Pagos;

public class PuentePagos {
  Metodos_Altas mp;
  
  Metodos_Pagos mp2;
  
  public void PuenteRegistro(DPagos pdto) {
    this.mp2 = new Metodos_Pagos();
    this.mp2.Guardar(pdto);
  }
}
