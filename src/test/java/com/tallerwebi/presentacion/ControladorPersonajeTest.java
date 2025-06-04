package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import org.dom4j.rule.Mode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class ControladorPersonajeTest {

    private HttpSession sessionMock;
    private Personaje personajeMockeado;
    private ServicioPersonaje servicioPersonajeMock;
    private ServicioUsuario servicioUsuarioMock;
    private ControladorPersonaje controladorPersonaje;
    private Usuario usuarioMockeado;

    @BeforeEach
    public void init() {
        sessionMock = mock(HttpSession.class);
        personajeMockeado = mock(Personaje.class);
        usuarioMockeado = mock(Usuario.class);
        servicioUsuarioMock = mock(ServicioUsuario.class);
        servicioPersonajeMock = mock(ServicioPersonaje.class);
        controladorPersonaje = new ControladorPersonaje(servicioUsuarioMock);
    }

    @Test
    public void queHayaUnaVistaDeCreacionDePersonaje(){

        ModelAndView modelAndView = controladorPersonaje.creacionPersonaje();

        String vistaEsperada = "creacion-personaje";

        assertThat(vistaEsperada, equalTo(modelAndView.getViewName()));
    }

    @Test
    public void queSeCreeElPersonajeYMeDevuelvaUnaVistaConLaInformacion(){
        //preparacion
        when(sessionMock.getAttribute(anyString())).thenReturn(usuarioMockeado);
        when(usuarioMockeado.getPersonaje()).thenReturn(personajeMockeado);

        //ejecucion
        ModelAndView modelAndView = controladorPersonaje.guardarPersonaje(personajeMockeado, sessionMock);

        String vistaEsperada = "home";

        assertThat(vistaEsperada, equalTo(modelAndView.getViewName()));
        assertThat(modelAndView.getModel().get("datosPersonaje"), equalTo(personajeMockeado));
        verify(servicioUsuarioMock, times(1)).setPersonaje(personajeMockeado, usuarioMockeado);

    }


}
