package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.NivelDeEquipamientoMaximoException;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
public class Arma extends Equipamiento {


    public Arma(String nombre, Estadisticas stats, Rol rol, Integer costoCompra, Integer costoVenta, Integer costoMejora, Integer nivel, Boolean equipado) {
        super(nombre, stats, rol, costoCompra, costoVenta, costoMejora, nivel, equipado);
    }

    public Arma() {

    }

    @Override
    public void mejorar() throws NivelDeEquipamientoMaximoException {
        Integer nivel = this.getNivel();
        if (nivel == 5){
            throw new NivelDeEquipamientoMaximoException("Se ha alcanzado el nivel maximo de este equipamiento");
        }
        this.setNivel(nivel + 1);
        this.getRol().aplicarMejora(this);
    }
}
