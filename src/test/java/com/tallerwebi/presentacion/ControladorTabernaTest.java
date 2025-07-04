package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.entidad.Personaje;
import com.tallerwebi.dominio.entidad.Taberna;
import com.tallerwebi.dominio.interfaz.servicio.ServicioTaberna;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;
import java.util.Map;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class ControladorTabernaTest {

    private ControladorTaberna controladorTaberna;

    private ServicioTaberna servicioTabernaMock;

    private Taberna taberna;

    private Personaje personajeMock;

    @BeforeEach
    public void init() {
        servicioTabernaMock = mock(ServicioTaberna.class);
        controladorTaberna = new ControladorTaberna(servicioTabernaMock);
        taberna = new Taberna();
        personajeMock = mock(Personaje.class);
    }



    @Test
    public void queSePuedaVerLaTaberna(){

        ModelAndView modelAndView= controladorTaberna.mostrarTaberna(personajeMock);

        String vistaEsperda= "taberna";

        assertThat(modelAndView.getViewName(), equalTo(vistaEsperda));
    }

    @Test
    public void queSePuedaVerLaTabernaConLaVistaDelMercader() {
        when(servicioTabernaMock.obtenerPersonajeDisponible()).thenReturn(PersonajeTaberna.MERCADER);
        when(servicioTabernaMock.obtenerVistaSegunPersonaje(PersonajeTaberna.MERCADER)).thenReturn("img/mercader.png");
        when(servicioTabernaMock.obtenerCervezasInvitadasPorPersonaje(personajeMock)).thenReturn(Map.of(
                PersonajeTaberna.MERCADER, 2,
                PersonajeTaberna.HERRERO, 0,
                PersonajeTaberna.GUARDIA, 1
        ));

        ModelAndView modelAndView = controladorTaberna.mostrarTaberna(personajeMock);

        assertThat(modelAndView.getViewName(), equalTo("taberna"));
        assertThat(modelAndView.getModel().get("personajeDisponible"), equalTo(PersonajeTaberna.MERCADER));
        assertThat(modelAndView.getModel().get("imagenParcial"), equalTo("img/mercader.png"));
        assertTrue(((Map<?, ?>) modelAndView.getModel().get("personajes")).containsKey(PersonajeTaberna.MERCADER));
    }

    @Test
    public void queSePuedaVerLaTabernaConLaVistaDelHerrero() {
        when(servicioTabernaMock.obtenerPersonajeDisponible()).thenReturn(PersonajeTaberna.HERRERO);
        when(servicioTabernaMock.obtenerVistaSegunPersonaje(PersonajeTaberna.HERRERO)).thenReturn("img/herrero.png");
        when(servicioTabernaMock.obtenerCervezasInvitadasPorPersonaje(personajeMock)).thenReturn(Map.of(
                PersonajeTaberna.MERCADER, 0,
                PersonajeTaberna.HERRERO, 3,
                PersonajeTaberna.GUARDIA, 1
        ));

        ModelAndView modelAndView = controladorTaberna.mostrarTaberna(personajeMock);

        assertThat(modelAndView.getViewName(), equalTo("taberna"));
        assertThat(modelAndView.getModel().get("personajeDisponible"), equalTo(PersonajeTaberna.HERRERO));
        assertThat(modelAndView.getModel().get("imagenParcial"), equalTo("img/herrero.png"));
        assertTrue(((Map<?, ?>) modelAndView.getModel().get("personajes")).containsKey(PersonajeTaberna.HERRERO));
    }

    @Test
    public void queSePuedaVerLaTabernaConLaVistaDelGuardia() {
        when(servicioTabernaMock.obtenerPersonajeDisponible()).thenReturn(PersonajeTaberna.GUARDIA);
        when(servicioTabernaMock.obtenerVistaSegunPersonaje(PersonajeTaberna.GUARDIA)).thenReturn("img/guardia.png");
        when(servicioTabernaMock.obtenerCervezasInvitadasPorPersonaje(personajeMock)).thenReturn(Map.of(
                PersonajeTaberna.MERCADER, 0,
                PersonajeTaberna.HERRERO, 1,
                PersonajeTaberna.GUARDIA, 5
        ));

        ModelAndView modelAndView = controladorTaberna.mostrarTaberna(personajeMock);

        assertThat(modelAndView.getViewName(), equalTo("taberna"));
        assertThat(modelAndView.getModel().get("personajeDisponible"), equalTo(PersonajeTaberna.GUARDIA));
        assertThat(modelAndView.getModel().get("imagenParcial"), equalTo("img/guardia.png"));
        assertTrue(((Map<?, ?>) modelAndView.getModel().get("personajes")).containsKey(PersonajeTaberna.GUARDIA));
    }

    @Test
    public void queSePuedaInvitarUnTragoAlPersonajeDisponible() {
        when(servicioTabernaMock.puedeInvitar(personajeMock, PersonajeTaberna.MERCADER)).thenReturn(true);
        when(servicioTabernaMock.obtenerPersonajeDisponible()).thenReturn(PersonajeTaberna.MERCADER);
        when(servicioTabernaMock.getCantidadCervezasInvitadas(personajeMock, PersonajeTaberna.MERCADER)).thenReturn(0);

        controladorTaberna.invitarTrago(personajeMock, "MERCADER");

        verify(servicioTabernaMock, times(1)).invitarCerveza(personajeMock, PersonajeTaberna.MERCADER);


    }

    @Test
    public void queNoSePuedaInvitarDosVecesEnUnDiaAlMismoPersonaje() {
        when(servicioTabernaMock.obtenerPersonajeDisponible()).thenReturn(PersonajeTaberna.MERCADER);
        when(servicioTabernaMock.puedeInvitar(personajeMock, PersonajeTaberna.MERCADER)).thenReturn(false);

        // Simulás el controller llamando a invitarTrago
        ModelAndView modelAndView = controladorTaberna.invitarTrago(personajeMock, "MERCADER");

        // Validás que el mensaje sea el esperado
        assertThat(modelAndView.getModel().get("mensaje"), equalTo("Ya se invitó a este personaje hoy."));
    }


}
