package com.tallerwebi.dominio;

public interface RepositorioPersonaje {
    Personaje buscarPersonaje(Long id);
    void guardar(Personaje personaje);
    void modificar(Personaje personaje);
}
