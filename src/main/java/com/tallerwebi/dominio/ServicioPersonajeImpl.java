package com.tallerwebi.dominio;

import org.springframework.stereotype.Service;

@Service
public class ServicioPersonajeImpl implements ServicioPersonaje {

    private Personaje personaje;

    public ServicioPersonajeImpl(Personaje personaje) {
        this.personaje = personaje;
    }

    @Override
    public void setNombre(String nombre) {
        personaje.setNombre(nombre);
    }

    @Override
    public String getNombre() {
        return personaje.getNombre();
    }
}
