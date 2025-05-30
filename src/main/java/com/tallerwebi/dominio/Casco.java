package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.NivelDeEquipamientoMaximoException;

public class Casco extends Equipamiento {

    @Override
    public void mejorar() throws NivelDeEquipamientoMaximoException {
        Integer nivel = this.getNivel();
        if (nivel == 5){
            throw new NivelDeEquipamientoMaximoException("Se ha alcanzado el nivel maximo de este equipamiento");
        }
        this.setNivel(nivel + 1);
        getRol().aplicarMejora(this);
    }
}
