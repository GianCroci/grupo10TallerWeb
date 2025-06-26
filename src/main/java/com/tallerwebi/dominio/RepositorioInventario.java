package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioInventario {
    List<Equipamiento> obtenerInventario(Long idPersonaje);
    void modificarEquipamiento(Equipamiento equipamiento);
    Equipamiento obtenerEquipamientoPorId(Long idEquipamiento);
    void agregarEquipamiento(Equipamiento equipamiento);
    Equipamiento obtenerEquipoDePersonajePorId(Long idPersonaje,Long idEquipamiento);
}
