package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.AmigoInexistenteException;
import com.tallerwebi.dominio.excepcion.PersonajeInvalidoException;
import com.tallerwebi.dominio.excepcion.SolicitudInvalidaException;
import com.tallerwebi.presentacion.AmigoDTO;
import com.tallerwebi.presentacion.SolicitudAmistadDTO;

import java.util.List;

public interface ServicioAmigo {
    void enviarSolicitud(Long idPersonaje, String codigoAmigo) throws PersonajeInvalidoException;

    void aceptarSolicitud(Long idSolicitud) throws SolicitudInvalidaException;

    void rechazarSolicitud(Long idSolicitud) throws SolicitudInvalidaException;

    List<AmigoDTO> obtenerAmigos(Long idPersonaje);

    List<SolicitudAmistadDTO> obtenerSolicitudesRecibidas(Long idPersonaje);

    List<SolicitudAmistadDTO> obtenerSolicitudesEnviadas(Long idPersonaje);
}
