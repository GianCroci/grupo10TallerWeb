package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioMensaje {
    void guardar(Mensaje mensaje);
    List<Mensaje> obtenerHistorial(String usuario1, String usuario2);
}
