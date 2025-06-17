package com.tallerwebi.presentacion;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tallerwebi.dominio.MensajeEnviado;
import com.tallerwebi.dominio.MensajeRecibido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
public class ControladorWebSocket {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat")
    public void enviarMensajePrivado(MensajeRecibido mensaje, Principal principal) {
        String remitente = principal.getName(); // nombre del usuario logueado
        MensajeEnviado respuesta = new MensajeEnviado(mensaje.getMensaje(), remitente);

        messagingTemplate.convertAndSendToUser(
                mensaje.getDestinatario(),
                "/queue/messages",
                respuesta
        );
    }
}


