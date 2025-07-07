package com.tallerwebi.dominio;


import com.tallerwebi.dominio.entidad.Batalla;
import com.tallerwebi.dominio.entidad.Personaje;
import com.tallerwebi.dominio.interfaz.servicio.ServicioPersonaje;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class ServicioBatallaWs {

    @Autowired
    private ServicioPersonaje servicioPersonaje;

    private final Map<String, Batalla> batallas = new HashMap<>();

    // Crear una batalla nueva
    public void crearBatalla(String salaId, Personaje jugador, Personaje rival) {
        if (!batallas.containsKey(salaId)) {
            batallas.put(salaId, new Batalla(jugador, rival));
        }
    }


    // Procesar un ataque enviado por el jugador
    public EstadoBatalla procesarAtaque(String salaId, String nombreAtacante) {
        Batalla batalla = batallas.get(salaId);
        if (batalla == null) {
            throw new IllegalStateException("La batalla no existe.");
        }

        if (!batalla.puedeAtacar(nombreAtacante)) {
            throw new IllegalStateException("No podés atacar ahora.");
        }

        return batalla.atacar(nombreAtacante);
    }

    public Optional<String> buscarSalaPendientePara(Long idPersonaje) {
        for (Map.Entry<String, Batalla> entrada : batallas.entrySet()) {
            String salaId = entrada.getKey();
            Batalla batalla = entrada.getValue();

            // El personaje participa en esta batalla
            if (salaId.contains(idPersonaje.toString())) {
                // ¿Este personaje no ha atacado todavía?
                if (!batalla.haAtacado(idPersonaje)) {
                    return Optional.of(salaId);
                }
            }
        }
        return Optional.empty();
    }


    public String obtenerOSalaExistente(Long idA, Long idB) {
        String clave1 = idA + "_" + idB;
        String clave2 = idB + "_" + idA;

        // Verificar si ya existe la sala
        if (batallas.containsKey(clave1)) {
            return clave1;
        }
        if (batallas.containsKey(clave2)) {
            return clave2;
        }

        // Si no existe, la creás
        Personaje a = servicioPersonaje.buscarPersonaje(idA);
        Personaje b = servicioPersonaje.buscarPersonaje(idB);

        String nuevaClave = clave1;
        batallas.put(nuevaClave, new Batalla(a, b));
        return nuevaClave;
    }


}

