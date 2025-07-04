package com.tallerwebi.dominio.entidad;

import com.tallerwebi.dominio.Estadisticas;
import com.tallerwebi.dominio.excepcion.NivelDeEquipamientoMaximoException;

import javax.persistence.Entity;

@Entity
public class Botas extends Equipamiento {

    public Botas(String nombre, Estadisticas stats, Rol rol, Integer costoCompra, Integer costoVenta, Integer costoMejora, Integer nivel, Boolean equipado) {
        super(nombre, stats, rol, costoCompra, costoVenta, costoMejora, nivel, equipado);
    }

    public Botas() {

    }

    @Override
    public void mejorar() throws NivelDeEquipamientoMaximoException {
        if (this.getNivel() == 5){
            throw new NivelDeEquipamientoMaximoException("Se ha alcanzado el nivel maximo de este equipamiento");
        }
        this.getRol().aplicarMejora(this);
        this.setCostoMejora(this.getCostoMejora() + 20);
        this.setCostoCompra(this.getCostoCompra() + 20);
        this.setCostoVenta(this.getCostoVenta() + 20);
        this.setNivel(this.getNivel() + 1);
    }
}
