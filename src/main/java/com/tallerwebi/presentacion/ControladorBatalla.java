package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Personaje;
import com.tallerwebi.dominio.ServicioBatalla;
import com.tallerwebi.dominio.ServicioPersonaje;
import com.tallerwebi.dominio.ServicioUsuario;
import com.tallerwebi.dominio.excepcion.RivalNoEncontrado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ControladorBatalla {

    private ServicioPersonaje servicioPersonaje;
    private ServicioBatalla servicioBatalla;

    @Autowired
    public ControladorBatalla(ServicioPersonaje servicioPersonaje, ServicioBatalla servicioBatalla) {
        this.servicioPersonaje = servicioPersonaje;
        this.servicioBatalla = servicioBatalla;
    }

    @GetMapping("/batalla")
    public ModelAndView irABatalla(HttpServletRequest request, RedirectAttributes redirectAttributes) {
        ModelMap modelMap = new ModelMap();
        Long idPersonaje = (Long) request.getSession().getAttribute("idPersonaje");
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

    @PostMapping("/atacar-rival")
    public ModelAndView atacarRival(HttpServletRequest request) {
        Long idPersonaje = (Long) request.getSession().getAttribute("idPersonaje");
        Long idRival = (Long) request.getSession().getAttribute("idRival");

        Personaje personaje = servicioPersonaje.buscarPersonaje(idPersonaje);
        Personaje rival = servicioPersonaje.buscarPersonaje(idRival);

        servicioBatalla.atacarRival(personaje, rival);

        ModelMap modelMap = new ModelMap();
        modelMap.put("personaje", personaje);
        modelMap.put("rival", rival);
        modelMap.put("resultado", servicioBatalla.getResultado());


        return new ModelAndView("batalla", modelMap);
    }

}
