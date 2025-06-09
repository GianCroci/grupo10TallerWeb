package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.NivelDeEquipamientoMaximoException;

import javax.persistence.*;

@Entity
public class Capucha extends Equipamiento {

    private String imagen;

    public Capucha() {
        super();

    }

    @Override
    public void mejorar() throws NivelDeEquipamientoMaximoException {

    }

    public Capucha(String nombre, Integer costoVenta) {
        super();
        this.setNombre(nombre);
        this.setCostoVenta(costoVenta);
        this.imagen = "img/capucha-sombras.png";
    }

    public String getImagen() {
        return imagen;
    }
}
