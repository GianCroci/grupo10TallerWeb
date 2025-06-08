package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Equipamiento;
import com.tallerwebi.dominio.ServicioEquipamiento;
import com.tallerwebi.dominio.ServicioEquipamientoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
public class ControladorEquipamiento {

    private final ServicioEquipamiento servicioEquipamiento;

    @Autowired
    public ControladorEquipamiento(ServicioEquipamiento servicioEquipamiento) {
        this.servicioEquipamiento = servicioEquipamiento;
    }

    @GetMapping("/equipamiento")
    public ModelAndView verEquipamiento() {
        ModelMap model = new ModelMap();
        model.addAttribute("contenido", servicioEquipamiento.mostrarEquipamiento());
        model.addAttribute("equipoSeleccionado", servicioEquipamiento.mostrarPrimerEquipado());
        return new ModelAndView("equipamiento", model);
    }

    @GetMapping("/equipamiento/{id}")
    public ModelAndView verEquipoEspecifico(@PathVariable Integer id) {
        ModelMap model = new ModelMap();
        model.addAttribute("contenido", servicioEquipamiento.mostrarEquipamiento());
        model.addAttribute("equipoSeleccionado", servicioEquipamiento.buscarEquipamientoPorId(id));
        return new ModelAndView("equipamiento", model);
    }

    @GetMapping("/equipamiento/equipar/{id}")
    public String equipar(@PathVariable Integer id) {
        servicioEquipamiento.equipar(id);
        return "redirect:/equipamiento";
    }

}