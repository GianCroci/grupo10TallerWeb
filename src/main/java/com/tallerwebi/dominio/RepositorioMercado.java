package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioMercado {
    List<Producto> obtenerProductos();
    Producto buscarPorId(Long id);
    void guardarProducto(Producto producto);
    void inicializarProductos();

    Mercado obtenerMercadoConProductos();
}
