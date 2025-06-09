package com.tallerwebi.dominio;

import javax.persistence.*;

@Entity
public class Abrigo extends Producto {

    private String imagen;

    public Abrigo() {
        super();
        this.imagen = "img/abrigo-gris.png";
    }

    public Abrigo(String nombre, double precio) {
        super(nombre, precio);
        this.imagen = "img/abrigo-gris.png";
    }

    public String getImagen() {
        return imagen;
    }
}
