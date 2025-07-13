package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidad.Usuario;
import com.tallerwebi.dominio.interfaz.repositorio.RepositorioUsuario;
import com.tallerwebi.dominio.excepcion.UsuarioExistente;
import com.tallerwebi.dominio.interfaz.servicio.ServicioLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service("servicioLogin")
@Transactional
public class ServicioLoginImpl implements ServicioLogin {

    private RepositorioUsuario repositorioUsuario;
    private BCryptPasswordEncoder encoder;

    @Autowired
    public ServicioLoginImpl(RepositorioUsuario repositorioUsuario, BCryptPasswordEncoder encoder){
        this.repositorioUsuario = repositorioUsuario;
        this.encoder = encoder;
    }

    @Override
    public Usuario consultarUsuario (String email, String password) {
        Usuario usuario = repositorioUsuario.buscar(email);
        if (encoder.matches(password, usuario.getPassword())) {
            return usuario;
        }
       return null;
    }

    @Override
    public void registrar(Usuario usuario) throws UsuarioExistente {
        if (repositorioUsuario.buscar(usuario.getEmail()) != null) {
            throw new UsuarioExistente("El usuario con ese email ya existe");
        }
        String hashPass = encoder.encode(usuario.getPassword());
        usuario.setPassword(hashPass);

        repositorioUsuario.guardar(usuario);
    }

}

