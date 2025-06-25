package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Equipamiento;
import com.tallerwebi.dominio.Mercado;
import com.tallerwebi.dominio.ServicioMercado;
import com.tallerwebi.dominio.ServicioTaberna;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class ControladorMercado {

    private final ServicioMercado servicioMercado;

    public ControladorMercado(ServicioMercado servicioMercado) {
        this.servicioMercado = servicioMercado;
    }

    @GetMapping("/mercado")
    public ModelAndView mostrarMercado(HttpSession session, RedirectAttributes redirectAttributes) {
        Long idPersonaje = (Long) session.getAttribute("idPersonaje");
        if (idPersonaje == null) {
            redirectAttributes.addFlashAttribute("error", "Debes iniciar sesión para acceder al mercado.");
            return new ModelAndView("redirect:/login");
        }

        Mercado mercado = servicioMercado.mostrarMercado();
        ModelAndView modelAndView = new ModelAndView("mercado");
        modelAndView.addObject("mercado", mercado);
        modelAndView.addObject("compraExitosa", null);
        return modelAndView;
    }


    @PostMapping("/realizar-compra")
    public ModelAndView realizarCompra(
            @RequestParam(name = "itemsSeleccionados", required = false) List<String> itemsSeleccionados,
            HttpSession session) {

        ModelMap model = new ModelMap();
        Long idPersonaje = (Long) session.getAttribute("idPersonaje");

        if (itemsSeleccionados == null || itemsSeleccionados.isEmpty()) {
            model.put("error", "No seleccionaste ningún objeto.");
            model.put("mercado", servicioMercado.mostrarMercado());
            return new ModelAndView("mercado", model);
        }

        String mensajeCompra = servicioMercado.procesarCompra(itemsSeleccionados, idPersonaje);
        model.put("compraExitosa", mensajeCompra);
        model.put("verInventario", true);
        model.put("mercado", servicioMercado.mostrarMercado());

        return new ModelAndView("mercado", model);
    }


}
