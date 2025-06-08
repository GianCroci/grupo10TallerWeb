package com.tallerwebi.dominio;


import com.tallerwebi.dominio.excepcion.NivelDeEquipamientoMaximoException;

import javax.persistence.*;

@Entity

public class Capucha extends Producto {
    public Capucha() {
        super();
    }

    public Capucha(String nombre, double precio) {
        super(nombre, precio);
    }
}
