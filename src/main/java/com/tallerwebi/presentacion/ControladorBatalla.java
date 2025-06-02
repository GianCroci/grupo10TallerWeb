package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Personaje;
import com.tallerwebi.dominio.ServicioBatalla;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControladorBatalla {

    private ServicioBatalla servicioBatalla;

    public ControladorBatalla(ServicioBatalla servicioBatalla) {
        this.servicioBatalla = servicioBatalla;
    }


    @GetMapping("/batalla")
    public ModelAndView irABatalla() {

        return new ModelAndView("batalla");
    }
}
