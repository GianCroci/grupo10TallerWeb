package com.tallerwebi.dominio;

public interface ServicioBatalla {
    Personaje buscarRival();

    void atacarRival(Personaje personaje, Personaje rival);

    String getResultado();
}
