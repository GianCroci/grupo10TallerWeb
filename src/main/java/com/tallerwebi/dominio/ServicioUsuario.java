package com.tallerwebi.dominio;

public interface ServicioUsuario {
    Boolean setPersonaje(Personaje personaje, Usuario usuario);

    void setUsuario(Usuario usuario);

    Usuario buscar(String mail);
}
