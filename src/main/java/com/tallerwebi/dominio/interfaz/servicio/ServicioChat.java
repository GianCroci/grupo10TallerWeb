package com.tallerwebi.dominio.interfaz.servicio;

import com.tallerwebi.dominio.entidad.Mensaje;

import java.util.List;

public interface ServicioChat {
    void guardarMensaje(Mensaje mensaje);
    List<Mensaje> obtenerHistorial(String usuario1, String usuario2);
}
