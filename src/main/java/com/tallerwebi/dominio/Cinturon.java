package com.tallerwebi.dominio;


import com.tallerwebi.dominio.excepcion.NivelDeEquipamientoMaximoException;

import javax.persistence.*;

@Entity

public class Cinturon extends Producto {
    public Cinturon() {
        super();
    }

    public Cinturon(String nombre, double precio) {
        super(nombre, precio);
    }
}
