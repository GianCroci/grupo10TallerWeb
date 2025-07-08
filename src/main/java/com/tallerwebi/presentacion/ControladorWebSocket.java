package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.entidad.Ataque;
import com.tallerwebi.dominio.entidad.Batalla;
import com.tallerwebi.dominio.entidad.Mensaje;
import com.tallerwebi.dominio.entidad.Personaje;
import com.tallerwebi.dominio.interfaz.servicio.ServicioChat;
import com.tallerwebi.dominio.interfaz.servicio.ServicioPersonaje;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
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
    @Autowired
    private ServicioPersonaje servicioPersonaje;


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

    @MessageMapping("/batalla/iniciar/{salaId}")
    public void iniciarBatalla(@DestinationVariable String salaId) {
        Batalla batalla = servicioBatalla.obtenerBatalla(salaId); // o como accedas al mapa

        Personaje jugadorA = batalla.getJugadorA();
        Personaje jugadorB = batalla.getJugadorB();

        EstadoBatalla estado = new EstadoBatalla();
        estado.setMensaje("¡Comienza la batalla!");
        estado.setHpJugador(100); // o del que se conecta
        estado.setHpRival(100);
        estado.setTurno(jugadorA.getEsTuTurno() ? jugadorA.getNombre() : jugadorB.getNombre());

        messagingTemplate.convertAndSend("/sala/batalla/" + salaId, estado);
    }


    @MessageMapping("/batalla/{salaId}")
    public void atacar(@DestinationVariable String salaId, MensajeBatalla mensaje) {
        Personaje atacante = servicioPersonaje.buscarPersonaje(mensaje.getIdAtacante());
        Personaje defensor = servicioPersonaje.buscarPersonaje(mensaje.getIdDefensor());

        // Calcular daño y restar vida
        int daño = servicioBatalla.calcularDaño(atacante); // esto deberías tenerlo ya
        //defensor.restarVida(daño);

        // Cambiar turnos
        atacante.setEsTuTurno(false);
        defensor.setEsTuTurno(true);

        // Guardar cambios si es necesario
        // servicioPersonaje.actualizar(atacante);
        // servicioPersonaje.actualizar(defensor);

        // Preparar el objeto de respuesta
        EstadoBatalla estado = new EstadoBatalla();
        estado.setMensaje(atacante.getNombre() + " atacó a " + defensor.getNombre() + " y causó " + daño + " de daño.");
        estado.setHpJugador(100); // asumimos que tenés `getHp()`
        estado.setHpRival(100);
        estado.setTurno(defensor.getNombre()); // ahora le toca al defensor

        // Enviar estado a todos los conectados en la sala
        messagingTemplate.convertAndSend("/sala/batalla/" + salaId, estado);
    }





}
