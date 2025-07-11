package com.tallerwebi.dominio.interfaz.servicio;

import com.tallerwebi.dominio.entidad.Batalla;

import java.util.Optional;

public interface ServicioBatallaWs {
    String obtenerOSalaExistente(Long idA, Long idB);
    Batalla obtenerBatalla(String salaId);

    Optional<String> buscarSalaPendientePara(Long id);
}
