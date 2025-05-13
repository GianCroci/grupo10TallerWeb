package com.tallerwebi.dominio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServicioPersonajeImpl implements ServicioPersonaje {

    private Personaje personaje;

    @Autowired
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

    @Override
    public void setRol(String rol) {
        personaje.setRol(rol);
    }

    @Override
    public String getRol() {
        return personaje.getRol();
    }

    @Override
    public Integer getFuerza() {
        return personaje.getFuerza();
    }

    @Override
    public Integer getInteligencia() {
        return personaje.getInteligencia();
    }

    @Override
    public Integer getArmadura() {
        return personaje.getArmadura();
    }

    @Override
    public Integer getAgilidad() {
        return personaje.getAgilidad();
    }

    @Override
    public void guardarPersonaje(Personaje personaje) {
        this.personaje = personaje;
    }

    @Override
    public void setGenero(String genero) {
        personaje.setGenero(genero);
    }

    @Override
    public String getGenero() {
        return personaje.getGenero();
    }
}
