package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.NivelDeEquipamientoMaximoException;

import javax.persistence.*;

@Entity
public class Abrigo extends Equipamiento{

    private String imagen;

    public Abrigo() {
        super();

    }

    @Override
    public void mejorar() throws NivelDeEquipamientoMaximoException {

    }

    public Abrigo(String nombre, Integer costoVenta) {
        super();
        this.setNombre(nombre);
        this.setCostoVenta(costoVenta);
        this.imagen = "img/abrigo-gris.png";
    }

    public String getImagen() {
        return imagen;
    }
    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
}
