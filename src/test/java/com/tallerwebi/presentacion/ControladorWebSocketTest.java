package com.tallerwebi.presentacion;


import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.entidad.Batalla;
import com.tallerwebi.dominio.entidad.Mensaje;
import com.tallerwebi.dominio.entidad.Personaje;
import com.tallerwebi.dominio.interfaz.servicio.ServicioChat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ControladorWebSocketTest {

    private ControladorWebSocket controladorWebSocket;

    private SimpMessagingTemplate messagingTemplateMock;
    private ServicioChat servicioChatMock;
    private ServicioBatallaWsImpl servicioBatallaMock;
    private Batalla batallaMock;
    private Personaje personajeAMock;
    private Personaje personajeBMock;





    @BeforeEach
    public void init() {
        messagingTemplateMock = mock(SimpMessagingTemplate.class);
        servicioChatMock = mock(ServicioChat.class);
        servicioBatallaMock = mock(ServicioBatallaWsImpl.class);
        controladorWebSocket = new ControladorWebSocket(messagingTemplateMock, servicioChatMock, servicioBatallaMock);
        batallaMock = mock(Batalla.class);
        personajeAMock = mock(Personaje.class);
        personajeBMock = mock(Personaje.class);


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

    @Test
    public void queSeInicieUnaBatallaConDosJugadores(){
        //preparacion
        Long salaId = 3L;
        String salaIdStr = String.valueOf(salaId);

        when(personajeAMock.getNombre()).thenReturn("JugadorA");
        when(personajeBMock.getNombre()).thenReturn("JugadorB");
        when(servicioBatallaMock.obtenerBatalla(salaIdStr)).thenReturn(batallaMock);
        when(batallaMock.getJugadorA()).thenReturn(personajeAMock);
        when(batallaMock.getJugadorB()).thenReturn(personajeBMock);
        when(batallaMock.getHpRestante("JugadorA")).thenReturn(100);
        when(batallaMock.getHpRestante("JugadorB")).thenReturn(100);

        //ejecucion
        controladorWebSocket.iniciarBatalla(salaIdStr);

        //verificacion
        ArgumentCaptor<EstadoBatalla> estadoCaptor = ArgumentCaptor.forClass(EstadoBatalla.class);
        verify(messagingTemplateMock, times(1))
                .convertAndSend(eq("/sala/batalla/" + salaIdStr), estadoCaptor.capture());

        EstadoBatalla estadoCapturado = estadoCaptor.getValue();
        assertEquals("¡Comienza la batalla!", estadoCapturado.getMensaje());
        assertEquals(100, estadoCapturado.getHpJugador());
        assertEquals(100, estadoCapturado.getHpRival());
        assertEquals("JugadorA", estadoCapturado.getTurno());
        assertEquals("JugadorA", estadoCapturado.getNombreJugadorA());
        assertEquals("JugadorB", estadoCapturado.getNombreJugadorB());
    }

    @Test
    public void queSeEjecuteUnAtaqueEnBatalla(){
        //preparacion
        Long salaId = 3L;
        String salaIdStr = String.valueOf(salaId);
        String nombreRemitente = "JugadorA";

        AtaqueDTO ataqueDTO = new AtaqueDTO();
        ataqueDTO.setRemitente(nombreRemitente);

        when(personajeAMock.getNombre()).thenReturn("JugadorA");
        when(personajeBMock.getNombre()).thenReturn("JugadorB");
        when(servicioBatallaMock.obtenerBatalla(salaIdStr)).thenReturn(batallaMock);
        when(batallaMock.getJugadorA()).thenReturn(personajeAMock);
        when(batallaMock.getJugadorB()).thenReturn(personajeBMock);

        EstadoBatalla estado = new EstadoBatalla();
        estado.setMensaje("¡JugadorA atacó!");
        estado.setHpJugador(80);
        estado.setHpRival(70);
        estado.setTurno("JugadorB");

        when(batallaMock.atacar(nombreRemitente)).thenReturn(estado);

        //ejecucion
        controladorWebSocket.atacar(salaIdStr, ataqueDTO);

        //verificacion
        ArgumentCaptor<EstadoBatalla> estadoCaptor = ArgumentCaptor.forClass(EstadoBatalla.class);
        verify(messagingTemplateMock, times(1))
                .convertAndSend(eq("/sala/batalla/" + salaIdStr), estadoCaptor.capture());

        EstadoBatalla estadoCapturado = estadoCaptor.getValue();

        // Verificar las propiedades que vienen del estado
        assertEquals("¡JugadorA atacó!", estadoCapturado.getMensaje());
        assertEquals(80, estadoCapturado.getHpJugador());
        assertEquals(70, estadoCapturado.getHpRival());
        assertEquals("JugadorB", estadoCapturado.getTurno());

        // Verificar las propiedades que se setean en el metodo
        assertEquals("JugadorA", estadoCapturado.getMiNombre());
        assertEquals("JugadorA", estadoCapturado.getNombreJugadorA());
        assertEquals("JugadorB", estadoCapturado.getNombreJugadorB());


        verify(batallaMock, times(1)).atacar(nombreRemitente);
    }

}
