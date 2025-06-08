package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Producto;
import com.tallerwebi.dominio.ServicioMercado;
import com.tallerwebi.dominio.ServicioTaberna;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class ControladorMercado {

    private final ServicioMercado servicioMercado;

    public ControladorMercado(ServicioMercado servicioMercado) {
        this.servicioMercado = servicioMercado;
    }

    @GetMapping("/mercado")
    public ModelAndView mostrarMercado() {
        ModelAndView modelAndView = new ModelAndView("mercado");
        modelAndView.addObject("mercado", servicioMercado.mostrarMercado());

        modelAndView.addObject("compraExitosa", null);
        return modelAndView;
    }

    @PostMapping("/realizar-compra")
    public ModelAndView realizarCompra(@RequestParam(name = "itemsSeleccionados", required = false) List<String> itemsSeleccionados) {
        ModelMap model = new ModelMap();
        String mensajeCompra = servicioMercado.procesarCompra(itemsSeleccionados);
        model.put("compraExitosa", mensajeCompra);
        model.put("mercado", servicioMercado.mostrarMercado());
        return new ModelAndView("mercado", model);
    }
}
