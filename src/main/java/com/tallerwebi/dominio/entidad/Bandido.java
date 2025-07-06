package com.tallerwebi.dominio.entidad;

import javax.persistence.Entity;

@Entity
public class Bandido extends Rol {

    public Bandido() {
        super(3l, "Bandido");
    }

    @Override
    public void aplicarMejora(Arma arma) {
        arma.getStats().aumentarFuerza(1);
        arma.getStats().aumentarAgilidad(3);
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
        pechera.getStats().aumentarArmadura(2);
    }

    @Override
    public void aplicarMejora(Pantalones pantalones) {
        pantalones.getStats().aumentarArmadura(1);
    }

    @Override
    public void aplicarMejora(Botas botas) {
        botas.getStats().aumentarArmadura(1);
        botas.getStats().aumentarAgilidad(2);
    }

    @Override
    public void aplicarStatsBase(Personaje personaje) {
        personaje.getEstadisticas().setFuerza(50);
        personaje.getEstadisticas().setArmadura(30);
        personaje.getEstadisticas().setAgilidad(100);
        personaje.getEstadisticas().setInteligencia(70);
    }
}
