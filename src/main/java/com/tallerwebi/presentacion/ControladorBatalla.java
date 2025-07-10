package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioBatallaWs;
import com.tallerwebi.dominio.entidad.Personaje;
import com.tallerwebi.dominio.interfaz.servicio.ServicioBatalla;
import com.tallerwebi.dominio.interfaz.servicio.ServicioPersonaje;
import com.tallerwebi.dominio.excepcion.RivalNoEncontrado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class ControladorBatalla {

    private ServicioPersonaje servicioPersonaje;
    private ServicioBatalla servicioBatalla;
    private ServicioBatallaWs batallaService;

    @Autowired
    public ControladorBatalla(ServicioPersonaje servicioPersonaje, ServicioBatalla servicioBatalla, ServicioBatallaWs batallaService) {
        this.servicioPersonaje = servicioPersonaje;
        this.servicioBatalla = servicioBatalla;
        this.batallaService = batallaService;
    }

    @GetMapping("/batalla")
    public ModelAndView irABatalla(HttpServletRequest request, RedirectAttributes redirectAttributes) {
        ModelMap modelMap = new ModelMap();
        Long idPersonaje = (Long) request.getSession().getAttribute("idPersonaje");
        if (idPersonaje == null) {
            redirectAttributes.addFlashAttribute("error", "No puede acceder a la vista batalla sin haber iniciado sesion");
            return new ModelAndView("redirect:/login");
        }
        Personaje personaje = servicioPersonaje.buscarPersonaje(idPersonaje);
        Personaje rival = null;
        try {
            rival = servicioBatalla.buscarRival(idPersonaje);
        } catch (RivalNoEncontrado e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            redirectAttributes.addFlashAttribute("datosPersonaje", personaje);
            return new ModelAndView("redirect:/home", modelMap);
        }
        request.getSession().setAttribute("idRival", rival.getId());


        modelMap.put("personaje", personaje);
        modelMap.put("rival", rival);


        return new ModelAndView("batalla", modelMap);
    }

    @GetMapping("/batalla-websocket/{idRival}")
    public String salaDeBatalla(@PathVariable Long idRival, HttpSession session, Model model) {
        Personaje jugador = (Personaje) session.getAttribute("personaje");


        String salaId = batallaService.obtenerOSalaExistente(jugador.getId(), idRival);
        Personaje rival = servicioPersonaje.buscarPersonaje(idRival);

        model.addAttribute("personaje", jugador);
        model.addAttribute("rival", rival);
        model.addAttribute("salaId", salaId);

        return "batalla-ws";
    }

}
