package com.tallerwebi.dominio;

import javax.persistence.*;

@Entity
public class ZapatoDos extends Producto {

    private String imagen;

    public ZapatoDos() {
        super();
        this.imagen = "img/zapatos-reforzados.png";
    }

    public ZapatoDos(String nombre, double precio) {
        super(nombre, precio);
        this.imagen = "img/zapatos-reforzados.png";
    }

    public String getImagen() {
        return imagen;
    }
}
