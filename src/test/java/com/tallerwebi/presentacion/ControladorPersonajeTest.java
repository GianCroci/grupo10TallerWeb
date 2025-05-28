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
    Model model = new ExtendedModelMap();

    @BeforeEach
    public void init() {
        sessionMock = mock(HttpSession.class);
        personajeMockeado = mock(Personaje.class);
        servicioUsuarioMock = mock(ServicioUsuario.class);
        servicioPersonajeMock = mock(ServicioPersonaje.class);
        controladorPersonaje = new ControladorPersonaje(servicioPersonajeMock, servicioUsuarioMock);
    }





    @Test
    public void queSeCreeElPersonajeYMeDevuelvaUnaVistaConLaInformacion() {
        // Mock del model
        Model modelMock = mock(Model.class);

        // Instancia de lo que testeamos
        ControladorPersonaje controlador = new ControladorPersonaje(servicioPersonajeMock, servicioUsuarioMock);

        // Mock para session y personaje que uses
        HttpSession sessionMock = mock(HttpSession.class);
        Personaje personajeMockeado = new Personaje();
        personajeMockeado.setNombre("Rushard");
        personajeMockeado.setRol("Mago");

        // Si usás sesión, asegurate de que devuelva usuario logueado
        Usuario usuarioLogueado = new Usuario();
        usuarioLogueado.setPersonaje(personajeMockeado);
        when(sessionMock.getAttribute("usuarioLogueado")).thenReturn(usuarioLogueado);

        // Llamada al método bajo test
        ModelAndView vista = controlador.guardarPersonaje(personajeMockeado, sessionMock, modelMock);

        // Capturamos lo que se agregó al modelo
        ArgumentCaptor<Object> captor = ArgumentCaptor.forClass(Object.class);
        verify(modelMock).addAttribute(eq("datosPersonaje"), captor.capture());

        Object valorPasado = captor.getValue();

        // Assert del nombre de vista
        assertThat(vista, equalTo("home"));

        // Assert que datosPersonaje no sea null
        assertThat(valorPasado, is(notNullValue()));

        // Opcional: si querés comprobar que tiene el nombre correcto
        assertThat(valorPasado, instanceOf(Personaje.class));
        Personaje personajeEnModelo = (Personaje) valorPasado;
        assertThat(personajeEnModelo.getNombre(), equalTo("Rushard"));
    }

    @Test
    public void queHayaUnaVistaDeCreacionDePersonaje(){

        ModelAndView modelAndView = controladorPersonaje.creacionPersonaje();

        String vistaEsperada = "creacion-personaje";

        assertThat(vistaEsperada, equalTo(modelAndView.getViewName()));
    }
}
