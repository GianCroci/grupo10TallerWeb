package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.PersonajeTaberna;
import com.tallerwebi.dominio.ServicioTaberna;

import com.tallerwebi.dominio.ServicioTabernaImpl;
import com.tallerwebi.dominio.Taberna;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
public class ControladorTabernaTest {

    private ControladorTaberna controladorTaberna;
    private ServicioTaberna servicioTaberna;

    private Taberna taberna;

    @BeforeEach
    public void init() {
        servicioTaberna = mock(ServicioTaberna.class);
        controladorTaberna = new ControladorTaberna(servicioTaberna);
        taberna = new Taberna();
    }


    @Test
    public void queSePuedaVerLaTaberna(){

        ModelAndView modelAndView= controladorTaberna.mostrarTaberna();

        assertThat(modelAndView.getViewName(), equalTo("taberna"));
    }


    @Test
    public void queEntre00amy12amCargueLaImagenDelMercaderYQueSoloTengaEseOption() {

        when(servicioTaberna.obtenerPersonajeDisponible()).thenReturn(PersonajeTaberna.MERCADER);
        when(servicioTaberna.obtenerVistaSegunPersonaje(PersonajeTaberna.MERCADER)).thenReturn("mercader.png");

        ModelAndView modelAndView = controladorTaberna.mostrarTaberna();

        assertThat(modelAndView.getModel().get("personajeDisponible"), equalTo(PersonajeTaberna.MERCADER));
        assertThat(modelAndView.getModel().get("imagenParcial"), equalTo("mercader.png"));

    }

    @Test
    public void queEntre12amy7pmCargueLaImagenDelHerreroYQueSoloTengaEseOption() {

        when(servicioTaberna.obtenerPersonajeDisponible()).thenReturn(PersonajeTaberna.HERRERO);
        when(servicioTaberna.obtenerVistaSegunPersonaje(PersonajeTaberna.HERRERO)).thenReturn("herrero.png");

        ModelAndView modelAndView = controladorTaberna.mostrarTaberna();

        assertThat(modelAndView.getModel().get("personajeDisponible"), equalTo(PersonajeTaberna.HERRERO));
        assertThat(modelAndView.getModel().get("imagenParcial"), equalTo("herrero.png"));

    }

    @Test
    public void queEntre7pmy12pmCargueLaImagenDelGuardiaYQueSoloTengaEseOption() {

        when(servicioTaberna.obtenerPersonajeDisponible()).thenReturn(PersonajeTaberna.GUARDIA);
        when(servicioTaberna.obtenerVistaSegunPersonaje(PersonajeTaberna.GUARDIA)).thenReturn("guardia.png");

        ModelAndView modelAndView = controladorTaberna.mostrarTaberna();

        assertThat(modelAndView.getModel().get("personajeDisponible"), equalTo(PersonajeTaberna.GUARDIA));
        assertThat(modelAndView.getModel().get("imagenParcial"), equalTo("guardia.png"));


    }

    @Test
    public void queSePuedaInvitarUnTragoAlPersonajeDisponible() {

        when(servicioTaberna.obtenerPersonajeDisponible()).thenReturn(PersonajeTaberna.MERCADER);
        when(servicioTaberna.getCervezasInvitadas(PersonajeTaberna.MERCADER)).thenReturn(1);

        ModelAndView modelAndView = controladorTaberna.invitarTrago("MERCADER");

        assertThat(modelAndView.getModel().get("mensaje"), equalTo("Has invitado un trago a MERCADER. Total de cervezas invitadas:1"));
    }

    @Test
    public void queNoSePuedaInvitarDosVecesEnUnDiaAlMismoPersonaje() {

        when(servicioTaberna.obtenerPersonajeDisponible()).thenReturn(PersonajeTaberna.MERCADER);
        when(servicioTaberna.getCervezasInvitadas(PersonajeTaberna.MERCADER)).thenReturn(1);
        when(servicioTaberna.invitarTrago(PersonajeTaberna.MERCADER)).thenThrow(new IllegalArgumentException("Ya invitaste a MERCADER hoy."));

        ModelAndView modelAndView = controladorTaberna.invitarTrago("MERCADER");


        assertThat(modelAndView.getModel().get("mensaje"), equalTo("Ya invitaste a MERCADER hoy."));
    }









}
