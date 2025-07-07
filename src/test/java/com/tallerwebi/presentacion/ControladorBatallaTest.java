package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.entidad.Personaje;
import com.tallerwebi.dominio.excepcion.RivalNoEncontrado;
import com.tallerwebi.dominio.interfaz.servicio.ServicioBatalla;
import com.tallerwebi.dominio.interfaz.servicio.ServicioPersonaje;
import com.tallerwebi.dominio.interfaz.servicio.ServicioUsuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.*;

public class ControladorBatallaTest {

   /* private ServicioPersonaje servicioPersonajeMock;
    private ServicioUsuario servicioUsuarioMock;
    private ServicioBatalla servicioBatallaMock;
    private ControladorBatalla controladorBatalla;
    private HttpServletRequest requestMock;
    private HttpSession sessionMock;
    private Personaje personajeMockeado;
    private Personaje rivalMockeado;
    private RedirectAttributes redirectAttributesMock;

    @BeforeEach
    public void init(){
        servicioUsuarioMock = mock(ServicioUsuario.class);
        servicioBatallaMock = mock(ServicioBatalla.class);
        servicioPersonajeMock = mock(ServicioPersonaje.class);
        servicioBatallaMock = new ServicioBatallaImpl(servicioPersonajeMock);
        servicioBatallaMock = mock(ServicioBatalla.class);
        controladorBatalla = new ControladorBatalla(servicioPersonajeMock, servicioBatallaMock);
        requestMock = mock(HttpServletRequest.class);
        sessionMock = mock(HttpSession.class);
        personajeMockeado = mock(Personaje.class);
        rivalMockeado = mock(Personaje.class);
        redirectAttributesMock = mock(RedirectAttributes.class);
    }

    @Test
    public void queMeMuestreUnaVistaDeArenaDeBatallaConDosPersonajes() throws RivalNoEncontrado {
        //preparacion
        when(requestMock.getSession()).thenReturn(sessionMock);
        when(sessionMock.getAttribute("idPersonaje")).thenReturn(1L);
        when(servicioPersonajeMock.buscarPersonaje(1L)).thenReturn(personajeMockeado);
        when(servicioBatallaMock.buscarRival(1L)).thenReturn(rivalMockeado);

        //ejecucion
        ModelAndView modelAndView = controladorBatalla.irABatalla(requestMock, redirectAttributesMock);

        String vistaEsperada = "batalla";

        //verificacion
        assertThat(vistaEsperada, equalTo(modelAndView.getViewName()));
        assertThat(personajeMockeado, equalTo(modelAndView.getModel().get("personaje")));
        assertThat(rivalMockeado, equalTo(modelAndView.getModel().get("rival")));
    }

    @Test
    public void queSePuedaAtacarAlrivalYMeDevuelvaUnMensajeConElResultadoDeLaBatalla(){
        //preparacion
        when(requestMock.getSession()).thenReturn(sessionMock);
        when(sessionMock.getAttribute("idPersonaje")).thenReturn(1L);
        when(sessionMock.getAttribute("idRival")).thenReturn(2L);
        when(servicioPersonajeMock.buscarPersonaje(1L)).thenReturn(personajeMockeado);
        when(servicioPersonajeMock.buscarPersonaje(2L)).thenReturn(rivalMockeado);
        when(servicioBatallaMock.getResultado()).thenReturn("Victoria");

        //ejecucion
        ModelAndView modelAndView = controladorBatalla.atacarRival(requestMock);

        String mensajeEsperado = "Victoria";

        //verificacion
        verify(servicioBatallaMock, times(1)).atacarRival(personajeMockeado, rivalMockeado);
        assertThat(mensajeEsperado, equalTo(modelAndView.getModel().get("resultado")));
    }
    /*

        */
}
