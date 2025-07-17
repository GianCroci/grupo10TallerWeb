package com.tallerwebi.dominio.entidad;

import com.tallerwebi.dominio.excepcion.NivelDeEquipamientoMaximoException;

import javax.persistence.Entity;

@Entity
public class Casco extends Equipamiento {

    @Override
    public void mejorar() throws NivelDeEquipamientoMaximoException {
        if (this.getNivel() == 10){
            throw new NivelDeEquipamientoMaximoException("Se ha alcanzado el nivel maximo de este equipamiento");
        }
        this.getRol().aplicarMejora(this);
        this.setCostoMejora(this.getCostoMejora() + 20);
        this.setCostoCompra(this.getCostoCompra() + 20);
        this.setCostoVenta(this.getCostoVenta() + 20);
        this.setNivel(this.getNivel() + 1);
    }
}
