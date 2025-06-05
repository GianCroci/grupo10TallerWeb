package com.tallerwebi.dominio;

import org.springframework.stereotype.Service;

@Service
public class ServicioBatallaImpl implements ServicioBatalla {

    private ServicioPersonaje servicioPersonaje;

    public ServicioBatallaImpl(ServicioPersonaje servicioPersonaje) {
        this.servicioPersonaje = servicioPersonaje;
    }

    @Override
    public Personaje buscarRival() {
        return servicioPersonaje.buscarRival();
    }

    @Override
    public Object atacarRival(Personaje rivalMockeado, Personaje mockeado) {
        return null;
    }

    @Override
    public String getResultado() {
        return "";
    }
}
