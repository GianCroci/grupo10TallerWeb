package com.tallerwebi.dominio;

import java.util.List;

public interface ServicioInventario {

    List<Equipamiento> mostrarEquipamiento();
    Equipamiento mostrarPrimerEquipado();
    Equipamiento buscarEquipamientoPorId(Long id);
    Object equipar(Long id);
    Boolean agregarEquipo(Equipamiento nuevo);
    //void darArmaEspecial();
}
