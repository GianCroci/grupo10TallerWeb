package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.RivalNoEncontrado;

public interface ServicioBatalla {
    Personaje buscarRival(Long idPersonaje) throws RivalNoEncontrado;

    void atacarRival(Personaje personaje, Personaje rival);

    String getResultado();
}
