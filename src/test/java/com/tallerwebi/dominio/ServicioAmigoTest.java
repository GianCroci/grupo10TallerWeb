package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.InventarioVacioException;
import com.tallerwebi.dominio.excepcion.PersonajeInvalidoException;
import com.tallerwebi.presentacion.MejoraDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class ServicioAmigoTest {

    private ServicioAmigo servicioAmigo;
    private RepositorioPersonaje repositorioPersonaje;
    private RepositorioSolicitudAmistad repositorioSolicitudAmistad;
    private Long idPersonajeMock;
    private String codigoAmigoMock;

    @BeforeEach
    public void init() {
        repositorioPersonaje = mock(RepositorioPersonaje.class);
        repositorioSolicitudAmistad = mock(RepositorioSolicitudAmistad.class);
        servicioAmigo = new ServicioAmigoImpl(repositorioPersonaje, repositorioSolicitudAmistad);
        idPersonajeMock = 1L;
        codigoAmigoMock = "codigoAmigoMock";
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
        when(personajeMock.getId()).thenReturn(idPersonajeMock);
        when(repositorioPersonaje.buscarPersonaje(idPersonajeMock)).thenReturn(personajeMock);
        when(repositorioPersonaje.buscarPersonajePorCodigoAmigo(codigoAmigoMock)).thenReturn(personajeMock);

        PersonajeInvalidoException exception = assertThrows(PersonajeInvalidoException.class, () -> {
            servicioAmigo.enviarSolicitud(idPersonajeMock, codigoAmigoMock);
        });

        String mensajeExceptionEsperado = "No puede añadirse a si mismo como amigo";

        String mensajeExceptionObtenido = exception.getMessage();

        assertThat(mensajeExceptionEsperado, equalToIgnoringCase(mensajeExceptionObtenido));
    }
}
