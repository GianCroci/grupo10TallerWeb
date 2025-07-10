package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidad.Pago;
import com.tallerwebi.dominio.interfaz.repositorio.RepositorioPago;
import com.tallerwebi.dominio.interfaz.servicio.ServicioPago;
import com.tallerwebi.dominio.interfaz.servicio.ServicioPersonaje;
import com.tallerwebi.dominio.interfaz.servicio.ServicioProducto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service("servicioPago")
@Transactional
public class ServicioPagoImpl implements ServicioPago {

    private final RepositorioPago repositorioPago;

    @Autowired
    public ServicioPagoImpl(RepositorioPago repositorioPago) {
        this.repositorioPago = repositorioPago;
    }

    @Override
    public void guardarPago(Pago pagoCreado) {
        repositorioPago.guardarPago(pagoCreado);
    }
}
