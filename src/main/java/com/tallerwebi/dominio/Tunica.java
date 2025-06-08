package com.tallerwebi.dominio;


import com.tallerwebi.dominio.excepcion.NivelDeEquipamientoMaximoException;

import javax.persistence.*;

@Entity

public class Tunica extends Producto {
    public Tunica() {
        super();
    }

    public Tunica(String nombre, double precio) {
        super(nombre, precio);
    }
}

