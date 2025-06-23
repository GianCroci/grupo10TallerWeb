package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.RivalNoEncontrado;
import org.springframework.stereotype.Service;

@Service
public class ServicioBatallaImpl implements ServicioBatalla {

    private ServicioPersonaje servicioPersonaje;
    private String resultado;

    public ServicioBatallaImpl(ServicioPersonaje servicioPersonaje) {
        this.servicioPersonaje = servicioPersonaje;
    }

    @Override
    public Personaje buscarRival(Long idPersonaje) throws RivalNoEncontrado {
        Personaje personaje = servicioPersonaje.buscarRival(idPersonaje);
        if (personaje == null) {
            throw new RivalNoEncontrado("No se encontro un rival para la batalla");
        }
        return personaje;
    }

    @Override
    public void atacarRival(Personaje personaje, Personaje rival) {

        Integer puntosPersonaje = personaje.getEstadisticas().getFuerza() + personaje.getEstadisticas().getArmadura() + personaje.getEstadisticas().getAgilidad() + personaje.getEstadisticas().getInteligencia();
        Integer puntosRival = rival.getEstadisticas().getFuerza() + rival.getEstadisticas().getArmadura() + rival.getEstadisticas().getAgilidad() + rival.getEstadisticas().getInteligencia();

        if (puntosPersonaje > puntosRival) {
            this.resultado = "Victoria";
        }
        else if(puntosPersonaje.equals(puntosRival)) {
            resultado = "Empate";
        }
        else{
            resultado = "Derrota";
        }

    }

    @Override
    public String getResultado() {
        return resultado;
    }
}
