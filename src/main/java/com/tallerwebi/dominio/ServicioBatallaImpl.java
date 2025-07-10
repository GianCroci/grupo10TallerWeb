package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidad.Personaje;
import com.tallerwebi.dominio.excepcion.RivalNoEncontrado;
import com.tallerwebi.dominio.interfaz.servicio.ServicioBatalla;
import com.tallerwebi.dominio.interfaz.servicio.ServicioPersonaje;
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
}
