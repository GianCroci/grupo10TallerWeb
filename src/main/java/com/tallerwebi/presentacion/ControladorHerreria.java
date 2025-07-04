package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.entidad.Equipamiento;
import com.tallerwebi.dominio.interfaz.servicio.ServicioHerreria;
import com.tallerwebi.dominio.excepcion.InventarioVacioException;
import com.tallerwebi.dominio.excepcion.NivelDeEquipamientoMaximoException;
import com.tallerwebi.dominio.excepcion.OroInsuficienteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class ControladorHerreria {

    private final ServicioHerreria servicioHerreria;

    @Autowired
    public ControladorHerreria(ServicioHerreria servicioHerreria) {
        this.servicioHerreria = servicioHerreria;
    }

    @RequestMapping("/herreria")
    public ModelAndView irALaHerreria(HttpSession session, RedirectAttributes redirectAttributes) {

        ModelMap model = new ModelMap();

        Long idPersonaje = (Long) session.getAttribute("idPersonaje");
        if (idPersonaje == null) {
            redirectAttributes.addFlashAttribute("error", "No puede acceder a la vista herreria sin haber iniciado sesion");
            return new ModelAndView("redirect:/login");
        }
        Integer oroPersonaje = servicioHerreria.obtenerOroDelPersonaje(idPersonaje);

        model.put("oroPersonaje", oroPersonaje);
        model.put("mejoraDto", new MejoraDto());

        List<Equipamiento> inventario = null;
        try {
            inventario = servicioHerreria.obtenerInventario(idPersonaje);
            model.put("inventario", inventario);
        } catch (InventarioVacioException e) {
            model.put("error", e.getMessage());
            model.remove("inventario");
        }

        return new ModelAndView("herreria", model);
    }

    @RequestMapping(path = "/mejorar-equipamiento", method = RequestMethod.POST)
    public ModelAndView mejorarEquipamiento(@ModelAttribute("mejoraDto") MejoraDto mejoraDto, HttpSession session, RedirectAttributes redirectAttributes) {
        Long idPersonaje = (Long) session.getAttribute("idPersonaje");
        try {
            servicioHerreria.mejorarEquipamiento(mejoraDto.getIdEquipamiento(), mejoraDto.getOroUsuario(), idPersonaje);
            redirectAttributes.addFlashAttribute("estadoMejora", "El equipamiento se ha mejorado correctamente");
            redirectAttributes.addFlashAttribute("tipoEstadoMejora", "success");
        } catch (NivelDeEquipamientoMaximoException | OroInsuficienteException e) {
            redirectAttributes.addFlashAttribute("estadoMejora", e.getMessage());
            redirectAttributes.addFlashAttribute("tipoEstadoMejora", "danger");
        }

        return new ModelAndView("redirect:/herreria");
    }
}