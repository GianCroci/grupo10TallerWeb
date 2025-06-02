package com.tallerwebi.dominio;

import javax.persistence.Entity;

@Entity
public class Mago extends Rol {

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
}
