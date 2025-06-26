package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Mensaje;
import com.tallerwebi.dominio.MensajeEnviado;
import com.tallerwebi.dominio.MensajeRecibido;
import com.tallerwebi.dominio.ServicioChat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class ControladorWebSocket {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private ServicioChat servicioChat;

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

}
