package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.AmigoInexistenteException;
import com.tallerwebi.dominio.excepcion.PersonajeInvalidoException;
import com.tallerwebi.dominio.excepcion.SolicitudInvalidaException;
import com.tallerwebi.presentacion.AmigoDTO;
import com.tallerwebi.presentacion.SolicitudAmistadDTO;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service("ServicioAmigo")
@Transactional
public class ServicioAmigoImpl implements ServicioAmigo {

    private final RepositorioPersonaje repositorioPersonaje;
    private final RepositorioSolicitudAmistad repositorioSolicitudAmistad;

    public ServicioAmigoImpl(RepositorioPersonaje repositorioPersonaje, RepositorioSolicitudAmistad repositorioSolicitudAmistad) {
        this.repositorioPersonaje = repositorioPersonaje;
        this.repositorioSolicitudAmistad = repositorioSolicitudAmistad;
    }
    @Override
    public void enviarSolicitud(Long idPersonaje, String codigoAmigo) throws PersonajeInvalidoException {
        Personaje remitente = repositorioPersonaje.buscarPersonaje(idPersonaje);
        Personaje destinatario = repositorioPersonaje.buscarPersonajePorCodigoAmigo(codigoAmigo);

        if (destinatario == null) {
            throw new PersonajeInvalidoException("No existe un personaje con este codigo de amistad");
        }
        if (remitente.equals(destinatario)) {
            throw new PersonajeInvalidoException("No puede añadirse a si mismo como amigo");
        }

        SolicitudAmistad solicitud = new SolicitudAmistad();
        solicitud.setRemitente(remitente);
        solicitud.setDestinatario(destinatario);
        solicitud.setEstado(EstadoSolicitud.PENDIENTE);

        repositorioSolicitudAmistad.guardarSolicitud(solicitud);
    }

    @Override
    public void aceptarSolicitud(Long idSolicitud) throws SolicitudInvalidaException {
        SolicitudAmistad solicitud = repositorioSolicitudAmistad.buscarPorId(idSolicitud);

        if (solicitud == null) {
            throw new SolicitudInvalidaException("La solicitud de amistad no existe");
        }
        if (solicitud.getEstado() != EstadoSolicitud.PENDIENTE) {
            throw new SolicitudInvalidaException("Solicitud invalida");
        }

        Personaje remitente = solicitud.getRemitente();
        Personaje destinatario = solicitud.getDestinatario();

        remitente.getAmigos().add(destinatario);
        destinatario.getAmigos().add(remitente);
        solicitud.setEstado(EstadoSolicitud.ACEPTADA);

        repositorioPersonaje.modificar(remitente);
        repositorioPersonaje.modificar(destinatario);
        repositorioSolicitudAmistad.modificarSolicitud(solicitud);
    }

    @Override
    public void rechazarSolicitud(Long idSolicitud) throws SolicitudInvalidaException {
        SolicitudAmistad solicitud = repositorioSolicitudAmistad.buscarPorId(idSolicitud);

        if (solicitud == null) {
            throw new SolicitudInvalidaException("La solicitud de amistad no existe");
        }
        if (solicitud.getEstado() != EstadoSolicitud.PENDIENTE) {
            throw new SolicitudInvalidaException("Solicitud invalida");
        }

        solicitud.setEstado(EstadoSolicitud.RECHAZADA);

        repositorioSolicitudAmistad.modificarSolicitud(solicitud);
    }

    @Override
    public List<AmigoDTO> obtenerAmigos(Long idPersonaje) throws AmigoInexistenteException {
        List<Personaje> amigos = repositorioPersonaje.obtenerAmigos(idPersonaje);

        if (amigos.isEmpty()) {
            throw new AmigoInexistenteException("No tiene amigos agregados");
        }
        List<AmigoDTO> amigosDTO = new ArrayList<>();
        for (Personaje personajeActual : amigos) {
            AmigoDTO amigoDTO = new AmigoDTO();
            amigoDTO.setearAtributos(personajeActual);
            amigosDTO.add(amigoDTO);
        }

        return amigosDTO;
    }

    @Override
    public List<SolicitudAmistadDTO> obtenerSolicitudesRecibidas(Long idPersonaje) {
        List<SolicitudAmistad> solicitudesRecibidasPendientes = repositorioSolicitudAmistad.obtenerSolicitudesDeAmistadRecibidasPendientes(idPersonaje);

        List<SolicitudAmistadDTO> solicitudesRecibidasPendientesDTO = new ArrayList<>();

        for (SolicitudAmistad solicitudActual : solicitudesRecibidasPendientes) {
            SolicitudAmistadDTO solicitudAmistadDTO = new SolicitudAmistadDTO();
            solicitudAmistadDTO.setearAtributosSolicitudesRecibidas(solicitudActual);
            solicitudesRecibidasPendientesDTO.add(solicitudAmistadDTO);
        }
        return solicitudesRecibidasPendientesDTO;
    }

    @Override
    public List<SolicitudAmistadDTO> obtenerSolicitudesEnviadas(Long idPersonaje) {
        List<SolicitudAmistad> solicitudesEnviadasPendientes = repositorioSolicitudAmistad.obtenerSolicitudesDeAmistadEnviadasPendientes(idPersonaje);

        List<SolicitudAmistadDTO> solicitudesEnviadasPendientesDTO = new ArrayList<>();

        for (SolicitudAmistad solicitudActual : solicitudesEnviadasPendientes) {
            SolicitudAmistadDTO solicitudAmistadDTO = new SolicitudAmistadDTO();
            solicitudAmistadDTO.setearAtributosSolicitudesEnviadas(solicitudActual);
            solicitudesEnviadasPendientesDTO.add(solicitudAmistadDTO);
        }
        return solicitudesEnviadasPendientesDTO;
    }
}
