package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.NivelDeEquipamientoMaximoException;

import javax.persistence.*;

@Entity
public class ZapatoDos extends Equipamiento {

    private String imagen;

    public ZapatoDos() {
        super();

    }

    @Override
    public void mejorar() throws NivelDeEquipamientoMaximoException {

    }

    public ZapatoDos(String nombre, Integer costoVenta) {
        super();
        this.setNombre(nombre);
        this.setCostoVenta(costoVenta);
        this.imagen = "img/zapatos-reforzados.png";
    }

    public String getImagen() {
        return imagen;
    }
    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
}
