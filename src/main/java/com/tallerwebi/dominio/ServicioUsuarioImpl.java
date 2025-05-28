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
    private Map<String, Usuario> usuarios = new HashMap<>();

    @Autowired
    public ServicioUsuarioImpl(RepositorioUsuario repoUsuario) {
        this.repoUsuario = repoUsuario;
    }

    public void setUsuario(Usuario usuario) {
        usuarios.put(usuario.getEmail(), usuario);
    }

    @Override
    public Usuario buscar(String mail) {
        return usuarios.get(mail);
    }

    @Override
    public Boolean setPersonaje(Personaje personaje, Usuario usuario) {

        if(!usuarios.containsKey(usuario.getEmail())) {
            usuarios.put(usuario.getEmail(), usuario);
        }
        Usuario usuarioEncontrado = usuarios.get(usuario.getEmail());
        usuarioEncontrado.setPersonaje(personaje);
        usuarios.replace(usuario.getEmail(), usuario, usuarioEncontrado);

        return true;
    }
}
