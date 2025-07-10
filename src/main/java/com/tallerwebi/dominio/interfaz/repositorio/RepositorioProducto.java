package com.tallerwebi.dominio.interfaz.repositorio;

import com.tallerwebi.dominio.entidad.Producto;

import java.util.List;

public interface RepositorioProducto {

    Producto buscarProductoPorId(Long idProducto);

    List<Producto> obtenerTodosLosProductos();
}
