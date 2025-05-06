package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioTaberna;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControladorTaberna {

    private ServicioTaberna servicioTaberna;

    public ControladorTaberna(ServicioTaberna servicioTaberna) {

        this.servicioTaberna = servicioTaberna;
    }

    @GetMapping("/taberna")
    public ModelAndView mostrarTaberna() {
        ModelAndView modelAndView= new ModelAndView("taberna");
        modelAndView.addObject("taberna", servicioTaberna.mostrarTaberna());

        return modelAndView;

    }
}
