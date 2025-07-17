package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.entidad.Personaje;
import com.tallerwebi.dominio.entidad.Usuario;
import com.tallerwebi.dominio.interfaz.servicio.ServicioPersonaje;
import com.tallerwebi.dominio.interfaz.servicio.ServicioUsuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
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
    private RedirectAttributes redirectAttributesMock;
    private Long idPersonajeMock;

    @BeforeEach
    public void init() {
        redirectAttributesMock = mock(RedirectAttributes.class);
        sessionMock = mock(HttpSession.class);
        personajeMockeado = mock(Personaje.class);
        personajeDtoMockeado = mock(PersonajeDTO.class);
        usuarioMockeado = mock(Usuario.class);
        servicioUsuarioMock = mock(ServicioUsuario.class);
        servicioPersonajeMock = mock(ServicioPersonaje.class);
        controladorPersonaje = new ControladorPersonaje(servicioUsuarioMock, servicioPersonajeMock);
        idPersonajeMock = 1L;
        when(sessionMock.getAttribute("accesoCreacionPersonaje")).thenReturn(true);
        when(sessionMock.getAttribute("idPersonaje")).thenReturn(null);
    }

    @Test
    public void queHayaUnaVistaDeCreacionDePersonaje(){

        ModelAndView modelAndView = controladorPersonaje.creacionPersonaje(redirectAttributesMock, sessionMock);

        String vistaEsperada = "creacion-personaje";

        assertThat(vistaEsperada, equalTo(modelAndView.getViewName()));
    }

    @Test
    public void queEnLaVistaDeCreacionDePersonajeSeGuardeUnModelConUnPersonajeDTO(){

        ModelAndView modelAndView = controladorPersonaje.creacionPersonaje(redirectAttributesMock, sessionMock);


        String vistaEsperada = "creacion-personaje";
        String vistaobtenida = modelAndView.getViewName();

        assertThat(vistaobtenida, equalToIgnoringCase(vistaEsperada));
        assertThat(modelAndView.getModel().get("datosPersonaje"), notNullValue());
    }

    @Test
    public void queNoSePuedaAccederALaVistaCreacionPersonajeSiSeInicioSesionYSeTieneUnPersonajeCreado(){

        when(sessionMock.getAttribute("idPersonaje")).thenReturn(idPersonajeMock);
        ModelAndView modelAndView = controladorPersonaje.creacionPersonaje(redirectAttributesMock, sessionMock);

        String vistaEsperada = "redirect:/home";
        String vistaobtenida = modelAndView.getViewName();

        assertThat(vistaobtenida, equalToIgnoringCase(vistaEsperada));
        verify(redirectAttributesMock, times(1)).addFlashAttribute("error", "No se puede crear un segundo personaje");
    }

    @Test
    public void queNoSePuedaAccederALaVistaCreacionPersonajeSiElAccesoCreacionPersonajeEsNulo(){

        when(sessionMock.getAttribute("accesoCreacionPersonaje")).thenReturn(null);
        ModelAndView modelAndView = controladorPersonaje.creacionPersonaje(redirectAttributesMock, sessionMock);

        String vistaEsperada = "redirect:/login";
        String vistaobtenida = modelAndView.getViewName();

        assertThat(vistaobtenida, equalToIgnoringCase(vistaEsperada));
        verify(redirectAttributesMock, times(1)).addFlashAttribute("error", "No puede acceder a la creacion de personaje sin haberse registrado");
    }

    @Test
    public void queSeCreeElPersonajeYMeDevuelvaUnaVistaConLaInformacion(){
        //preparacion
        when(sessionMock.getAttribute(anyString())).thenReturn(usuarioMockeado);
        when(usuarioMockeado.getPersonaje()).thenReturn(personajeMockeado);
        when(personajeMockeado.getId()).thenReturn(1l);
        when(servicioPersonajeMock.crearPersonaje(any(), any(), any(), any())).thenReturn(personajeMockeado);
        when(personajeMockeado.getImagen()).thenReturn("guerrero.png");


        //ejecucion
        ModelAndView modelAndView = controladorPersonaje.guardarPersonaje(personajeDtoMockeado, sessionMock);

        String vistaEsperada = "redirect:/home";

        assertThat(vistaEsperada, equalTo(modelAndView.getViewName()));
        verify(servicioUsuarioMock, times(1)).setPersonaje(personajeMockeado, usuarioMockeado);

    }


}
