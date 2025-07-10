package com.tallerwebi.dominio.interfaz.servicio;

import com.tallerwebi.dominio.Estadisticas;
import com.tallerwebi.dominio.entidad.Equipamiento;
import com.tallerwebi.dominio.entidad.Personaje;
import com.tallerwebi.dominio.excepcion.InventarioVacioException;

import java.util.List;

public interface ServicioInventario {

    List <Equipamiento> obtenerInventario(Long idPersonaje) throws InventarioVacioException;
    Equipamiento obtenerPrimerEquipado(Long idPersonaje) throws InventarioVacioException;
    Equipamiento obtenerEquipamientoPorId(Long idPersonaje,Long idEquipamiento);
    Boolean equipar(Long idPersonaje,Long idEquipamiento) throws InventarioVacioException;
    void sumarEstadisticas(Personaje personaje, Estadisticas stats);
    void restarEstadisticas(Personaje personaje, Estadisticas stats);
    Equipamiento obtenerEquipamientoEquipadoPorTipo(Long idPersonaje, Class<? extends Equipamiento> tipoEquipamiento);
    //void darArmaEspecial();
}
