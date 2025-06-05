package com.tallerwebi.dominio;

public interface ServicioBatalla {
    Personaje buscarRival();

    Object atacarRival(Personaje rivalMockeado, Personaje mockeado);

    String getResultado();
}
