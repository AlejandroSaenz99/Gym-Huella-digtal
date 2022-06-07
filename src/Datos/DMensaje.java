/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Datos;

/**
 *
 * @author AlejandroSaenz
 */
public class DMensaje {
    private int Id;
    private String Mensaje;

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public String getMensaje() {
        return Mensaje;
    }

    public void setMensaje(String Mensaje) {
        this.Mensaje = Mensaje;
    }

    public DMensaje(int Id, String Mensaje) {
        this.Id = Id;
        this.Mensaje = Mensaje;
    }

    public DMensaje() {
    }
}
