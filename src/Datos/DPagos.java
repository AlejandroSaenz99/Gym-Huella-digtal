package Datos;

import java.util.Date;

public class DPagos {
  private int Id;
  
  private int Folio;
  
  private String Nombre;
  
  private String Apellidos;
  
  private int Cantidad;
  
  private Date Fecha;
  
  private String Tipo;
  private Date Proximo_Pago;

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public int getFolio() {
        return Folio;
    }

    public void setFolio(int Folio) {
        this.Folio = Folio;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public String getApellidos() {
        return Apellidos;
    }

    public void setApellidos(String Apellidos) {
        this.Apellidos = Apellidos;
    }

    public int getCantidad() {
        return Cantidad;
    }

    public void setCantidad(int Cantidad) {
        this.Cantidad = Cantidad;
    }

    public Date getFecha() {
        return Fecha;
    }

    public void setFecha(Date Fecha) {
        this.Fecha = Fecha;
    }

    public String getTipo() {
        return Tipo;
    }

    public void setTipo(String Tipo) {
        this.Tipo = Tipo;
    }

    public Date getProximo_Pago() {
        return Proximo_Pago;
    }

    public void setProximo_Pago(Date Proximo_Pago) {
        this.Proximo_Pago = Proximo_Pago;
    }

    public DPagos(int Id, int Folio, String Nombre, String Apellidos, int Cantidad, Date Fecha, String Tipo, Date Proximo_Pago) {
        this.Id = Id;
        this.Folio = Folio;
        this.Nombre = Nombre;
        this.Apellidos = Apellidos;
        this.Cantidad = Cantidad;
        this.Fecha = Fecha;
        this.Tipo = Tipo;
        this.Proximo_Pago = Proximo_Pago;
    }

    public DPagos() {
    }
}