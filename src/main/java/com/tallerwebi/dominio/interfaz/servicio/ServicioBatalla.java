package com.tallerwebi.dominio.interfaz.servicio;

import com.tallerwebi.dominio.entidad.Personaje;
import com.tallerwebi.dominio.excepcion.RivalNoEncontrado;

public interface ServicioBatalla {
    Personaje buscarRival(Long idPersonaje) throws RivalNoEncontrado;

}
