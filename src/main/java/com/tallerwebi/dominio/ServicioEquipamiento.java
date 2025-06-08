package com.tallerwebi.dominio;

import java.util.List;
import java.util.Optional;

public interface ServicioEquipamiento {

    List<Equipamiento> mostrarEquipamiento();
    Optional<Equipamiento> mostrarPrimerEquipado();
    Optional<Equipamiento> buscarEquipamientoPorId(Integer id);
    Object equipar(Integer id);
    Boolean agregarEquipo(Equipamiento nuevo);
    //void darArmaEspecial();
}
