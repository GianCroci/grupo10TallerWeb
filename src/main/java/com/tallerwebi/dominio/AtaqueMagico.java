package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidad.Enemigo;
import com.tallerwebi.dominio.entidad.Personaje;
import com.tallerwebi.dominio.interfaz.AccionCombate;
import com.tallerwebi.presentacion.BatallaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.util.Random;

@Component
public class AtaqueMagico implements AccionCombate {

    Random random;
    @Autowired
    public AtaqueMagico(Random random) {
        this.random = random;
    }

    @Override
    public void realizarAccionJugador(Personaje personaje, Enemigo enemigo, BatallaDTO batallaDTO) {
        Integer probabilidadDeEsquivarDelEnemigo = (enemigo.getEstadisticas().getAgilidad() - personaje.getEstadisticas().getAgilidad()) / 2;
        Integer numeroAleatorio = this.random.nextInt(99);
        Integer danioFinal = personaje.getEstadisticas().getInteligencia() - (enemigo.getEstadisticas().getArmadura() / 2);
        if (danioFinal < 0){
            danioFinal = 0;
        }
        if (batallaDTO.getUltimaAccionEnemigo().equalsIgnoreCase("defensa")){
            danioFinal /= 2;
        }
        if (batallaDTO.getUltimaAccionEnemigo().equalsIgnoreCase("esquivar")){
            probabilidadDeEsquivarDelEnemigo *= 2;
        }
        if (numeroAleatorio > probabilidadDeEsquivarDelEnemigo){
            batallaDTO.setTurno(personaje.getNombre() + " ha realizado un ataque magico y ha hecho " + danioFinal + " puntos de daño.");
            batallaDTO.setVidaActualEnemigo(batallaDTO.getVidaActualEnemigo() - danioFinal);
        }else {
            batallaDTO.setTurno(personaje.getNombre() + " ha intentado asestar un ataque magico a " + enemigo.getNombre() + " pero lo ha esquivado.");
        }
        batallaDTO.setUltimaAccionPersonaje(this.getClass().getSimpleName());
    }
}
