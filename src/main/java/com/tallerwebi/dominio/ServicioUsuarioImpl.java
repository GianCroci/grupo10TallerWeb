package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidad.Personaje;
import com.tallerwebi.dominio.entidad.Usuario;
import com.tallerwebi.dominio.interfaz.repositorio.RepositorioUsuario;
import com.tallerwebi.dominio.interfaz.servicio.ServicioUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServicioUsuarioImpl implements ServicioUsuario {
    private RepositorioUsuario repoUsuario;

    @Autowired
    public ServicioUsuarioImpl(RepositorioUsuario repoUsuario) {

        this.repoUsuario = repoUsuario;
    }

    public void setUsuario(Usuario usuario) {
        repoUsuario.guardar(usuario);
    }

    @Override
    public Usuario buscar(String mail) {

        return repoUsuario.buscar(mail);
    }

    @Override
    public void setPersonaje(Personaje personaje, Usuario usuario) {
        usuario.setPersonaje(personaje);
        repoUsuario.modificar(usuario);
    }
}
