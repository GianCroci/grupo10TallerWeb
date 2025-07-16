package com.tallerwebi.dominio.entidad;

import javax.persistence.Entity;

@Entity
public class Guerrero extends Rol {

    public Guerrero() {
        super(1l, "Guerrero");
    }

    @Override
    public void aplicarMejora(Arma arma) {
        arma.getStats().aumentarFuerza(4);
    }

    @Override
    public void aplicarMejora(Escudo escudo) {
        escudo.getStats().aumentarArmadura(3);
    }

    @Override
    public void aplicarMejora(Casco casco) {
        casco.getStats().aumentarArmadura(2);
    }

    @Override
    public void aplicarMejora(Pechera pechera) {
        pechera.getStats().aumentarArmadura(4);
    }

    @Override
    public void aplicarMejora(Pantalones pantalones) {
        pantalones.getStats().aumentarArmadura(2);
    }

    @Override
    public void aplicarMejora(Botas botas) {
        botas.getStats().aumentarArmadura(2);
        botas.getStats().aumentarAgilidad(1);
    }

    @Override
    public void aplicarStatsBase(Personaje personaje) {
        personaje.getEstadisticas().setFuerza(100);
        personaje.getEstadisticas().setArmadura(20);
        personaje.getEstadisticas().setAgilidad(80);
        personaje.getEstadisticas().setInteligencia(60);
    }
}
