package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.NivelDeEquipamientoMaximoException;

import javax.persistence.*;

@Entity
public class Cinturon extends Equipamiento {

    private String imagen;

    public Cinturon() {
        super();

    }

    @Override
    public void mejorar() throws NivelDeEquipamientoMaximoException {

    }

    public Cinturon(String nombre, Integer costoVenta) {
        super();
        this.setNombre(nombre);
        this.setCostoVenta(costoVenta);
        this.imagen = "img/cinturon-oro.png";
    }

    public String getImagen() {
        return imagen;
    }
}
