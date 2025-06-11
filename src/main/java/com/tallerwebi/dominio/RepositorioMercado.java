package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioMercado {
    List<Equipamiento> obtenerProductos();
    Equipamiento buscarPorId(Long id);
    Mercado obtenerMercadoConProductos();
}
