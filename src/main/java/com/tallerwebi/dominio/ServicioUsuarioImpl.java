package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.UsuarioExistente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
