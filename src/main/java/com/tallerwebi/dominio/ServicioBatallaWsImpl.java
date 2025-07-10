package com.tallerwebi.dominio;


import com.tallerwebi.dominio.entidad.Batalla;
import com.tallerwebi.dominio.entidad.Personaje;
import com.tallerwebi.dominio.interfaz.servicio.ServicioBatalla;
import com.tallerwebi.dominio.interfaz.servicio.ServicioBatallaWs;
import com.tallerwebi.dominio.interfaz.servicio.ServicioPersonaje;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class ServicioBatallaWsImpl implements ServicioBatallaWs {


    private final ServicioPersonaje servicioPersonaje;

    private final Map<String, Batalla> batallas;

    @Autowired
    public ServicioBatallaWsImpl(ServicioPersonaje servicioPersonaje) {
        this(servicioPersonaje, new HashMap<>());
    }

    // Constructor para tests
    public ServicioBatallaWsImpl(ServicioPersonaje servicioPersonaje, Map<String, Batalla> batallas) {
        this.servicioPersonaje = servicioPersonaje;
        this.batallas = batallas;
    }

    public Optional<String> buscarSalaPendientePara(Long idPersonaje) {
        for (String clave : batallas.keySet()) {
            Batalla b = batallas.get(clave);
            if (b.getJugadorA().getId().equals(idPersonaje) || b.getJugadorB().getId().equals(idPersonaje)) {
                return Optional.of(clave); // ej: "2_3"
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

        a.setEsTuTurno(true);
        b.setEsTuTurno(false);

        String nuevaClave = clave1;
        batallas.put(nuevaClave, new Batalla(a, b));
        return nuevaClave;
    }


    public Batalla obtenerBatalla(String salaId) {
        return batallas.get(salaId);
    }


}

