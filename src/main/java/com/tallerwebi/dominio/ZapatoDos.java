package com.tallerwebi.dominio;


import com.tallerwebi.dominio.excepcion.NivelDeEquipamientoMaximoException;

import javax.persistence.*;

@Entity

public class ZapatoDos extends Producto {
    public ZapatoDos() {
        super();
    }

    public ZapatoDos(String nombre, double precio) {
        super(nombre, precio);
    }
}
