package com.tallerwebi.dominio;

public interface ServicioBatalla {
    Personaje buscarRival(Long idPersonaje);

    void atacarRival(Personaje personaje, Personaje rival);

    String getResultado();
}
