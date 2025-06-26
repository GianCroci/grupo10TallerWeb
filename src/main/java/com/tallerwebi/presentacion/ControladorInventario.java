package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.excepcion.InventarioNoExistente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class ControladorInventario {

    private final ServicioInventario servicioInventario;

    @Autowired
    public ControladorInventario(ServicioInventario servicioInventario, ServicioPersonaje servicioPersonaje) {
        this.servicioInventario = servicioInventario;
    }

    @GetMapping("/inventario")
    public ModelAndView verEquipamiento(HttpSession session) {
        Long idPersonaje = (Long) session.getAttribute("idPersonaje");
        ModelMap model = new ModelMap();

        if (idPersonaje == null) {
            return new ModelAndView("redirect:/login");
        }

        try {
            List<Equipamiento> inventario = servicioInventario.obtenerInventario(idPersonaje);

            model.addAttribute("inventario", inventario);

            if (servicioInventario.obtenerPrimerEquipado(idPersonaje) != null) {
                model.addAttribute("equipoSeleccionado", servicioInventario.obtenerPrimerEquipado(idPersonaje));
            } else {
                model.addAttribute("equipoSeleccionado", inventario.get(0));
            }

        } catch (InventarioNoExistente e) {
            model.addAttribute("mensajeError", e.getMessage());
        }

        return new ModelAndView("inventario", model);
    }


    @GetMapping("/inventario/{idEquipo}")
    public ModelAndView verEquipoEspecifico(HttpSession session, @PathVariable Long idEquipo) {
        Long idPersonaje = (Long) session.getAttribute("idPersonaje");

        if (idPersonaje == null) {
            return new ModelAndView("redirect:/login");
        }

        ModelMap model = new ModelMap();
        model.addAttribute("inventario", servicioInventario.obtenerInventario(idPersonaje));
        model.addAttribute("equipoSeleccionado", servicioInventario.obtenerEquipamientoPorId(idPersonaje, idEquipo));

        return new ModelAndView("inventario", model);
    }


    @GetMapping("/inventario/equipar/{idEquipo}")
    public String equipar(HttpSession session,@PathVariable Long idEquipo) {
        Long idPersonaje = (Long) session.getAttribute("idPersonaje");
        servicioInventario.equipar(idPersonaje,idEquipo);
        return "redirect:/inventario";
    }




}