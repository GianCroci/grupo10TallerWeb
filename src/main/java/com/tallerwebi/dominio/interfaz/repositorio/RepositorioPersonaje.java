package com.tallerwebi.dominio.interfaz.repositorio;

import com.tallerwebi.dominio.entidad.Personaje;

import java.util.List;

public interface RepositorioPersonaje {
    Personaje buscarPersonaje(Long id);
    void guardar(Personaje personaje);
    void modificar(Personaje personaje);
    Personaje buscarRival(Long idPersonaje);
    Integer buscarOroPersonaje(Long idPersonaje);

    Personaje buscarPersonajePorCodigoAmigo(String codigoAmigo);

    List<Personaje> obtenerAmigos(Long idPersonaje);

    String obtenerCodigoAmigoPropio(Long idPersonaje);

    void eliminar(Personaje personaje);
}
