package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.InventarioVacioException;

import java.util.List;

public interface ServicioInventario {

    List <Equipamiento> obtenerInventario(Long idPersonaje) throws InventarioVacioException;
    Equipamiento obtenerPrimerEquipado(Long idPersonaje) throws InventarioVacioException;
    Equipamiento obtenerEquipamientoPorId(Long idPersonaje,Long idEquipamiento);
    Boolean equipar(Long idPersonaje,Long idEquipamiento) throws InventarioVacioException;
    void sumarEstadisticas(Personaje personaje, Estadisticas stats);
    //void darArmaEspecial();
}
