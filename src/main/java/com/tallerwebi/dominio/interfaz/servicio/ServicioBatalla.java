package com.tallerwebi.dominio.interfaz.servicio;

import com.tallerwebi.dominio.entidad.Personaje;
import com.tallerwebi.dominio.excepcion.RivalNoEncontrado;
import com.tallerwebi.presentacion.BatallaDTO;
import com.tallerwebi.presentacion.EnemigoDTO;

import java.util.List;

public interface ServicioBatalla {
    Personaje buscarRival(Long idPersonaje) throws RivalNoEncontrado;

    List<EnemigoDTO> buscarEnemigosParaTablon();

    BatallaDTO comenzarBatalla(Long idPersonaje, Long idEnemigo);

    void realizarAccion(String accion, BatallaDTO batallaDTOActual);
}
