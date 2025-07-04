package com.tallerwebi.dominio.interfaz.servicio;

import com.tallerwebi.dominio.entidad.Mercado;

import java.util.List;

public interface ServicioMercado {
    Mercado mostrarMercado();

    String procesarCompra(List<String> itemsSeleccionados, Long idPersonaje);

    Integer obtenerOroDelPersonaje(Long idPersonaje);
}
