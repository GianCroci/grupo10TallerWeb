package com.tallerwebi.dominio;

import java.util.List;

public interface ServicioInventario {

    List <Equipamiento> obtenerInventario(Long idPersonaje);
    Equipamiento obtenerPrimerEquipado(Long idPersonaje);
    Equipamiento obtenerEquipamientoPorId(Long idPersonaje,Long idEquipamiento);
    Boolean equipar(Long idPersonaje,Long idEquipamiento);
    void sumarEstadisticas(Personaje personaje, Estadisticas stats);
    //void darArmaEspecial();
}
