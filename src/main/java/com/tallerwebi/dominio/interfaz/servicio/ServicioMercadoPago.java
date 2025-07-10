package com.tallerwebi.dominio.interfaz.servicio;

import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.tallerwebi.presentacion.Pedido;

import java.util.Optional;

public interface ServicioMercadoPago {

    String crearCheckout(Pedido pedido) throws MPException, MPApiException;

    Optional<String> obtenerMensajeOperacion(String operacionMP);
}
