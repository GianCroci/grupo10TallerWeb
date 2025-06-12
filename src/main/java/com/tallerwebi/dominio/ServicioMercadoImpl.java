package com.tallerwebi.dominio;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("servicioMercado")
public class ServicioMercadoImpl implements ServicioMercado {

    private final RepositorioMercado repositorioMercado;

    public ServicioMercadoImpl(RepositorioMercado repositorioMercado) {
        this.repositorioMercado = repositorioMercado;
    }


    @Override
    public Mercado mostrarMercado() {
        Mercado mercado = repositorioMercado.obtenerMercadoConProductos();
        return mercado;
    }


    @Override
    public String procesarCompra(List<String> itemsSeleccionados) {
        if (itemsSeleccionados == null || itemsSeleccionados.isEmpty()) {
            return "No seleccionaste ningún objeto";
        }
        return "¡Compra realizada con éxito! Has comprado: " + String.join(", ", itemsSeleccionados);
    }
}

