package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioMercado;
import com.tallerwebi.dominio.ServicioTaberna;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControladorMercado {

    private ServicioMercado servicioMercado;

    public ControladorMercado(ServicioMercado servicioMercado) {

        this.servicioMercado = servicioMercado;
    }

    @GetMapping("/mercado")
    public ModelAndView mostrarMercado() {
        ModelAndView modelAndView= new ModelAndView("mercado");
        modelAndView.addObject("mercado", servicioMercado.mostrarMercado());

        return modelAndView;

    }
}