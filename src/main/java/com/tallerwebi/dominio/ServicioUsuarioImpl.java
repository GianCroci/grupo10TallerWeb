package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.UsuarioExistente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServicioUsuarioImpl implements ServicioUsuario {

    private RepositorioUsuario repoUsuario;
    private Usuario usuario;

    @Autowired
    public ServicioUsuarioImpl(RepositorioUsuario repoUsuario) {
        this.repoUsuario = repoUsuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
        repoUsuario.guardar(usuario);
    }

    @Override
    public Usuario buscar(String mail) {
        return repoUsuario.buscar(mail);
    }

    @Override
    public Boolean setPersonaje(Personaje personaje) {

        Usuario usuarioEncontrado = repoUsuario.buscar(usuario.getEmail());
        if (usuarioEncontrado != null) {
            usuarioEncontrado.setPersonaje(personaje);
            return true;
        }
        return false;


    }
}
