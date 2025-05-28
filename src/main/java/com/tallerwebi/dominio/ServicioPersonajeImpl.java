package com.tallerwebi.dominio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServicioPersonajeImpl implements ServicioPersonaje {

    private Personaje personaje;
    private RepositorioPersonaje repoPersonaje;

    @Autowired
    public ServicioPersonajeImpl(RepositorioPersonaje repoPersonaje) {
        this.repoPersonaje = repoPersonaje;
        this.personaje = new Personaje();
    }

    @Override
    public void setNombre(String nombre) {
        personaje.setNombre(nombre);
    }

    @Override
    public String getNombre() {
        Long id = 1l;
        Personaje personajeBuscado = repoPersonaje.buscarPersonaje(id);
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
    public Boolean guardarPersonaje(Personaje personaje) {
        Long id = personaje.getId();
        Boolean seGuardo = repoPersonaje.guardar(id, personaje);
        if (seGuardo == true) {
            return true;
        }
        else {
            return false;
        }
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
