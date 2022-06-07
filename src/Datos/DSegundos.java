/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Datos;

/**
 *
 * @author del.sistemas_res
 */
public class DSegundos {
 private int segundos;
private int id;

    public DSegundos(int segundos, int id) {
        this.segundos = segundos;
        this.id = id;
    }

    public DSegundos() {
    }

    public int getSegundos() {
        return segundos;
    }

    public void setSegundos(int segundos) {
        this.segundos = segundos;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
  
}
