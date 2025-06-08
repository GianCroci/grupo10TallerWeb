package com.tallerwebi.dominio;

import javax.persistence.*;

@Entity
public class Cinturon extends Producto {

    private String imagen;

    public Cinturon() {
        super();
        this.imagen = "img/cinturon-oro.png";
    }

    public Cinturon(String nombre, double precio) {
        super(nombre, precio);
        this.imagen = "img/cinturon-oro.png";
    }

    public String getImagen() {
        return imagen;
    }
}
