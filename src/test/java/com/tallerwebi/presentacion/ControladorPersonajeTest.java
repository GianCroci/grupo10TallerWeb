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
    private PersonajeDTO personajeDtoMockeado;
    private ServicioPersonaje servicioPersonajeMock;
    private ServicioUsuario servicioUsuarioMock;
    private ControladorPersonaje controladorPersonaje;
    private Usuario usuarioMockeado;

    @BeforeEach
    public void init() {
        sessionMock = mock(HttpSession.class);
        personajeMockeado = mock(Personaje.class);
        personajeDtoMockeado = mock(PersonajeDTO.class);
        usuarioMockeado = mock(Usuario.class);
        servicioUsuarioMock = mock(ServicioUsuario.class);
        servicioPersonajeMock = mock(ServicioPersonaje.class);
        controladorPersonaje = new ControladorPersonaje(servicioUsuarioMock, servicioPersonajeMock);
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
        when(personajeMockeado.getId()).thenReturn(1l);
        when(servicioPersonajeMock.crearPersonaje(any(), any(), any(), any())).thenReturn(personajeMockeado);

        //ejecucion
        ModelAndView modelAndView = controladorPersonaje.guardarPersonaje(personajeDtoMockeado, sessionMock);

        String vistaEsperada = "redirect:/home";

        assertThat(vistaEsperada, equalTo(modelAndView.getViewName()));
        verify(servicioUsuarioMock, times(1)).setPersonaje(personajeMockeado, usuarioMockeado);

    }


}
