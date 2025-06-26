package com.tallerwebi.dominio;

import java.util.List;

public interface ServicioChat {
    void guardarMensaje(Mensaje mensaje);
    List<Mensaje> obtenerHistorial(String usuario1, String usuario2);
}
