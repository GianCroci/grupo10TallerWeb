package com.tallerwebi.dominio;


import com.tallerwebi.dominio.excepcion.NivelDeEquipamientoMaximoException;

import javax.persistence.*;


@Entity

public class Abrigo extends Producto {
    public Abrigo() {
        super();
    }

    public Abrigo(String nombre, double precio) {
        super(nombre, precio);
    }
}
