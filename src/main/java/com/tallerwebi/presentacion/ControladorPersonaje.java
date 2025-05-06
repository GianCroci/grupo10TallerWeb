package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Personaje;
import com.tallerwebi.dominio.ServicioPersonaje;
import com.tallerwebi.dominio.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControladorPersonaje {

    @Autowired
    private ServicioPersonaje servicioPersonaje;

    public ControladorPersonaje(ServicioPersonaje servicioPersonaje) {
        this.servicioPersonaje = servicioPersonaje;
    }


    @GetMapping("/creacion-personaje")
    public ModelAndView creacionPersonaje() {

        ModelMap modelMap = new ModelMap();
        modelMap.put("datosPersonaje", new Personaje());
        return new ModelAndView("creacion-personaje", modelMap);
    }

    @PostMapping("/guardar-personaje")
    public ModelAndView guardarPersonaje(@ModelAttribute("datosPersonaje") Personaje personaje) {
        ModelMap modelMap = new ModelMap();
        servicioPersonaje.guardarPersonaje(personaje);
        modelMap.put("datosPersonaje", personaje);
        return new ModelAndView("nuevo-personaje", modelMap);

    }

    @GetMapping("/nuevo-personaje")
    public ModelAndView nuevoPersonaje() {
        ModelMap model = new ModelMap();
        model.put("datosPersonaje", new Personaje());
        return new ModelAndView("nuevo-personaje", model);
    }
}
