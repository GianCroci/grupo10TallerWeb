package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Equipamiento;
import com.tallerwebi.dominio.Probando;
import com.tallerwebi.dominio.ServicioHerreria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

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

            List<Equipamiento> inventario = servicioHerreria.obtenerInventario();
            model.put("inventario", inventario);

        return new ModelAndView("herreria", model);
    }

    @RequestMapping(path = "/mejorar-equipamiento", method = RequestMethod.POST)
    public ModelAndView mejorarEquipamiento(@ModelAttribute MejoraDto mejoraDto) {

        ModelMap model = new ModelMap();

        List<Equipamiento> inventario = servicioHerreria.obtenerInventario();
        model.put("inventario", inventario);

        Boolean mejorado = servicioHerreria.mejorarEquipamiento(mejoraDto.getEquipamiento(), mejoraDto.getOroUsuario());

        model.put("mensaje", "El equipamiento se ha mejorado correctamente");
        if (!mejorado) {
            model.put("mensaje", "El equipamiento no se ha podido mejorar");
        }

        return new ModelAndView("herreria", model);
    }
}
