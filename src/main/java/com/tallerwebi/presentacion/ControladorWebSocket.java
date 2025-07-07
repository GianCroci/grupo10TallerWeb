package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.EstadoBatalla;
import com.tallerwebi.dominio.ServicioBatallaWs;
import com.tallerwebi.dominio.entidad.Ataque;
import com.tallerwebi.dominio.entidad.Mensaje;
import com.tallerwebi.dominio.MensajeEnviado;
import com.tallerwebi.dominio.MensajeRecibido;
import com.tallerwebi.dominio.interfaz.servicio.ServicioChat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class ControladorWebSocket {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private ServicioChat servicioChat;

    @Autowired
    private ServicioBatallaWs servicioBatalla;


    public ControladorWebSocket(SimpMessagingTemplate messagingTemplate, ServicioChat servicioChat, ServicioBatallaWs servicioBatalla) {
        this.messagingTemplate = messagingTemplate;
        this.servicioChat = servicioChat;
        this.servicioBatalla = servicioBatalla;
    }

    @MessageMapping("/chat")
    public void enviarMensajePrivado(MensajeRecibido mensaje) {
        System.out.println("Recibido: " + mensaje.getMensaje());

        Mensaje nuevo = new Mensaje(
                mensaje.getRemitente(),
                mensaje.getDestinatario(),
                mensaje.getMensaje()
        );
        servicioChat.guardarMensaje(nuevo);

        MensajeEnviado respuesta = new MensajeEnviado(mensaje.getMensaje(), mensaje.getRemitente());

        messagingTemplate.convertAndSend(
                "/user/" + mensaje.getDestinatario() + "/queue/messages",
                respuesta
        );
    }

    @MessageMapping("/batalla/{salaId}")
    public void enviarAtaque(@DestinationVariable String salaId, Ataque ataque) {
        EstadoBatalla estado = servicioBatalla.procesarAtaque(salaId, ataque.getRemitente());

        messagingTemplate.convertAndSend("/sala/batalla/" + salaId, estado);
    }



}
