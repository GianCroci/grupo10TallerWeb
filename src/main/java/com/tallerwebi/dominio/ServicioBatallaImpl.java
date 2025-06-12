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
    public Personaje buscarRival(Long idPersonaje) {
        return servicioPersonaje.buscarRival(idPersonaje);
    }

    @Override
    public void atacarRival(Personaje personaje, Personaje rival) {

        if (personaje.getEstadisticas().getFuerza() + personaje.getEstadisticas().getArmadura() + personaje.getEstadisticas().getAgilidad() + personaje.getEstadisticas().getInteligencia() > rival.getEstadisticas().getFuerza() + rival.getEstadisticas().getArmadura() + personaje.getEstadisticas().getAgilidad() + personaje.getEstadisticas().getInteligencia()) {
            resultado = "Victoria";
        };
        resultado = "Derrota";
    }

    @Override
    public String getResultado() {
        return resultado;
    }
}
