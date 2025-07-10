package com.tallerwebi.presentacion;

import com.mercadopago.client.preference.PreferenceBackUrlsRequest;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.preference.Preference;
import com.tallerwebi.dominio.interfaz.servicio.ServicioMercadoPago;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
public class ControladorMercadoPago {

    private final ServicioMercadoPago servicioMercadoPago;

    @Autowired
    public ControladorMercadoPago(ServicioMercadoPago servicioMercadoPago) {
        this.servicioMercadoPago = servicioMercadoPago;
    }

    @RequestMapping(path = "/mp", method = RequestMethod.POST,  produces = "text/plain", consumes = "application/json")
    public String crearCheckout(@RequestBody Pedido pedido){

        String preferenceId = null;
        try {
            preferenceId = servicioMercadoPago.crearCheckout(pedido);
        } catch (MPException | MPApiException e) {
            throw new RuntimeException(e);
        }

        return preferenceId;
    }

    @RequestMapping(path = "/pago/success")
    public ModelAndView pagoValidadoAprobado(){

        return new ModelAndView("redirect:http://localhost:8080/spring/home?operacionMP=success");
    }

    @RequestMapping(path = "/pago/failure")
    public ModelAndView pagoValidadoFallido() {

        return new ModelAndView("redirect:http://localhost:8080/spring/home?operacionMP=failure");
    }

    @RequestMapping(path = "/pago/pending")
    public ModelAndView pagoValidadoPendiente() {

        return new ModelAndView("redirect:http://localhost:8080/spring/home?operacionMP=pending");
    }
}
