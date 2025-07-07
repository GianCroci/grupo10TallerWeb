package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidad.Enemigo;
import com.tallerwebi.dominio.entidad.Personaje;
import com.tallerwebi.dominio.interfaz.AccionCombate;
import com.tallerwebi.presentacion.BatallaDTO;

public class Esquivar implements AccionCombate {
    @Override
    public void realizarAccionJugador(Personaje personaje, Enemigo enemigo, BatallaDTO batallaDTO) {
        batallaDTO.setUltimaAccionPersonaje(this.getClass().getSimpleName());
        batallaDTO.setTurno(personaje.getNombre() + " se prepara para esquivar el siguiente golpe.");
    }
}
