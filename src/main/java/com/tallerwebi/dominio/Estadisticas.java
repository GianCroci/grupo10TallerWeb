package com.tallerwebi.dominio;

import javax.persistence.Embeddable;

@Embeddable
public class Estadisticas {

    private Integer fuerza;
    private Integer inteligencia;
    private Integer armadura;
    private Integer agilidad;

    public void aumentarFuerza(Integer mejora) {
        this.fuerza += mejora;
    }
    public void aumentarInteligencia(Integer mejora) {
        this.inteligencia += mejora;
    }
    public void aumentarArmadura(Integer mejora) {
        this.armadura += mejora;
    }
    public void aumentarAgilidad(Integer mejora) {
        this.agilidad += mejora;
    }

    public Integer getFuerza() { return fuerza; }

    public void setFuerza(Integer fuerza) { this.fuerza = fuerza; }

    public Integer getInteligencia() { return inteligencia; }

    public void setInteligencia(Integer inteligencia) { this.inteligencia = inteligencia; }

    public Integer getArmadura() { return armadura; }

    public void setArmadura(Integer armadura) { this.armadura = armadura; }

    public Integer getAgilidad() { return agilidad; }

    public void setAgilidad(Integer agilidad) { this.agilidad = agilidad; }
}
