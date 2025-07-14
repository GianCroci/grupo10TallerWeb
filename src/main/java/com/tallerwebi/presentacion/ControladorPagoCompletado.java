package com.tallerwebi.presentacion;

import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.tallerwebi.dominio.entidad.Pago;
import com.tallerwebi.dominio.interfaz.servicio.ServicioMercadoPago;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
public class ControladorPagoCompletado {

    private final ServicioMercadoPago servicioMercadoPago;

    @Autowired
    public ControladorPagoCompletado(ServicioMercadoPago servicioMercadoPago) {
        this.servicioMercadoPago = servicioMercadoPago;
    }

    @RequestMapping(path = "/pago-completado")
    public ModelAndView crearCheckout(HttpSession session, @RequestParam(required = false) String operacionMP) {
        ModelMap model = new ModelMap();
        Optional<String> mensajito = servicioMercadoPago.obtenerMensajeOperacion(operacionMP);
        if (mensajito.isPresent()) {
            model.put("mensajito", mensajito.get());
        }

        model.put("imgFondo", "/img/pago-completadont.png");
        if (operacionMP.equals("success")) {
            model.put("imgFondo", "/img/pago-completado.png");
        }

        return new ModelAndView("pago-completado", model);
    }
}
