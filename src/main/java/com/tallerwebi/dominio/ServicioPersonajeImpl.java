package com.tallerwebi.dominio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServicioPersonajeImpl implements ServicioPersonaje {

    private RepositorioPersonaje repoPersonaje;

    @Autowired
    public ServicioPersonajeImpl(RepositorioPersonaje repoPersonaje) {
        this.repoPersonaje = repoPersonaje;
        //Personaje personaje = new Personaje();
    }

    @Override
    public String getNombre(Personaje personaje) {
        return repoPersonaje.buscarPersonaje(personaje.getId()).getNombre();
    }

    @Override
    public String getRol(Personaje personaje) {

        return repoPersonaje.buscarPersonaje(personaje.getId()).getRol();
    }

    @Override
    public Integer getFuerza(Personaje personaje) {

        return repoPersonaje.buscarPersonaje(personaje.getId()).getFuerza();
    }

    @Override
    public Integer getInteligencia(Personaje personaje) {

        return repoPersonaje.buscarPersonaje(personaje.getId()).getInteligencia();
    }

    @Override
    public Integer getArmadura(Personaje personaje) {

        return repoPersonaje.buscarPersonaje(personaje.getId()).getArmadura();
    }

    @Override
    public Integer getAgilidad(Personaje personaje) {
        return repoPersonaje.buscarPersonaje(personaje.getId()).getAgilidad();
    }

    @Override
    public void guardarPersonaje(Personaje personaje) {
        repoPersonaje.guardar(personaje);
    }

    @Override
    public String getGenero(Personaje personaje) {
        return repoPersonaje.buscarPersonaje(personaje.getId()).getGenero();
    }

    @Override
    public Personaje buscarPersonaje(Long id) {
        return repoPersonaje.buscarPersonaje(id);
    }

    @Override
    public void modificar(Personaje personaje) {
        repoPersonaje.modificar(personaje);
    }

    @Override
    public Personaje buscarRival() {
        return repoPersonaje.buscarRival();
    }
}
