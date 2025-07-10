package com.tallerwebi.dominio.interfaz.repositorio;

import com.tallerwebi.dominio.entidad.Enemigo;

import java.util.List;

public interface RepositorioEnemigo {
    List<Enemigo> obtenerEnemigos();

    Enemigo obtenerEnemigoPorId(Long idEnemigoMock);
}
