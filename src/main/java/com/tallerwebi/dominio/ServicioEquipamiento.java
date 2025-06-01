package com.tallerwebi.dominio;

import java.util.List;
import java.util.Optional;

public interface ServicioEquipamiento {
    List<Equipamiento> mostrarEquipamiento();

    Object equipar(Integer id);

    Optional<Equipamiento> buscarEquipamientoPorId(Integer id);

    //void darArmaEspecial();
}
