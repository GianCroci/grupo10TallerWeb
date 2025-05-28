package com.tallerwebi.dominio;

public interface ServicioUsuario {
    Boolean setPersonaje(Personaje personaje);

    void setUsuario(Usuario usuario);

    Usuario buscar(String mail);
}
