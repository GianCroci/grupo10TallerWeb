package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioHerreria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControladorHerreria {

    private final ServicioHerreria servicioHerreria;

    @Autowired
    public ControladorHerreria(ServicioHerreria servicioHerreria) {
        this.servicioHerreria = servicioHerreria;
    }

    @RequestMapping("/herreria")
    public ModelAndView irALaHerreria() {

        ModelMap model = new ModelMap();

            model.put("mejoraDto", new MejoraDto());

        return new ModelAndView("herreria", model);
    }

    @RequestMapping(path = "/mejorar-equipamiento", method = RequestMethod.POST)
    public ModelAndView mejorarEquipamiento(MejoraDto mejoraDto) {

        ModelMap model = new ModelMap();

        Boolean mejorado = servicioHerreria.mejorarEquipamiento(mejoraDto);

        if (mejorado) {
            model.put("mensaje", "El equipamiento se ha mejorado correctamente");
        }else {
            model.put("mensaje", "El equipamiento no se ha podido mejorar");
        }

        return new ModelAndView("herreria", model);
    }
}
