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
        List<Equipamiento> lista = servicioEquipamiento.mostrarEquipamiento();

        model.addAttribute("contenido", lista);

        if (!lista.isEmpty()) {
            Optional<Equipamiento> equipoEquipado = lista.stream().filter(Equipamiento::getEquipado).findFirst();
            if (equipoEquipado.isPresent()) {
                model.addAttribute("equipoSeleccionado", equipoEquipado.get());
            } else {
                model.addAttribute("equipoSeleccionado", lista.get(0));
            }
        }
        return new ModelAndView("equipamiento", model);
    }

    @GetMapping("/equipamiento/{id}")
    public ModelAndView verDetalleEquipamiento(@PathVariable Integer id) {
        ModelMap model = new ModelMap();
        Optional<Equipamiento> equipoSeleccionado = servicioEquipamiento.buscarEquipamientoPorId(id);

        model.addAttribute("contenido", servicioEquipamiento.mostrarEquipamiento());
        equipoSeleccionado.ifPresent(equipo -> model.addAttribute("equipoSeleccionado", equipo));

        return new ModelAndView("equipamiento", model);
    }

    @GetMapping("/equipamiento/equipar/{id}")
    public String equipar(@PathVariable Integer id) {
        servicioEquipamiento.equipar(id);
        return "redirect:/equipamiento";
    }
}