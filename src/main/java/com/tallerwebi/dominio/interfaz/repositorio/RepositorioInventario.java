package com.tallerwebi.dominio.interfaz.repositorio;

import com.tallerwebi.dominio.entidad.Equipamiento;

import java.util.List;

public interface RepositorioInventario {
    List<Equipamiento> obtenerInventario(Long idPersonaje);
    void modificarEquipamiento(Equipamiento equipamiento);
    Equipamiento obtenerEquipamientoPorId(Long idEquipamiento);
    void agregarEquipamiento(Equipamiento equipamiento);
    Equipamiento obtenerEquipoDePersonajePorId(Long idPersonaje,Long idEquipamiento);
}
