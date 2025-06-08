package com.tallerwebi.dominio;


import com.tallerwebi.dominio.excepcion.NivelDeEquipamientoMaximoException;

import javax.persistence.*;

@Entity

public class ZapatoUno extends Producto {
    public ZapatoUno() {
        super();
    }

    public ZapatoUno(String nombre, double precio) {
        super(nombre, precio);
    }
}

