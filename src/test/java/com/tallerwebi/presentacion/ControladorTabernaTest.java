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
        public void queSePuedaVerLaTabernaSiElUsuarioEstaLogueado() {
            when(sessionMock.getAttribute("idPersonaje")).thenReturn(1L);

            ModelAndView mv = controladorTaberna.mostrarTaberna(sessionMock, redirectAttributesMock);

            assertEquals("taberna", mv.getViewName());
        }

        @Test
        public void queSePuedaVerLaTabernaLasCervezasDisponibleYLasEstadisticasDeCervezasInvitidas() {
            when(sessionMock.getAttribute("idPersonaje")).thenReturn(1L);
            when(servicioTabernaMock.obtenerCervezasDisponibles(1L)).thenReturn(2);
            when(servicioTabernaMock.mostrarTaberna()).thenReturn("vistaTaberna");
            when(servicioTabernaMock.obtenerCervezasInvitadasPorPersonaje(1L)).thenReturn(Map.of(
                    PersonajeTaberna.MERCADER, 0,
                    PersonajeTaberna.HERRERO, 2,
                    PersonajeTaberna.GUARDIA, 1
            ));

            ModelAndView modelAndView = controladorTaberna.mostrarTaberna(sessionMock, redirectAttributesMock);

            assertThat(modelAndView.getModel().get("cervezasDisponibles"), equalTo(2));
            assertThat(modelAndView.getViewName(), equalTo("taberna"));
            assertThat(modelAndView.getModel().get("personajes"), equalTo(Map.of(
                    PersonajeTaberna.MERCADER, 0,
                    PersonajeTaberna.HERRERO, 2,
                    PersonajeTaberna.GUARDIA, 1
            )));
        }

        @Test
        public void queSePuedaInvitarUnTragoAlPersonajeSeleccionado() {
            when(sessionMock.getAttribute("idPersonaje")).thenReturn(1L);
            when(servicioTabernaMock.getCantidadCervezasInvitadas(1L, PersonajeTaberna.HERRERO)).thenReturn(3);
            when(servicioTabernaMock.obtenerCervezasInvitadasPorPersonaje(1L)).thenReturn(new HashMap<>());
            when(servicioTabernaMock.obtenerCervezasDisponibles(1L)).thenReturn(1);

            ModelAndView resultado = controladorTaberna.invitarTrago(sessionMock, "HERRERO");

            verify(servicioTabernaMock).invitarCerveza(1L, PersonajeTaberna.HERRERO);
            assertEquals("Has invitado un trago a HERRERO. Total de cervezas invitadas: 3", resultado.getModel().get("mensaje"));
            assertEquals("taberna", resultado.getViewName());
        }


        @Test
        public void queSePuedaInvitarConUnaCervezaRestanteYLuegoQuedeEnCero() {
            when(sessionMock.getAttribute("idPersonaje")).thenReturn(1L);
            when(servicioTabernaMock.obtenerCervezasDisponibles(1L)).thenReturn(1).thenReturn(0);
            when(servicioTabernaMock.getCantidadCervezasInvitadas(1L, PersonajeTaberna.GUARDIA)).thenReturn(2);
            when(servicioTabernaMock.obtenerCervezasInvitadasPorPersonaje(1L)).thenReturn(new HashMap<>());

            ModelAndView resultado = controladorTaberna.invitarTrago(sessionMock, "GUARDIA");

            verify(servicioTabernaMock).invitarCerveza(1L, PersonajeTaberna.GUARDIA);
            assertEquals("Has invitado un trago a GUARDIA. Total de cervezas invitadas: 2", resultado.getModel().get("mensaje"));
            assertEquals(0, resultado.getModel().get("cervezasDisponibles"));
        }

        @Test
        public void queNoSePuedaInvitarACualquierPersonajeCuandoLaCantidadDeCervezasEsDeCero() {
            when(sessionMock.getAttribute("idPersonaje")).thenReturn(1L);
            when(servicioTabernaMock.obtenerCervezasDisponibles(1L)).thenReturn(0); // No hay cervezas disponibles

            ModelAndView resultado = controladorTaberna.invitarTrago(sessionMock, "HERRERO");

            assertEquals("No tenemos más cervezas para ti hoy. ¡Vuelve mañana!", resultado.getModel().get("mensaje"));
        }


        @Test
        public void queSeMuestreElPersonajeSeleccionadoLuegoDeInvitar() {
            when(sessionMock.getAttribute("idPersonaje")).thenReturn(1L);
            when(servicioTabernaMock.obtenerCervezasDisponibles(1L)).thenReturn(1);
            when(servicioTabernaMock.getCantidadCervezasInvitadas(1L, PersonajeTaberna.MERCADER)).thenReturn(1);
            when(servicioTabernaMock.obtenerCervezasInvitadasPorPersonaje(1L)).thenReturn(new HashMap<>());

            ModelAndView resultado = controladorTaberna.invitarTrago(sessionMock, "MERCADER");

            assertEquals("MERCADER", resultado.getModel().get("personajeSeleccionado"));
        }
        @Test
        public void queRedirijaAlLoginSiNoHaySesion() {
            when(sessionMock.getAttribute("idPersonaje")).thenReturn(null);

            ModelAndView modelAndView = controladorTaberna.mostrarTaberna(sessionMock, redirectAttributesMock);

            assertEquals("redirect:/login", modelAndView.getViewName());
        }

    }
