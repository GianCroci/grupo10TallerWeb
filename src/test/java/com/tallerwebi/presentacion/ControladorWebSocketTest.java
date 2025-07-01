package com.tallerwebi.presentacion;


import com.tallerwebi.dominio.Mensaje;
import com.tallerwebi.dominio.MensajeRecibido;
import com.tallerwebi.dominio.MensajeEnviado;
import com.tallerwebi.dominio.ServicioChat;
import com.tallerwebi.presentacion.ControladorWebSocket;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.*;

public class ControladorWebSocketTest {

    private ControladorWebSocket controladorWebSocket;

    private SimpMessagingTemplate messagingTemplateMock;
    private ServicioChat servicioChatMock;

    @BeforeEach
    public void init() {
        messagingTemplateMock = mock(SimpMessagingTemplate.class);
        servicioChatMock = mock(ServicioChat.class);
        controladorWebSocket = new ControladorWebSocket(messagingTemplateMock, servicioChatMock);

    }

    @Test
    public void queSePuedaEnviarMensajePrivado() {

        //preparacion

        MensajeRecibido mensajeMock = mock(MensajeRecibido.class);
        when(mensajeMock.getRemitente()).thenReturn("Gian");
        when(mensajeMock.getDestinatario()).thenReturn("Tomas");
        when(mensajeMock.getMensaje()).thenReturn("Hola Tomas");


        //ejecucion
        controladorWebSocket.enviarMensajePrivado(mensajeMock);

        //verificacion

        ArgumentCaptor<Mensaje> captorMensaje = ArgumentCaptor.forClass(Mensaje.class);
        verify(servicioChatMock).guardarMensaje(captorMensaje.capture());

        Mensaje mensajeGuardado = captorMensaje.getValue();
        assertThat(mensajeGuardado.getRemitente(), equalTo("Gian"));
        assertThat(mensajeGuardado.getDestinatario(), equalTo("Tomas"));
        assertThat(mensajeGuardado.getMensaje(), equalTo("Hola Tomas"));


        ArgumentCaptor<MensajeEnviado> captorRespuesta = ArgumentCaptor.forClass(MensajeEnviado.class);

        verify(messagingTemplateMock).convertAndSend(eq("/user/Tomas/queue/messages"), captorRespuesta.capture());

        MensajeEnviado respuestaEnviada = captorRespuesta.getValue();
        assertThat(respuestaEnviada.getRemitente(), equalTo("Gian"));
        assertThat(respuestaEnviada.getMensaje(), equalTo("Hola Tomas"));

    }

}
