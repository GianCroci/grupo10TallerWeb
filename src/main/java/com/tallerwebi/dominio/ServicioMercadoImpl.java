package com.tallerwebi.dominio;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("servicioMercado")
public class ServicioMercadoImpl implements ServicioMercado {

    private final RepositorioMercado repositorioMercado;

    public ServicioMercadoImpl(RepositorioMercado repositorioMercado) {
        this.repositorioMercado = repositorioMercado;
    }


    private ServicioTaberna servicioTaberna;

    public ServicioMercadoImpl(@Lazy ServicioTaberna servicioTaberna) {

        this.servicioTaberna = servicioTaberna;

    }
    @Override
    public Mercado mostrarMercado() {
        repositorioMercado.inicializarProductos();
        return repositorioMercado.obtenerMercadoConProductos();
        //Mercado mercado = new Mercado();
        //mercado.getProductos().addAll(repositorioMercado.obtenerProductos());
        //return mercado;
    }

    @Override
    public String procesarCompra(List<String> itemsSeleccionados) {
        if (itemsSeleccionados == null || itemsSeleccionados.isEmpty()) {
            return "No seleccionaste ningún objeto";
        }
        return "¡Compra realizada con éxito! Has comprado: " + String.join(", ", itemsSeleccionados);
    }

    @Override
    public double aplicarDescuentoMercader(Equipamiento equipamiento) {
        if (servicioTaberna.obtenerBeneficioMercader() == true) {
            return equipamiento.getCostoVenta() * 0.8; // Aplica un descuento del 20%
        }else{
            return equipamiento.getCostoVenta() * 1.0; // No aplica descuento
        }
    }
}

