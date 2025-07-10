package com.tallerwebi.dominio.interfaz;

import com.tallerwebi.dominio.entidad.Enemigo;
import com.tallerwebi.dominio.entidad.Personaje;
import com.tallerwebi.presentacion.BatallaDTO;

public interface AccionCombate {

    void realizarAccionJugador(Personaje personajeObtenido, Enemigo enemigoObtenido, BatallaDTO batallaDTOActual);

}
