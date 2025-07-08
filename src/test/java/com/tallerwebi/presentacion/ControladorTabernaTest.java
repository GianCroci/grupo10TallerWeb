package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.entidad.Personaje;
import com.tallerwebi.dominio.entidad.Taberna;
import com.tallerwebi.dominio.interfaz.servicio.ServicioTaberna;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class ControladorTabernaTest {

    private HttpSession sessionMock;

    private RedirectAttributes redirectAttributesMock;

    private ControladorTaberna controladorTaberna;

    private ServicioTaberna servicioTabernaMock;

    private Taberna taberna;

    private Personaje personajeMock;

    @BeforeEach
    public void init() {
        sessionMock = mock(HttpSession.class);
        redirectAttributesMock = mock(RedirectAttributes.class);
        servicioTabernaMock = mock(ServicioTaberna.class);
        controladorTaberna = new ControladorTaberna(servicioTabernaMock);
        taberna = new Taberna();
        personajeMock = mock(Personaje.class);
    }


    @Test
    public void queSePuedaVerLaTaberna() {
        when(sessionMock.getAttribute("idPersonaje")).thenReturn(1L);

        ModelAndView mv = controladorTaberna.mostrarTaberna(sessionMock, redirectAttributesMock);

        assertEquals("taberna", mv.getViewName());
    }

    @Test
    public void queSePuedaVerLaTabernaConLaVistaDelMercader() {
        when(sessionMock.getAttribute("idPersonaje")).thenReturn(1L);
        when(servicioTabernaMock.obtenerPersonajeDisponible()).thenReturn(PersonajeTaberna.MERCADER);
        when(servicioTabernaMock.obtenerVistaSegunPersonaje(PersonajeTaberna.MERCADER)).thenReturn("mercader.png");

        ModelAndView modelAndView = controladorTaberna.mostrarTaberna(sessionMock, redirectAttributesMock);

        assertThat(modelAndView.getViewName(), equalTo("taberna"));
        assertThat(modelAndView.getModel().get("personajeDisponible"), equalTo(PersonajeTaberna.MERCADER));
        assertThat(modelAndView.getModel().get("imagenParcial"), equalTo("mercader.png"));
    }

    @Test
    public void queSePuedaVerLaTabernaConLaVistaDelHerrero() {
        when(sessionMock.getAttribute("idPersonaje")).thenReturn(1L);
        when(servicioTabernaMock.obtenerPersonajeDisponible()).thenReturn(PersonajeTaberna.HERRERO);
        when(servicioTabernaMock.obtenerVistaSegunPersonaje(PersonajeTaberna.HERRERO)).thenReturn("herrero.png");

        ModelAndView modelAndView = controladorTaberna.mostrarTaberna(sessionMock, redirectAttributesMock);

        assertThat(modelAndView.getViewName(), equalTo("taberna"));
        assertThat(modelAndView.getModel().get("personajeDisponible"), equalTo(PersonajeTaberna.HERRERO));
        assertThat(modelAndView.getModel().get("imagenParcial"), equalTo("herrero.png"));
    }

    @Test
    public void queSePuedaVerLaTabernaConLaVistaDelGuardia() {
        when(sessionMock.getAttribute("idPersonaje")).thenReturn(1L);
        when(servicioTabernaMock.obtenerPersonajeDisponible()).thenReturn(PersonajeTaberna.GUARDIA);
        when(servicioTabernaMock.obtenerVistaSegunPersonaje(PersonajeTaberna.GUARDIA)).thenReturn("guardia.png");


        ModelAndView modelAndView = controladorTaberna.mostrarTaberna(sessionMock, redirectAttributesMock);

        assertThat(modelAndView.getViewName(), equalTo("taberna"));
        assertThat(modelAndView.getModel().get("personajeDisponible"), equalTo(PersonajeTaberna.GUARDIA));
        assertThat(modelAndView.getModel().get("imagenParcial"), equalTo("guardia.png"));
    }

    @Test
    public void queSePuedaVerLaTabernaConElPersonajeDisponibleYLasEstadisticasDeCervezasInvitidas() {
        when(sessionMock.getAttribute("idPersonaje")).thenReturn(1L);
        when(servicioTabernaMock.obtenerPersonajeDisponible()).thenReturn(PersonajeTaberna.HERRERO);
        when(servicioTabernaMock.obtenerVistaSegunPersonaje(PersonajeTaberna.HERRERO)).thenReturn("herrero.png");
        when(servicioTabernaMock.mostrarTaberna()).thenReturn("vistaTaberna");
        when(servicioTabernaMock.obtenerCervezasInvitadasPorPersonaje(1L)).thenReturn(Map.of(
                PersonajeTaberna.MERCADER, 0,
                PersonajeTaberna.HERRERO, 2,
                PersonajeTaberna.GUARDIA, 1
        ));

        ModelAndView modelAndView = controladorTaberna.mostrarTaberna(sessionMock, redirectAttributesMock);

        assertThat(modelAndView.getViewName(), equalTo("taberna"));
        assertThat(modelAndView.getModel().get("personajeDisponible"), equalTo(PersonajeTaberna.HERRERO));
        assertThat(modelAndView.getModel().get("imagenParcial"), equalTo("herrero.png"));
        assertThat(modelAndView.getModel().get("personajes"), equalTo(Map.of(
                PersonajeTaberna.MERCADER, 0,
                PersonajeTaberna.HERRERO, 2,
                PersonajeTaberna.GUARDIA, 1
        )));
    }


    @Test
    public void queSePuedaInvitarUnTragoAlPersonajeDisponible() {
        when(sessionMock.getAttribute("idPersonaje")).thenReturn(1L);
        when(servicioTabernaMock.obtenerPersonajeDisponible()).thenReturn(PersonajeTaberna.HERRERO);
        when(servicioTabernaMock.obtenerVistaSegunPersonaje(PersonajeTaberna.HERRERO)).thenReturn("herrero.png");
        when(servicioTabernaMock.mostrarTaberna()).thenReturn("vistaTaberna");
        when(servicioTabernaMock.puedeInvitar(1L, PersonajeTaberna.HERRERO)).thenReturn(true);
        when(servicioTabernaMock.getCantidadCervezasInvitadas(1L, PersonajeTaberna.HERRERO)).thenReturn(3);
        when(servicioTabernaMock.obtenerCervezasInvitadasPorPersonaje(1L)).thenReturn(new HashMap<>());

        ModelAndView resultado = controladorTaberna.invitarTrago(sessionMock, "HERRERO");

        verify(servicioTabernaMock).invitarCerveza(1L, PersonajeTaberna.HERRERO);
        assertEquals("Has invitado un trago a HERRERO. Total de cervezas invitadas: 3", resultado.getModel().get("mensaje"));
        assertEquals("taberna", resultado.getViewName());
    }

    @Test
    public void queNoSePuedaInvitarATrabajadorIncorrecto() {
        when(sessionMock.getAttribute("idPersonaje")).thenReturn(1L);
        when(servicioTabernaMock.obtenerPersonajeDisponible()).thenReturn(PersonajeTaberna.GUARDIA);
        when(servicioTabernaMock.obtenerVistaSegunPersonaje(PersonajeTaberna.GUARDIA)).thenReturn("guardia.png");
        when(servicioTabernaMock.mostrarTaberna()).thenReturn("vistaTaberna");
        when(servicioTabernaMock.obtenerCervezasInvitadasPorPersonaje(1L)).thenReturn(new HashMap<>());

        ModelAndView resultado = controladorTaberna.invitarTrago(sessionMock, "HERRERO");

        assertEquals("No puedes invitar un trago a HERRERO en este momento.", resultado.getModel().get("mensaje"));
        assertEquals("taberna", resultado.getViewName());
    }

    @Test
    public void queLanceErrorSiElPersonajeNoExiste() {
        when(sessionMock.getAttribute("idPersonaje")).thenReturn(1L);
        when(servicioTabernaMock.obtenerPersonajeDisponible()).thenReturn(PersonajeTaberna.HERRERO);
        when(servicioTabernaMock.obtenerVistaSegunPersonaje(PersonajeTaberna.HERRERO)).thenReturn("herrero.png");
        when(servicioTabernaMock.mostrarTaberna()).thenReturn("vistaTaberna");
        when(servicioTabernaMock.obtenerCervezasInvitadasPorPersonaje(1L)).thenReturn(new HashMap<>());

        ModelAndView resultado = controladorTaberna.invitarTrago(sessionMock, "NO_EXISTE");

        assertEquals("Personaje no válido.", resultado.getModel().get("mensaje"));
        assertEquals("taberna", resultado.getViewName());
    }



    @Test
    public void queRedirijaAlLoginSiNoHaySesion() {
        when(sessionMock.getAttribute("idPersonaje")).thenReturn(null);

        ModelAndView modelAndView = controladorTaberna.mostrarTaberna(sessionMock, redirectAttributesMock);

        assertEquals("redirect:/login", modelAndView.getViewName());
    }

}
