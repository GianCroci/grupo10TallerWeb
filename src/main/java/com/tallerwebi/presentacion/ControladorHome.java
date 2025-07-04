package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.entidad.Personaje;
import com.tallerwebi.dominio.interfaz.servicio.ServicioPersonaje;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
public class ControladorHome {

    private final ServicioPersonaje servicioPersonaje;

    @Autowired
    public ControladorHome(ServicioPersonaje servicioPersonaje) {
        this.servicioPersonaje = servicioPersonaje;
    }

    @RequestMapping("/home")
    public ModelAndView irAlHome(HttpSession session, RedirectAttributes redirectAttributes) {
        ModelMap model = new ModelMap();

        Long idPersonaje = (Long) session.getAttribute("idPersonaje");
        if (idPersonaje == null) {
            redirectAttributes.addFlashAttribute("error", "No puede acceder a la vista home sin haber iniciado sesion");
            return new ModelAndView("redirect:/login");
        }
        Personaje personaje = servicioPersonaje.buscarPersonaje(idPersonaje);
        model.put("infoPersonaje", personaje);
        return new ModelAndView("home", model);
    }
}
