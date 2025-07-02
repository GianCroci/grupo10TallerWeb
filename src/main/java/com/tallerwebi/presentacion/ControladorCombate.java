package com.tallerwebi.presentacion;
import com.tallerwebi.dominio.EstadoBatalla;
import com.tallerwebi.dominio.MovimientoEnviado;
import com.tallerwebi.dominio.MovimientoRecibido;
import com.tallerwebi.dominio.Personaje;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;


import java.util.*;

@Controller
public class ControladorCombate {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    private Queue<String> jugadoresEsperando = new LinkedList<>();
    private Map<String, EstadoBatalla> batallas = new HashMap<>();

    private List<String> accionesDisponibles = Arrays.asList("atacar", "defender", "poción");


    @GetMapping("/combate")
    public ModelAndView vistaCombate() {
        ModelMap model = new ModelMap();
        model.addAttribute("acciones", accionesDisponibles);
        return new ModelAndView("combate", model);
    }

    @MessageMapping("/unirse")
    public void unirse(String jugador) {
        synchronized (jugadoresEsperando) {
            if (!jugadoresEsperando.isEmpty()) {
                String oponente = jugadoresEsperando.poll();
                EstadoBatalla batalla = new EstadoBatalla(jugador, oponente);

                batallas.put(jugador, batalla);
                batallas.put(oponente, batalla);

                enviarTurno(jugador);
                enviarEsperar(oponente);
            } else {
                jugadoresEsperando.offer(jugador);
                messagingTemplate.convertAndSendToUser(jugador, "/queue/combate",
                        new MovimientoEnviado("Esperando oponente...", null, false));
            }
        }
    }

    @MessageMapping("/accion")
    public void procesarAccion(MovimientoRecibido movimiento) {
        EstadoBatalla batalla = batallas.get(movimiento.getJugador());
        if (batalla == null || !batalla.esTurnoDe(movimiento.getJugador())) return;

        String oponente = batalla.getOponente(movimiento.getJugador());
        String mensaje = movimiento.getJugador() + " usó " + movimiento.getAccion() + " contra " + oponente;

        // Enviar resultado a ambos
        messagingTemplate.convertAndSendToUser(movimiento.getJugador(), "/queue/combate",
                new MovimientoEnviado(mensaje, null, false));
        messagingTemplate.convertAndSendToUser(oponente, "/queue/combate",
                new MovimientoEnviado(mensaje, null, false));

        batalla.cambiarTurno();
        enviarTurno(batalla.turnoActual);
        enviarEsperar(batalla.getOponente(batalla.turnoActual));
    }

    private void enviarTurno(String jugador) {
        messagingTemplate.convertAndSendToUser(jugador, "/queue/combate",
                new MovimientoEnviado("¡Tu turno!", accionesDisponibles, true));
    }

    private void enviarEsperar(String jugador) {
        messagingTemplate.convertAndSendToUser(jugador, "/queue/combate",
                new MovimientoEnviado("Esperando el turno del oponente...", null, false));
    }
}
