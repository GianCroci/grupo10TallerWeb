package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.entidad.Equipamiento;
import com.tallerwebi.dominio.excepcion.InventarioVacioException;
import com.tallerwebi.dominio.interfaz.servicio.ServicioInventario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
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
    public ModelAndView verEquipamiento(@RequestParam(value = "tipo", required = false) String tipo, HttpSession session) {
        Long idPersonaje = (Long) session.getAttribute("idPersonaje");
        ModelMap model = new ModelMap();

        if (idPersonaje == null) {
            return new ModelAndView("redirect:/login");
        }

        try {
            List<Equipamiento> inventario = servicioInventario.obtenerInventarioFiltradoPorTipo(idPersonaje, tipo);
            Equipamiento equipoSeleccionado = servicioInventario.obtenerEquipoSeleccionado(inventario, idPersonaje);

            model.addAttribute("inventario", inventario);
            model.addAttribute("equipoSeleccionado", equipoSeleccionado);
            model.addAttribute("tipoSeleccionado", tipo);

        } catch (InventarioVacioException e) {
            model.addAttribute("mensajeError", e.getMessage());
        }

        return new ModelAndView("inventario", model);
    }

    @GetMapping("/inventario/{idEquipo}")
    public ModelAndView verEquipoEspecifico(@PathVariable Long idEquipo, @RequestParam(value = "tipo", required = false) String tipo, HttpSession session) {
        Long idPersonaje = (Long) session.getAttribute("idPersonaje");
        ModelMap model = new ModelMap();

        if (idPersonaje == null) {
            return new ModelAndView("redirect:/login");
        }

        if (tipo == null || tipo.isBlank()) {
            tipo = "Todos";
        }

        try {
            List<Equipamiento> inventarioCompleto = servicioInventario.obtenerInventario(idPersonaje);
            List<Equipamiento> inventarioFiltrado = servicioInventario.obtenerInventarioFiltradoPorTipo(idPersonaje, tipo);

            Equipamiento equipoSeleccionado = servicioInventario.obtenerEquipamientoPorId(idPersonaje, idEquipo);
            equipoSeleccionado = servicioInventario.validarEquipoSeleccionadoEnFiltro(inventarioFiltrado, equipoSeleccionado, inventarioCompleto);

            model.addAttribute("inventario", inventarioFiltrado);
            model.addAttribute("equipoSeleccionado", equipoSeleccionado);
            model.addAttribute("tipoSeleccionado", tipo);

        } catch (InventarioVacioException e) {
            model.addAttribute("mensajeError", e.getMessage());
        }

        return new ModelAndView("inventario", model);
    }

    @GetMapping("/inventario/equipar/{idEquipo}")
    public String equipar(@PathVariable Long idEquipo,@RequestParam(value = "tipo", required = false) String tipo, HttpSession session) {
        Long idPersonaje = (Long) session.getAttribute("idPersonaje");
        if (idPersonaje == null) {
            return "redirect:/login";
        }

        try {
            servicioInventario.equipar(idPersonaje, idEquipo);
        } catch (InventarioVacioException e) {
            System.err.println("Error al equipar: " + e.getMessage());
        }

        return (tipo != null && !tipo.isBlank())
                ? "redirect:/inventario?tipo=" + tipo
                : "redirect:/inventario";
    }
}
