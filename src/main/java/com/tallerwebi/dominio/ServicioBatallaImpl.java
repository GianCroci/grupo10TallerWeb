package com.tallerwebi.dominio;

import org.springframework.stereotype.Service;

@Service
public class ServicioBatallaImpl implements ServicioBatalla {

    private ServicioPersonaje servicioPersonaje;
    private String resultado;

    public ServicioBatallaImpl(ServicioPersonaje servicioPersonaje) {
        this.servicioPersonaje = servicioPersonaje;
    }

    @Override
    public Personaje buscarRival() {
        return servicioPersonaje.buscarRival();
    }

    @Override
    public void atacarRival(Personaje personaje, Personaje rival) {

        if (personaje.getFuerza() + personaje.getArmadura() + personaje.getAgilidad() + personaje.getInteligencia() > rival.getFuerza() + rival.getArmadura() + personaje.getAgilidad() + personaje.getInteligencia()) {
            resultado = "Victoria";
        };
        resultado = "Derrota";
    }

    @Override
    public String getResultado() {
        return resultado;
    }
}
