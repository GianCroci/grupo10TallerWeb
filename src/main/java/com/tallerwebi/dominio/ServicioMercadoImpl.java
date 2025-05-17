package com.tallerwebi.dominio;

import org.springframework.stereotype.Service;

import java.util.List;

@Service("servicioMercado")
public class ServicioMercadoImpl implements ServicioMercado{

    @Override
    public Mercado mostrarMercado() {
        return null;
    }

    @Override
    public String procesarCompra(List<String> itemsSeleccionados) {
        if (itemsSeleccionados == null || itemsSeleccionados.isEmpty()) {
            return "No seleccionaste ningún objeto";
        }

        return "¡Compra realizada con éxito! Has comprado: " + String.join(", ", itemsSeleccionados);
    }
}
