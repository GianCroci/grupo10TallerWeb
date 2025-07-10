package com.tallerwebi.dominio.entidad;

import javax.persistence.Entity;

@Entity
public class Mago extends Rol {

    public Mago() {
        super(2l, "Mago");
    }

    @Override
    public void aplicarMejora(Arma arma) {
        arma.getStats().aumentarInteligencia(3);
    }

    @Override
    public void aplicarMejora(Escudo escudo) {
        escudo.getStats().aumentarArmadura(2);
    }

    @Override
    public void aplicarMejora(Casco casco) {
        casco.getStats().aumentarArmadura(1);
    }

    @Override
    public void aplicarMejora(Pechera pechera) {
        pechera.getStats().aumentarInteligencia(2);
        pechera.getStats().aumentarArmadura(2);
    }

    @Override
    public void aplicarMejora(Pantalones pantalones) {
        pantalones.getStats().aumentarArmadura(1);
    }

    @Override
    public void aplicarMejora(Botas botas) {
        botas.getStats().aumentarArmadura(1);
        botas.getStats().aumentarAgilidad(1);
    }

    @Override
    public void aplicarStatsBase(Personaje personaje) {
        personaje.getEstadisticas().setFuerza(0);
        personaje.getEstadisticas().setArmadura(10);
        personaje.getEstadisticas().setAgilidad(10);
        personaje.getEstadisticas().setInteligencia(30);
    }
}
