package com.tallerwebi.dominio.interfaz.servicio;

import com.tallerwebi.dominio.entidad.Usuario;
import com.tallerwebi.dominio.entidad.Personaje;

public interface ServicioUsuario {
    void setPersonaje(Personaje personaje, Usuario usuario);

    void setUsuario(Usuario usuario);

    Usuario buscar(String mail);
}
