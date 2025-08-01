package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidad.Mensaje;
import com.tallerwebi.dominio.interfaz.repositorio.RepositorioMensaje;
import com.tallerwebi.dominio.interfaz.servicio.ServicioChat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service("servicioChat")
public class ServicioChatImpl implements ServicioChat {

    @Autowired
    private RepositorioMensaje repositorioMensaje;


    public ServicioChatImpl(RepositorioMensaje repositorioMensaje) {
        this.repositorioMensaje = repositorioMensaje;
    }

    @Override
    public void guardarMensaje(Mensaje mensaje) {
        repositorioMensaje.guardar(mensaje);
    }

    @Override
    public List<Mensaje> obtenerHistorial(String usuario1, String usuario2) {
        return repositorioMensaje.obtenerHistorial(usuario1, usuario2);
    }
}
