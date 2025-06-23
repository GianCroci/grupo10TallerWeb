package com.tallerwebi.dominio;


import com.tallerwebi.dominio.excepcion.NivelDeEquipamientoMaximoException;

import javax.persistence.*;

@Entity
public class Tunica extends Equipamiento {

    private String imagen;

    public Tunica() {
        super();

    }

    @Override
    public void mejorar() throws NivelDeEquipamientoMaximoException {

    }

    public Tunica(String nombre, Integer costoVenta) {
        super();
        this.setNombre(nombre);
        this.setCostoVenta(costoVenta);
        this.imagen = "img/tunica-azul.png";
    }


    public String getImagen() {
        return imagen;
    }
    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
}


