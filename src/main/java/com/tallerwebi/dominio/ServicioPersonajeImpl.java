package com.tallerwebi.dominio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServicioPersonajeImpl implements ServicioPersonaje {

    private RepositorioPersonaje repositorioPersonaje;
    private RepositorioRol repositorioRol;

    @Autowired
    public ServicioPersonajeImpl(RepositorioPersonaje repositorioPersonaje, RepositorioRol repositorioRol) {
        this.repositorioPersonaje = repositorioPersonaje;
        this.repositorioRol = repositorioRol;
    }

    @Override
    public String getNombre(Personaje personaje) {
        return repositorioPersonaje.buscarPersonaje(personaje.getId()).getNombre();
    }

    @Override
    public Rol getRol(Personaje personaje) {

        return repositorioPersonaje.buscarPersonaje(personaje.getId()).getRol();
    }

    @Override
    public Integer getFuerza(Personaje personaje) {

        return repositorioPersonaje.buscarPersonaje(personaje.getId()).getEstadisticas().getFuerza();
    }

    @Override
    public Integer getInteligencia(Personaje personaje) {

        return repositorioPersonaje.buscarPersonaje(personaje.getId()).getEstadisticas().getInteligencia();
    }

    @Override
    public Integer getArmadura(Personaje personaje) {

        return repositorioPersonaje.buscarPersonaje(personaje.getId()).getEstadisticas().getArmadura();
    }

    @Override
    public Integer getAgilidad(Personaje personaje) {
        return repositorioPersonaje.buscarPersonaje(personaje.getId()).getEstadisticas().getAgilidad();
    }

    @Override
    public void guardarPersonaje(Personaje personaje) {
        repositorioPersonaje.guardar(personaje);
    }

    @Override
    public String getGenero(Personaje personaje) {
        return repositorioPersonaje.buscarPersonaje(personaje.getId()).getGenero();
    }

    @Override
    public Personaje buscarPersonaje(Long id) {
        return repositorioPersonaje.buscarPersonaje(id);
    }

    @Override
    public void modificar(Personaje personaje) {
        repositorioPersonaje.modificar(personaje);
    }

    @Override
    public Personaje buscarRival(Long idPersonaje) {
        return repositorioPersonaje.buscarRival(idPersonaje);
    }

    @Override
    public Personaje crearPersonaje(String nombre, String genero, String imagen, Long idRol) {
        Rol rolObtenido = repositorioRol.obtenerRolPorId(idRol);
        Personaje personajeCreado = new Personaje();
        personajeCreado.setNombre(nombre);
        personajeCreado.setGenero(genero);
        personajeCreado.setImagen(imagen);
        personajeCreado.setRol(rolObtenido);
        personajeCreado.setOro(0);
        personajeCreado.setEstadisticas(new Estadisticas());
        personajeCreado.aplicarEstadisticasBase();
        personajeCreado.inicializarCodigoAmigo();
        repositorioPersonaje.guardar(personajeCreado);
        return personajeCreado;
    }

    @Override
    public String obtenerCodigoAmigoPropio(Long idPersonaje) {
        return repositorioPersonaje.obtenerCodigoAmigoPropio(idPersonaje);
    }
}
