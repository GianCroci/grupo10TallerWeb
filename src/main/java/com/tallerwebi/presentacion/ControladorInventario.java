package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.entidad.Equipamiento;
import com.tallerwebi.dominio.excepcion.InventarioVacioException;
import com.tallerwebi.dominio.interfaz.servicio.ServicioInventario;
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
    public ControladorInventario(ServicioInventario servicioInventario) {
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

            Equipamiento equipoSeleccionado = null;
            if (!inventario.isEmpty()) {
                equipoSeleccionado = inventario.stream()
                        .filter(Equipamiento::getEquipado)
                        .findFirst()
                        .orElse(null);

                if (equipoSeleccionado == null) {
                    equipoSeleccionado = inventario.get(0);
                }
            }
            model.addAttribute("equipoSeleccionado", equipoSeleccionado);

        } catch (InventarioVacioException e) {
            model.addAttribute("mensajeError", e.getMessage());
        }

        return new ModelAndView("inventario", model);
    }

    @GetMapping("/inventario/{idEquipo}")
    public ModelAndView verEquipoEspecifico(HttpSession session, @PathVariable Long idEquipo) {
        Long idPersonaje = (Long) session.getAttribute("idPersonaje");
        ModelMap model = new ModelMap();

        if (idPersonaje == null) {
            return new ModelAndView("redirect:/login");
        }

        try {
            model.addAttribute("inventario", servicioInventario.obtenerInventario(idPersonaje));
            model.addAttribute("equipoSeleccionado", servicioInventario.obtenerEquipamientoPorId(idPersonaje, idEquipo));
        } catch (InventarioVacioException e) {
            model.addAttribute("mensajeError", e.getMessage());
        }

        return new ModelAndView("inventario", model);
    }

    @GetMapping("/inventario/equipar/{idEquipo}")
    public String equipar(HttpSession session, @PathVariable Long idEquipo) {
        Long idPersonaje = (Long) session.getAttribute("idPersonaje");
        if (idPersonaje == null) {
            return "redirect:/login";
        }
        try {
            servicioInventario.equipar(idPersonaje, idEquipo);
        } catch (InventarioVacioException e) {
            System.err.println("Error al equipar: " + e.getMessage());
        }
        return "redirect:/inventario";
    }
}