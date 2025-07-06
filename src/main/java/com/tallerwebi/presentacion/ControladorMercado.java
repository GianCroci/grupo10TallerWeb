package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.entidad.Mercado;
import com.tallerwebi.dominio.interfaz.servicio.ServicioMercado;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class ControladorMercado {

    private final ServicioMercado servicioMercado;

    public ControladorMercado(ServicioMercado servicioMercado) {
        this.servicioMercado = servicioMercado;
    }

    @GetMapping("/mercado")
    public ModelAndView mostrarMercado(HttpSession session) {
        Long idPersonaje = (Long) session.getAttribute("idPersonaje");

        if (idPersonaje == null) {
            ModelAndView errorRedirect = new ModelAndView("redirect:/login");
            errorRedirect.addObject("error", "Debes iniciar sesión para acceder al mercado.");
            return errorRedirect;
        }

        Integer oroPersonaje = servicioMercado.obtenerOroDelPersonaje(idPersonaje);
        Mercado mercado = servicioMercado.mostrarMercado();

        ModelAndView modelAndView = new ModelAndView("mercado");
        modelAndView.addObject("mercado", mercado);
        modelAndView.addObject("compraExitosa", null);
        modelAndView.addObject("oroPersonaje", oroPersonaje);

        return modelAndView;
    }



    @PostMapping("/realizar-compra")
    public ModelAndView realizarCompra(
            @RequestParam(name = "itemsSeleccionados", required = false) List<String> itemsSeleccionados,
            HttpSession session) {

        ModelMap model = new ModelMap();
        Long idPersonaje = (Long) session.getAttribute("idPersonaje");

        if (idPersonaje == null) {
            model.put("error", "Debes iniciar sesión.");
            return new ModelAndView("redirect:/login");
        }

        String mensajeCompra = servicioMercado.procesarCompra(itemsSeleccionados, idPersonaje);

        Integer oroPersonaje = servicioMercado.obtenerOroDelPersonaje(idPersonaje);

        model.put("oroPersonaje", oroPersonaje);
        model.put("mercado", servicioMercado.mostrarMercado());

        if (mensajeCompra.startsWith("¡Compra realizada")) {
            model.put("compraExitosa", mensajeCompra);
            model.put("verInventario", true);
        } else {
            model.put("error", mensajeCompra);
        }

        return new ModelAndView("mercado", model);
    }



}
