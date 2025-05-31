package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.NivelDeEquipamientoMaximoException;

import javax.persistence.Entity;

@Entity
public class Escudo extends Equipamiento {

    public Escudo(String nombre, Estadisticas stats, Rol rol, Integer costoCompra, Integer costoVenta, Integer costoMejora, Integer nivel, Boolean equipado) {
        super(nombre, stats, rol, costoCompra, costoVenta, costoMejora, nivel, equipado);
    }

    public Escudo() {

    }

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
