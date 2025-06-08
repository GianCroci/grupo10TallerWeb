package com.tallerwebi.dominio;

import javax.persistence.*;

@Entity
public class Capucha extends Producto {

    private String imagen;

    public Capucha() {
        super();
        this.imagen = "img/capucha-sombras.png";
    }

    public Capucha(String nombre, double precio) {
        super(nombre, precio);
        this.imagen = "img/capucha-sombras.png";
    }

    public String getImagen() {
        return imagen;
    }
}
