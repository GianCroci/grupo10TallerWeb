package com.tallerwebi.dominio;

import java.util.List;

public interface ServicioMercado {
    Mercado mostrarMercado();

    String procesarCompra(List<String> itemsSeleccionados, Long idPersonaje);

    Integer obtenerOroDelPersonaje(Long idPersonaje);
}
