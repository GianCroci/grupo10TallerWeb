package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidad.Personaje;
import com.tallerwebi.dominio.entidad.SolicitudAmistad;
import com.tallerwebi.dominio.excepcion.PersonajeInvalidoException;
import com.tallerwebi.dominio.excepcion.SolicitudInvalidaException;
import com.tallerwebi.dominio.interfaz.repositorio.RepositorioPersonaje;
import com.tallerwebi.dominio.interfaz.repositorio.RepositorioSolicitudAmistad;
import com.tallerwebi.dominio.interfaz.servicio.ServicioAmigo;
import com.tallerwebi.presentacion.AmigoDTO;
import com.tallerwebi.presentacion.SolicitudAmistadDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class ServicioAmigoTest {

    private ServicioAmigo servicioAmigo;
    private RepositorioPersonaje repositorioPersonaje;
    private RepositorioSolicitudAmistad repositorioSolicitudAmistad;
    private Long idPersonajeMock;
    private String codigoAmigoMock;
    private Long idSolicitudMock;

    @BeforeEach
    public void init() {
        repositorioPersonaje = mock(RepositorioPersonaje.class);
        repositorioSolicitudAmistad = mock(RepositorioSolicitudAmistad.class);
        servicioAmigo = new ServicioAmigoImpl(repositorioPersonaje, repositorioSolicitudAmistad);
        idPersonajeMock = 1L;
        codigoAmigoMock = "codigoAmigoMock";
        idSolicitudMock = 1L;
    }

    @Test
    public void queAlEnviarUnaSolicitudDeAmistadCorrectaSeGuarde() throws PersonajeInvalidoException {
        Personaje personajeMock = mock(Personaje.class);
        Personaje personajeMock2 = mock(Personaje.class);
        when(repositorioPersonaje.buscarPersonaje(idPersonajeMock)).thenReturn(personajeMock);
        when(repositorioPersonaje.buscarPersonajePorCodigoAmigo(codigoAmigoMock)).thenReturn(personajeMock2);

        servicioAmigo.enviarSolicitud(idPersonajeMock, codigoAmigoMock);

        verify(repositorioSolicitudAmistad).guardarSolicitud(any(SolicitudAmistad.class));
    }

    @Test
    public void queAlEnviarUnaSolicitudDeAmistadLanceUnaPersonajeInvalidoExceptionSiElDestinatarioEsNulo() throws PersonajeInvalidoException {
        Personaje personajeMock = mock(Personaje.class);
        when(repositorioPersonaje.buscarPersonaje(idPersonajeMock)).thenReturn(personajeMock);
        when(repositorioPersonaje.buscarPersonajePorCodigoAmigo(codigoAmigoMock)).thenReturn(null);

        PersonajeInvalidoException exception = assertThrows(PersonajeInvalidoException.class, () -> {
            servicioAmigo.enviarSolicitud(idPersonajeMock, codigoAmigoMock);
        });

        String mensajeExceptionEsperado = "No existe un personaje con este codigo de amistad";
        String mensajeExceptionObtenido = exception.getMessage();

        assertThat(mensajeExceptionEsperado, equalToIgnoringCase(mensajeExceptionObtenido));
    }

    @Test
    public void queAlEnviarUnaSolicitudDeAmistadLanceUnaPersonajeInvalidoExceptionSiElRemitenteYElDestinatarioSonIguales() throws PersonajeInvalidoException {
        Personaje personajeMock = mock(Personaje.class);
        when(repositorioPersonaje.buscarPersonaje(idPersonajeMock)).thenReturn(personajeMock);
        when(repositorioPersonaje.buscarPersonajePorCodigoAmigo(codigoAmigoMock)).thenReturn(personajeMock);

        PersonajeInvalidoException exception = assertThrows(PersonajeInvalidoException.class, () -> {
            servicioAmigo.enviarSolicitud(idPersonajeMock, codigoAmigoMock);
        });

        String mensajeExceptionEsperado = "No puede añadirse a si mismo como amigo";
        String mensajeExceptionObtenido = exception.getMessage();

        assertThat(mensajeExceptionEsperado, equalToIgnoringCase(mensajeExceptionObtenido));
    }

    @Test
    public void queAlEnviarUnaSolicitudDeAmistadLanceUnaPersonajeInvalidoExceptionSiElRemitenteYElDestinatarioYaSonAmigos() throws PersonajeInvalidoException {
        Personaje personajeMock = mock(Personaje.class);
        Personaje personajeMock2 = mock(Personaje.class);
        List<Personaje> amigosMock = new ArrayList<>();
        amigosMock.add(personajeMock2);
        when(personajeMock.getAmigos()).thenReturn(amigosMock);
        when(repositorioPersonaje.buscarPersonaje(idPersonajeMock)).thenReturn(personajeMock);
        when(repositorioPersonaje.buscarPersonajePorCodigoAmigo(codigoAmigoMock)).thenReturn(personajeMock2);

        PersonajeInvalidoException exception = assertThrows(PersonajeInvalidoException.class, () -> {
            servicioAmigo.enviarSolicitud(idPersonajeMock, codigoAmigoMock);
        });

        String mensajeExceptionEsperado = "Este personaje ya se encuentra como amigo";
        String mensajeExceptionObtenido = exception.getMessage();

        assertThat(mensajeExceptionEsperado, equalToIgnoringCase(mensajeExceptionObtenido));
    }

    @Test
    public void queAlAceptarUnaSolicitudDeAmistadExistenteLosPersonajesSeAgregenComoAmigosSeGuardenYLaSolicitudPaseAEstadoAceptada() throws SolicitudInvalidaException {
        SolicitudAmistad solicitud = new SolicitudAmistad();
        Personaje personajeRemitente = new Personaje();
        Personaje personajeDestinatario = new Personaje();
        solicitud.setEstado(EstadoSolicitud.PENDIENTE);
        solicitud.setRemitente(personajeRemitente);
        solicitud.setDestinatario(personajeDestinatario);
        when(repositorioSolicitudAmistad.buscarPorId(idSolicitudMock)).thenReturn(solicitud);

        servicioAmigo.aceptarSolicitud(idSolicitudMock);

        assertThat(solicitud.getEstado(), is(EstadoSolicitud.ACEPTADA));
        verify(repositorioSolicitudAmistad).modificarSolicitud(solicitud);
        verify(repositorioPersonaje, times(2)).modificar(any());
        verify(repositorioSolicitudAmistad).modificarSolicitud(solicitud);
    }

    @Test
    public void queAlAceptarUnaSolicitudDeAmistadInexistenteLanceUnaSolicitudInvalidaException() throws SolicitudInvalidaException {

        when(repositorioSolicitudAmistad.buscarPorId(idSolicitudMock)).thenReturn(null);

        SolicitudInvalidaException exception = assertThrows(SolicitudInvalidaException.class, () -> {
            servicioAmigo.aceptarSolicitud(idSolicitudMock);
        });

        String mensajeExceptionEsperado = "La solicitud de amistad no existe";
        String mensajeExceptionObtenido = exception.getMessage();

        assertThat(mensajeExceptionEsperado, equalToIgnoringCase(mensajeExceptionObtenido));
    }

    @Test
    public void queAlAceptarUnaSolicitudDeAmistadConEstadoAceptadaLanceUnaSolicitudInvalidaException() throws SolicitudInvalidaException {
        SolicitudAmistad solicitud = new SolicitudAmistad();
        solicitud.setEstado(EstadoSolicitud.ACEPTADA);
        when(repositorioSolicitudAmistad.buscarPorId(idSolicitudMock)).thenReturn(solicitud);

        SolicitudInvalidaException exception = assertThrows(SolicitudInvalidaException.class, () -> {
            servicioAmigo.aceptarSolicitud(idSolicitudMock);
        });

        String mensajeExceptionEsperado = "Solicitud invalida";
        String mensajeExceptionObtenido = exception.getMessage();

        assertThat(mensajeExceptionEsperado, equalToIgnoringCase(mensajeExceptionObtenido));
    }

    @Test
    public void queAlAceptarUnaSolicitudDeAmistadConEstadoRechazadaLanceUnaSolicitudInvalidaException() throws SolicitudInvalidaException {
        SolicitudAmistad solicitud = new SolicitudAmistad();
        solicitud.setEstado(EstadoSolicitud.RECHAZADA);
        when(repositorioSolicitudAmistad.buscarPorId(idSolicitudMock)).thenReturn(solicitud);

        SolicitudInvalidaException exception = assertThrows(SolicitudInvalidaException.class, () -> {
            servicioAmigo.aceptarSolicitud(idSolicitudMock);
        });

        String mensajeExceptionEsperado = "Solicitud invalida";
        String mensajeExceptionObtenido = exception.getMessage();

        assertThat(mensajeExceptionEsperado, equalToIgnoringCase(mensajeExceptionObtenido));
    }

    @Test
    public void queAlRechazarUnaSolicitudDeAmistadSeCambieElEstadoARechazadoYSeGuarde() throws SolicitudInvalidaException {
        SolicitudAmistad solicitud = new SolicitudAmistad();
        solicitud.setEstado(EstadoSolicitud.PENDIENTE);
        when(repositorioSolicitudAmistad.buscarPorId(idSolicitudMock)).thenReturn(solicitud);

        servicioAmigo.rechazarSolicitud(idSolicitudMock);

        assertThat(solicitud.getEstado(), is(EstadoSolicitud.RECHAZADA));
        verify(repositorioSolicitudAmistad).modificarSolicitud(solicitud);
    }

    @Test
    public void queAlRechazarUnaSolicitudDeAmistadInexistenteLanceUnaSolicitudInvalidaException() throws SolicitudInvalidaException {
        when(repositorioSolicitudAmistad.buscarPorId(idSolicitudMock)).thenReturn(null);

        SolicitudInvalidaException exception = assertThrows(SolicitudInvalidaException.class, () -> {
            servicioAmigo.rechazarSolicitud(idSolicitudMock);
        });

        String mensajeExceptionEsperado = "La solicitud de amistad no existe";
        String mensajeExceptionObtenido = exception.getMessage();

        assertThat(mensajeExceptionEsperado, equalToIgnoringCase(mensajeExceptionObtenido));
    }

    @Test
    public void queAlRechazarUnaSolicitudDeAmistadConEstadoAceptadaLanceUnaSolicitudInvalidaException() throws SolicitudInvalidaException {
        SolicitudAmistad solicitud = new SolicitudAmistad();
        solicitud.setEstado(EstadoSolicitud.ACEPTADA);
        when(repositorioSolicitudAmistad.buscarPorId(idSolicitudMock)).thenReturn(solicitud);

        SolicitudInvalidaException exception = assertThrows(SolicitudInvalidaException.class, () -> {
            servicioAmigo.rechazarSolicitud(idSolicitudMock);
        });

        String mensajeExceptionEsperado = "Solicitud invalida";
        String mensajeExceptionObtenido = exception.getMessage();

        assertThat(mensajeExceptionEsperado, equalToIgnoringCase(mensajeExceptionObtenido));
    }

    @Test
    public void queAlRechazarUnaSolicitudDeAmistadConEstadoRechazadaLanceUnaSolicitudInvalidaException() throws SolicitudInvalidaException {
        SolicitudAmistad solicitud = new SolicitudAmistad();
        solicitud.setEstado(EstadoSolicitud.RECHAZADA);
        when(repositorioSolicitudAmistad.buscarPorId(idSolicitudMock)).thenReturn(solicitud);

        SolicitudInvalidaException exception = assertThrows(SolicitudInvalidaException.class, () -> {
            servicioAmigo.rechazarSolicitud(idSolicitudMock);
        });

        String mensajeExceptionEsperado = "Solicitud invalida";
        String mensajeExceptionObtenido = exception.getMessage();

        assertThat(mensajeExceptionEsperado, equalToIgnoringCase(mensajeExceptionObtenido));
    }

    @Test
    public void queElMetodoObtenerAmigosDevuelvaUnaListaDeAmigosDTOConLosAtributosCorrectos() throws SolicitudInvalidaException {
        Personaje personaje = new Personaje();
        personaje.setId(1l);
        personaje.setNombre("Jose");
        personaje.setImagen("imagen.png");
        personaje.setCodigoAmigo("codigo01");
        List<Personaje> personajes = new ArrayList<>();
        personajes.add(personaje);
        when(repositorioPersonaje.obtenerAmigos(idPersonajeMock)).thenReturn(personajes);

        List<AmigoDTO> listaObtenida = servicioAmigo.obtenerAmigos(idPersonajeMock);

        Long idEsperado = 1l;
        Long idObtenido = listaObtenida.get(0).getId();

        String nombreEsperado = "Jose";
        String nombreObtenido = listaObtenida.get(0).getNombre();

        String imagenEsperada = "imagenMiniatura.png";
        String imagenObtenida = listaObtenida.get(0).getImagenAmigo();

        String codigoAmigoEsperada = "codigo01";
        String codigoObtenido = listaObtenida.get(0).getCodigoAmigo();

        assertThat(idEsperado, is(idObtenido));
        assertThat(nombreEsperado, equalToIgnoringCase(nombreObtenido));
        assertThat(imagenEsperada, equalToIgnoringCase(imagenObtenida));
        assertThat(codigoAmigoEsperada, equalToIgnoringCase(codigoObtenido));
    }

    @Test
    public void queElMetodoObtenerSolicitudesRecibidasDevuelaUnaListaDeSolicitudesDTOConLosAtributosCorrectos() throws SolicitudInvalidaException {
        Personaje personaje = new Personaje();
        personaje.setId(1l);
        personaje.setNombre("Jose");

        Personaje personaje2 = new Personaje();
        personaje2.setId(2l);
        personaje2.setNombre("Roberto");

        SolicitudAmistad solictud = new SolicitudAmistad();
        solictud.setId(1l);
        solictud.setDestinatario(personaje);
        solictud.setRemitente(personaje2);

        List<SolicitudAmistad> solicitudes = new ArrayList<>();
        solicitudes.add(solictud);
        when(repositorioSolicitudAmistad.obtenerSolicitudesDeAmistadRecibidasPendientes(idPersonajeMock)).thenReturn(solicitudes);

        List<SolicitudAmistadDTO> listaObtenida = servicioAmigo.obtenerSolicitudesRecibidas(idPersonajeMock);

        Long idEsperado = 1l;
        Long idObtenido = listaObtenida.get(0).getIdSolicitud();

        String nombreRemitenteEsperado = "Roberto";
        String nombreRemitenteObtenido = listaObtenida.get(0).getNombreRemitente();

        assertThat(idEsperado, is(idObtenido));
        assertThat(nombreRemitenteEsperado, equalToIgnoringCase(nombreRemitenteObtenido));
    }

    @Test
    public void queElMetodoObtenerSolicitudesEnviadasDevuelaUnaListaDeSolicitudesDTOConLosAtributosCorrectos() throws SolicitudInvalidaException {
        Personaje personaje = new Personaje();
        personaje.setId(1l);
        personaje.setNombre("Jose");

        Personaje personaje2 = new Personaje();
        personaje2.setId(2l);
        personaje2.setNombre("Roberto");

        SolicitudAmistad solictud = new SolicitudAmistad();
        solictud.setId(1l);
        solictud.setDestinatario(personaje);
        solictud.setRemitente(personaje2);

        List<SolicitudAmistad> solicitudes = new ArrayList<>();
        solicitudes.add(solictud);
        when(repositorioSolicitudAmistad.obtenerSolicitudesDeAmistadEnviadasPendientes(idPersonajeMock)).thenReturn(solicitudes);

        List<SolicitudAmistadDTO> listaObtenida = servicioAmigo.obtenerSolicitudesEnviadas(idPersonajeMock);

        String nombreDestinatarioEsperado = "Jose";
        String nombreDestinatarioObtenido = listaObtenida.get(0).getNombreDestinatario();

        assertThat(nombreDestinatarioEsperado, equalToIgnoringCase(nombreDestinatarioObtenido));
    }

    @Test
    public void queElMetodoObtenerCodigoAmigoPropioDevuelvaElCodigoDeAmigo() throws SolicitudInvalidaException {
        String codigoAmigo = "codigo01";

        when(repositorioPersonaje.obtenerCodigoAmigoPropio(idPersonajeMock)).thenReturn(codigoAmigo);

        String codigoAmigoEsperado = "codigo01";
        String codigoAmigoObtenido = servicioAmigo.obtenerCodigoAmigoPropio(idPersonajeMock);

        assertThat(codigoAmigoEsperado, equalToIgnoringCase(codigoAmigoObtenido));
    }
}
