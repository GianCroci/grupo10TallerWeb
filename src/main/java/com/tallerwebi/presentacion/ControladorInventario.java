package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioInventario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControladorInventario {

    private final ServicioInventario servicioInventario;

    @Autowired
    public ControladorInventario(ServicioInventario servicioInventario) {
        this.servicioInventario = servicioInventario;
    }

    @GetMapping("/inventario")
    public ModelAndView verEquipamiento() {
        ModelMap model = new ModelMap();
        model.addAttribute("contenido", servicioInventario.mostrarEquipamiento());
        model.addAttribute("equipoSeleccionado", servicioInventario.mostrarPrimerEquipado());
        return new ModelAndView("inventario", model);
    }

    @GetMapping("/inventario/{id}")
    public ModelAndView verEquipoEspecifico(@PathVariable Long id) {
        ModelMap model = new ModelMap();
        model.addAttribute("contenido", servicioInventario.mostrarEquipamiento());
        model.addAttribute("equipoSeleccionado", servicioInventario.buscarEquipamientoPorId(id));
        return new ModelAndView("inventario", model);
    }

    @GetMapping("/inventario/equipar/{id}")
    public String equipar(@PathVariable Long id) {
        servicioInventario.equipar(id);
        return "redirect:/inventario";
    }

}