package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.MensajeEnviado;
import com.tallerwebi.dominio.MensajeRecibido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class ControladorWebSocket {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat")
    public void enviarMensajePrivado(MensajeRecibido mensaje) {
        System.out.println("Mensaje recibido en backend:");
        System.out.println("Remitente: " + mensaje.getRemitente());
        System.out.println("Destinatario: " + mensaje.getDestinatario());
        System.out.println("Mensaje: " + mensaje.getMensaje());

        MensajeEnviado respuesta = new MensajeEnviado(mensaje.getMensaje(), mensaje.getRemitente());

        messagingTemplate.convertAndSend(
                "/user/" + mensaje.getDestinatario() + "/queue/messages",
                respuesta
        );



    }
}
