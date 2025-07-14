package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidad.Personaje;
import com.tallerwebi.dominio.entidad.Usuario;
import com.tallerwebi.dominio.interfaz.repositorio.RepositorioUsuario;
import com.tallerwebi.dominio.interfaz.servicio.ServicioUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service("servicioUsuario")
@Transactional
public class ServicioUsuarioImpl implements ServicioUsuario {
    private RepositorioUsuario repositorioUsuario;

    @Autowired
    public ServicioUsuarioImpl(RepositorioUsuario repoUsuario) {

        this.repositorioUsuario = repoUsuario;
    }

    public void setUsuario(Usuario usuario) {
        repositorioUsuario.guardar(usuario);
    }

    @Override
    public Usuario buscar(String mail) {

        return repositorioUsuario.buscar(mail);
    }

    @Override
    public void setPersonaje(Personaje personaje, Usuario usuario) {
        usuario.setPersonaje(personaje);
        repositorioUsuario.modificar(usuario);
    }
}
