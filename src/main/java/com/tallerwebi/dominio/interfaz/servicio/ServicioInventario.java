package com.tallerwebi.dominio.interfaz.servicio;

import com.tallerwebi.dominio.Estadisticas;
import com.tallerwebi.dominio.entidad.Equipamiento;
import com.tallerwebi.dominio.entidad.Personaje;
import com.tallerwebi.dominio.excepcion.InventarioVacioException;

import java.util.List;

public interface ServicioInventario {

    List <Equipamiento> obtenerInventario(Long idPersonaje) throws InventarioVacioException;
    Equipamiento obtenerEquipamientoPorId(Long idPersonaje,Long idEquipamiento);
    Boolean equipar(Long idPersonaje,Long idEquipamiento) throws InventarioVacioException;
    void sumarEstadisticas(Estadisticas stats, Estadisticas equipo);
    void restarEstadisticas(Estadisticas stats, Estadisticas equipo);
    Equipamiento obtenerEquipamientoEquipadoPorTipo(Long idPersonaje, Class<? extends Equipamiento> tipoEquipamiento);
    List<Equipamiento> obtenerInventarioFiltradoPorTipo(Long idPersonaje, String tipo) throws InventarioVacioException;
    Equipamiento obtenerEquipoSeleccionado(List<Equipamiento> inventario, Long idPersonaje) throws InventarioVacioException;
    Equipamiento validarEquipoSeleccionadoEnFiltro(List<Equipamiento> inventarioFiltrado, Equipamiento equipoSeleccionado, List<Equipamiento> inventarioCompleto);
}
