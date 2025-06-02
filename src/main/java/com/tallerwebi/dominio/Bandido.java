package com.tallerwebi.dominio;

import javax.persistence.Entity;

@Entity
public class Bandido extends Rol {

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
}
