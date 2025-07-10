package com.tallerwebi.dominio.interfaz.servicio;

import com.tallerwebi.dominio.entidad.Batalla;

public interface ServicioBatallaWs {
    String obtenerOSalaExistente(Long idA, Long idB);
    Batalla obtenerBatalla(String salaId);
}
