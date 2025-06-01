package com.tallerwebi.dominio;

import com.tallerwebi.presentacion.MejoraDto;

import java.util.List;

public interface ServicioHerreria {

    Boolean mejorarEquipamiento(Equipamiento equipamiento, Double oro);

    List<Equipamiento> obtenerInventario();


   // Boolean sePuedeMejorar();
}
