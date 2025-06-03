package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioBatalla;
import com.tallerwebi.dominio.ServicioPersonaje;
import com.tallerwebi.dominio.ServicioUsuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ControladorBatallaTest {

    private ServicioPersonaje servicioPersonajeMock;
    private ServicioUsuario servicioUsuarioMock;
    private ControladorBatalla controladorBatalla;
    private HttpServletRequest requestMock;
    private HttpSession sessionMock;

    @BeforeEach
    public void init(){
        servicioUsuarioMock = mock(ServicioUsuario.class);
        servicioPersonajeMock = mock(ServicioPersonaje.class);
        controladorBatalla = new ControladorBatalla(servicioUsuarioMock);
        requestMock = mock(HttpServletRequest.class);
        sessionMock = mock(HttpSession.class);
    }

    @Test
    public void queMeMuestreUnaVistaDeArenaDeBatallaConDosPersonajes(){
        //preparacion
        when(requestMock.getSession()).thenReturn(sessionMock);
        when(sessionMock.getAttribute("idPersonaje")).thenReturn(1L);
        when(servicioPersonajeMock.)



        ModelAndView modelAndView = controladorBatalla.irABatalla();

        String vistaEsperada = "batalla";

        assertThat(vistaEsperada, equalTo(modelAndView.getViewName()));
    }
}
