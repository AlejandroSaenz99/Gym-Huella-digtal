package Datos;

public class DAltas {
  private int Id;
  
  private String Nombre;
  
  private String Apellidos;
  
  private Float Cantidad;
  
  private String Fecha;
  
  private String Registrado_Por;
  
  private String Imagen;
  
  public DAltas(int Id, String Nombre, String Apellidos, Float Cantidad, String Fecha, String Registrado_Por, String Imagen) {
    this.Id = Id;
    this.Nombre = Nombre;
    this.Apellidos = Apellidos;
    this.Cantidad = Cantidad;
    this.Fecha = Fecha;
    this.Registrado_Por = Registrado_Por;
    this.Imagen = Imagen;
  }
  
  public DAltas() {}
  
  public int getId() {
    return this.Id;
  }
  
  public void setId(int Id) {
    this.Id = Id;
  }
  
  public String getNombre() {
    return this.Nombre;
  }
  
  public void setNombre(String Nombre) {
    this.Nombre = Nombre;
  }
  
  public String getApellidos() {
    return this.Apellidos;
  }
  
  public void setApellidos(String Apellidos) {
    this.Apellidos = Apellidos;
  }
  
  public Float getCantidad() {
    return this.Cantidad;
  }
  
  public void setCantidad(Float Cantidad) {
    this.Cantidad = Cantidad;
  }
  
  public String getFecha() {
    return this.Fecha;
  }
  
  public void setFecha(String Fecha) {
    this.Fecha = Fecha;
  }
  
  public String getRegistrado_Por() {
    return this.Registrado_Por;
  }
  
  public void setRegistrado_Por(String Registrado_Por) {
    this.Registrado_Por = Registrado_Por;
  }
  
  public String getImagen() {
    return this.Imagen;
  }
  
  public void setImagen(String Imagen) {
    this.Imagen = Imagen;
  }
}
