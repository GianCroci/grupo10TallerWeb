package com.tallerwebi.dominio.excepcion;

public class Equipamiento {

    private String nombre;
    private String tipo;

    public Equipamiento(String nombre,String tipo) {
        this.nombre = nombre;
        this.tipo = tipo;
    }

    public String getNombre() {
        return this.nombre;
    }


    public String getTipo() {
        return this.tipo;
    }
}