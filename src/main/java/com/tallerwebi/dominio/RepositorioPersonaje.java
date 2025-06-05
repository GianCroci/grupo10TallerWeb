package com.tallerwebi.dominio;

public interface RepositorioPersonaje {
    Personaje buscarPersonaje(Long id);
    Boolean guardar(Long id, Personaje personaje);
    void modificar(Personaje personaje);
    Integer buscarOroPersonaje(Long idPersonaje);
}
