package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.excepcion.PersonajeInvalidoException;
import com.tallerwebi.dominio.excepcion.SolicitudInvalidaException;
import com.tallerwebi.dominio.interfaz.servicio.ServicioAmigo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class ControladorAmigo {

    private final ServicioAmigo servicioAmigo;

    @Autowired
    public ControladorAmigo(ServicioAmigo servicioAmigo) {
        this.servicioAmigo = servicioAmigo;
    }

    @RequestMapping("/amigos")
    public ModelAndView verAmigos(HttpSession session, RedirectAttributes redirectAttributes) {
        ModelMap model = new ModelMap();

        Long idPersonaje = (Long) session.getAttribute("idPersonaje");
        if (idPersonaje == null) {
            redirectAttributes.addFlashAttribute("error", "No puede acceder a la vista amigos sin haber iniciado sesion");
            return new ModelAndView("redirect:/login");
        }
        String codigoAmigo = servicioAmigo.obtenerCodigoAmigoPropio(idPersonaje);
        List<SolicitudAmistadDTO> solicitudesRecibidas = servicioAmigo.obtenerSolicitudesRecibidas(idPersonaje);
        List<SolicitudAmistadDTO> solicitudesEnviadas = servicioAmigo.obtenerSolicitudesEnviadas(idPersonaje);
        List<AmigoDTO> amigos = servicioAmigo.obtenerAmigos(idPersonaje);

        model.put("amigos", amigos);
        model.put("solicitudesRecibidas", solicitudesRecibidas);
        model.put("solicitudesEnviadas", solicitudesEnviadas);
        model.put("codigoAmigo", codigoAmigo);

        return new ModelAndView("amigos", model);
    }

    @RequestMapping("/enviar-solicitud")
    public ModelAndView enviarSolicitudAmistad(@RequestParam(name = "codigoAmigo") String codigoAmigo, HttpSession session, RedirectAttributes redirectAttributes) {

        Long idPersonaje = (Long) session.getAttribute("idPersonaje");

        try {
            servicioAmigo.enviarSolicitud(idPersonaje, codigoAmigo);
        } catch (PersonajeInvalidoException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return new ModelAndView("redirect:/amigos");
        }
        redirectAttributes.addFlashAttribute("mensaje", "Solicitud enviada");

        return new ModelAndView("redirect:/amigos");
    }


    @RequestMapping(path = "/aceptar-solicitud", method = RequestMethod.POST)
    public ModelAndView aceptarSolicitud(@RequestParam Long idSolicitud, RedirectAttributes redirectAttributes) {

        try {
            servicioAmigo.aceptarSolicitud(idSolicitud);
        } catch (SolicitudInvalidaException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return new ModelAndView("redirect:/amigos");
        }
        redirectAttributes.addFlashAttribute("mensaje", "Solicitud aceptada");

        return new ModelAndView("redirect:/amigos");
    }

    @RequestMapping(path = "/rechazar-solicitud", method = RequestMethod.POST)
    public ModelAndView rechazarSolicitud(@RequestParam Long idSolicitud, RedirectAttributes redirectAttributes) {

        try {
            servicioAmigo.rechazarSolicitud(idSolicitud);
        } catch (SolicitudInvalidaException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return new ModelAndView("redirect:/amigos");
        }
        redirectAttributes.addFlashAttribute("mensaje", "Solicitud rechazada");

        return new ModelAndView("redirect:/amigos");
    }
}