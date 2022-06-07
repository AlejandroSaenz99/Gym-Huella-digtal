/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Datos;


public class DAsistencia {
    private int Id;
    private int Id_Cliente;
    private String Nombre;
    private String Apellidos;
    private String Fecha;
    private String Hora;

    public DAsistencia(int Id, int Id_Cliente, String Nombre, String Apellidos, String Fecha, String Hora) {
        this.Id = Id;
        this.Id_Cliente = Id_Cliente;
        this.Nombre = Nombre;
        this.Apellidos = Apellidos;
        this.Fecha = Fecha;
        this.Hora = Hora;
    }

    public DAsistencia() {
    }

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public int getId_Cliente() {
        return Id_Cliente;
    }

    public void setId_Cliente(int Id_Cliente) {
        this.Id_Cliente = Id_Cliente;
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

    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String Fecha) {
        this.Fecha = Fecha;
    }

    public String getHora() {
        return Hora;
    }

    public void setHora(String Hora) {
        this.Hora = Hora;
    }
}
