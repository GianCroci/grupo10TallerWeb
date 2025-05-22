package com.tallerwebi.dominio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ControladorEquipamiento {

    ServicioEquipamientoImpl servicioEquipamiento;

    @Autowired
    public ControladorEquipamiento(ServicioEquipamientoImpl servicioEquipamiento) {
        this.servicioEquipamiento = servicioEquipamiento;
    }

    @GetMapping("/equipamiento")
    public ModelAndView verEquipamiento() {
        ModelMap model = new ModelMap();
        List<Equipamiento> lista = servicioEquipamiento.mostrarEquipamiento();
        model.put("contenido", lista);
        return new ModelAndView("equipamiento", model);
    }



}