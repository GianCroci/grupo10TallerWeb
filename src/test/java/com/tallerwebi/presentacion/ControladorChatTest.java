package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Mensaje;
import com.tallerwebi.dominio.Personaje;
import com.tallerwebi.dominio.ServicioChat;
import com.tallerwebi.dominio.ServicioPersonaje;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ControladorChatTest {

    private HttpSession sessionMock;
    private ServicioPersonaje servicioPersonajeMock;
    private ServicioChat servicioChatMock;
    private Personaje personajeMockeado;
    private Personaje amigoMockeado;
    private ControladorChat controladorChat;



    @BeforeEach
    public void init(){
        sessionMock = mock(HttpSession.class);
        servicioPersonajeMock = mock(ServicioPersonaje.class);
        servicioChatMock = mock(ServicioChat.class);
        personajeMockeado = mock(Personaje.class);
        amigoMockeado = mock(Personaje.class);
        controladorChat = new ControladorChat(servicioPersonajeMock, servicioChatMock);

    }

    @Test
    public void queSePuedaChatearConUnAmigo(){
        //preparacion
        Long idAmigo = 2L;
        when(personajeMockeado.getNombre()).thenReturn("Gian");
        when(amigoMockeado.getNombre()).thenReturn("Tomas");
        when(sessionMock.getAttribute("idPersonaje")).thenReturn(1L);
        when(servicioPersonajeMock.buscarPersonaje(1L)).thenReturn(personajeMockeado);
        when(servicioPersonajeMock.buscarPersonaje(idAmigo)).thenReturn(amigoMockeado);
        Mensaje mensaje1 = new Mensaje("Gian", "Tomas", "Hola Tomas");
        Mensaje mensaje2 = new Mensaje("Tomas", "Gian", "Hola Gian");
        List<Mensaje> historial = List.of(mensaje1, mensaje2);
        when(servicioChatMock.obtenerHistorial(personajeMockeado.getNombre(), amigoMockeado.getNombre())).thenReturn(historial);

        //ejecucion
        ModelAndView modelAndView = controladorChat.chatearConAmigo(sessionMock, idAmigo);

        //verificacion
        String vistaEsperada = "sala-chat";
        assertThat(vistaEsperada, equalTo(modelAndView.getViewName()));
        assertThat(modelAndView.getModel().get("usuario"), equalTo(personajeMockeado.getNombre()));
        assertThat(modelAndView.getModel().get("destinatario"), equalTo(amigoMockeado.getNombre()));
        assertThat(modelAndView.getModel().get("historial"), equalTo(historial));
    }


}
