package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Equipamiento;
import com.tallerwebi.dominio.RepositorioInventario;
import com.tallerwebi.dominio.ServicioInventario;
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
    private final RepositorioInventario repositorioInventario;

    @Autowired
    public ControladorInventario(ServicioInventario servicioInventario, RepositorioInventario repositorioInventario) {
        this.servicioInventario = servicioInventario;
        this.repositorioInventario = repositorioInventario;
    }

    @GetMapping("/inventario")
    public ModelAndView verEquipamiento(HttpSession session) {
        Long idPersonaje = (Long) session.getAttribute("idPersonaje");
        ModelMap model = new ModelMap();
        model.addAttribute("contenido", servicioInventario.mostrarEquipamiento());
        model.addAttribute("equipoSeleccionado", servicioInventario.mostrarPrimerEquipado());

        List<Equipamiento> compras = repositorioInventario.obtenerComprasDePersonaje(idPersonaje);
        model.addAttribute("comprasRealizadas", compras);
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