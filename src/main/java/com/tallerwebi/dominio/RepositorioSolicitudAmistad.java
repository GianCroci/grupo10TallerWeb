package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioSolicitudAmistad {
    void guardarSolicitud(SolicitudAmistad solicitud);

    SolicitudAmistad buscarPorId(Long idSolicitud);

    void modificarSolicitud(SolicitudAmistad solicitud);

    List<SolicitudAmistad> obtenerSolicitudesDeAmistadRecibidasPendientes(Long idPersonaje);

    List<SolicitudAmistad> obtenerSolicitudesDeAmistadEnviadasPendientes(Long idPersonaje);
}
