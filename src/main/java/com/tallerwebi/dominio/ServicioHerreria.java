package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.InventarioVacioException;
import com.tallerwebi.dominio.excepcion.NivelDeEquipamientoMaximoException;
import com.tallerwebi.dominio.excepcion.OroInsuficienteException;

import java.util.List;

public interface ServicioHerreria {

    void mejorarEquipamiento(Long equipamiento, Integer oro, Long idPersonaje) throws NivelDeEquipamientoMaximoException, OroInsuficienteException;

    List<Equipamiento> obtenerInventario(Long idPersonaje) throws InventarioVacioException;

    Integer obtenerOroDelPersonaje(Long idPersonaje);


   // Boolean sePuedeMejorar();
}
