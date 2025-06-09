package com.tallerwebi.dominio;

import javax.persistence.*;

@Entity
public class ZapatoUno extends Producto {

    private String imagen;

    public ZapatoUno() {
        super();
        this.imagen = "img/zapatos-gris.png";
    }

    public ZapatoUno(String nombre, double precio) {
        super(nombre, precio);
        this.imagen = "img/zapatos-gris.png";
    }

    public String getImagen() {
        return imagen;
    }
}
