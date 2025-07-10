package com.tallerwebi.dominio.interfaz.servicio;

import com.tallerwebi.dominio.entidad.Producto;

import java.util.List;

public interface ServicioProducto {
    List<Producto> obtenerTodosLosProductos();

    Producto buscarProductoPorId(Long idProducto);
}
