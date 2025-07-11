package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.entidad.Batalla;
import com.tallerwebi.dominio.entidad.Mensaje;
import com.tallerwebi.dominio.entidad.Personaje;
import com.tallerwebi.dominio.interfaz.servicio.ServicioBatallaWs;
import com.tallerwebi.dominio.interfaz.servicio.ServicioChat;
import com.tallerwebi.dominio.interfaz.servicio.ServicioPersonaje;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class ControladorWebSocket {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private ServicioChat servicioChat;

    @Autowired
    private ServicioBatallaWs servicioBatallaWs;
    @Autowired
    private ServicioPersonaje servicioPersonaje;


    public ControladorWebSocket(SimpMessagingTemplate messagingTemplate, ServicioChat servicioChat, ServicioBatallaWs servicioBatallaWs) {
        this.messagingTemplate = messagingTemplate;
        this.servicioChat = servicioChat;
        this.servicioBatallaWs = servicioBatallaWs;
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

    @MessageMapping("/batalla/iniciar/{salaId}")
    public void iniciarBatalla(@DestinationVariable String salaId) {
        System.out.println("📡 Iniciando batalla para sala: " + salaId);

        Batalla batalla = servicioBatallaWs.obtenerBatalla(salaId);
        Personaje jugadorA = batalla.getJugadorA();
        Personaje jugadorB = batalla.getJugadorB();

        EstadoBatalla estado = new EstadoBatalla();
        estado.setMensaje("¡Comienza la batalla!");
        estado.setHpJugador(batalla.getHpRestante(jugadorA.getNombre()));
        estado.setHpRival(batalla.getHpRestante(jugadorB.getNombre()));
        estado.setTurno(jugadorA.getNombre());

        estado.setNombreJugadorA(jugadorA.getNombre());
        estado.setNombreJugadorB(jugadorB.getNombre());

        messagingTemplate.convertAndSend("/sala/batalla/" + salaId, estado);
    }

    @MessageMapping("/batalla/{salaId}")
    public void atacar(@DestinationVariable String salaId, @Payload AtaqueDTO ataque) {
        Batalla batalla = servicioBatallaWs.obtenerBatalla(salaId);
        String remitente = ataque.getRemitente();

        EstadoBatalla estado = batalla.atacar(remitente);
        estado.setMiNombre(remitente);

        estado.setNombreJugadorA(batalla.getJugadorA().getNombre());
        estado.setNombreJugadorB(batalla.getJugadorB().getNombre());

        messagingTemplate.convertAndSend("/sala/batalla/" + salaId, estado);
    }


    @MessageMapping("/batalla/ganador")
    public void otorgarOroAGanador(@Payload GanadorDTO ganador) {
        Personaje personajeGanador = servicioPersonaje.buscarPersonaje(ganador.getIdGanador());

        if (personajeGanador != null) {
            int oroActual = personajeGanador.getOro() != null ? personajeGanador.getOro() : 0;
            personajeGanador.setOro(oroActual + 10);

            servicioPersonaje.modificar(personajeGanador);
            System.out.println("🏆 " + personajeGanador.getNombre() + " ganó y ahora tiene " + personajeGanador.getOro() + " oro.");
        }
    }



}
