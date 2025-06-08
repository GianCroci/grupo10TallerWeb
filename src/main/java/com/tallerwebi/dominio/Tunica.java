package com.tallerwebi.dominio;


import com.tallerwebi.dominio.excepcion.NivelDeEquipamientoMaximoException;

import javax.persistence.*;

@Entity
public class Tunica extends Producto {

    private String imagen;

    public Tunica() {
        super();
        this.imagen = "img/tunica-azul.png";
    }

    public Tunica(String nombre, double precio) {
        super(nombre, precio);
        this.imagen = "img/tunica-azul.png";
    }

    public String getImagen() {
        return imagen;
    }
}


