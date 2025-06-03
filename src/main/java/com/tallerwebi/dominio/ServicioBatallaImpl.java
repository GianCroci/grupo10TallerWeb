package com.tallerwebi.dominio;

import org.springframework.stereotype.Service;

@Service
public class ServicioBatallaImpl implements ServicioBatalla {
    @Override
    public Personaje buscarRival() {
        return null;
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
