package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.entidad.Personaje;
import com.tallerwebi.dominio.interfaz.servicio.ServicioBatalla;
import com.tallerwebi.dominio.interfaz.servicio.ServicioPersonaje;
import com.tallerwebi.dominio.excepcion.RivalNoEncontrado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

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

    @RequestMapping("/tablon-enemigos")
    public ModelAndView irATablonEnemigos(HttpSession session, RedirectAttributes redirectAttributes) {

        Long idPersonaje = (Long) session.getAttribute("idPersonaje");
        if (idPersonaje == null) {
            redirectAttributes.addFlashAttribute("error", "No puede acceder a la vista tablon de enemigos sin haber iniciado sesion");
            return new ModelAndView("redirect:/login");
        }
        if (session.getAttribute("batallaActual") != null) {
            session.removeAttribute("batallaActual");
        }

        ModelMap model = new ModelMap();

        List<EnemigoDTO> enemigosDTO = servicioBatalla.buscarEnemigosParaTablon();

        model.put("enemigos", enemigosDTO);

        return new ModelAndView("tablon-enemigos", model);
    }

    @RequestMapping(path = "/comenzar-batalla", method = RequestMethod.POST)
    public ModelAndView comenzarBatalla(@RequestParam Long idEnemigo, HttpSession session) {

        Long idPersonaje = (Long) session.getAttribute("idPersonaje");

        ModelMap model = new ModelMap();
        BatallaDTO batallaDTO = servicioBatalla.comenzarBatalla(idPersonaje, idEnemigo);
        model.put("batallaDTO", batallaDTO);
        session.setAttribute("batallaActual", batallaDTO);
        return new ModelAndView("campo-batalla", model);
    }

    @RequestMapping(path = "/realizar-accion", method = RequestMethod.POST)
    public ModelAndView realizarAccion(@RequestParam String accion, HttpSession session) {
        BatallaDTO batallaDTO = (BatallaDTO) session.getAttribute("batallaActual");

        if (batallaDTO.getVidaActualEnemigo().equals(0) || batallaDTO.getVidaActualPersonaje().equals(0)) {
            session.removeAttribute("batallaActual");
            return new ModelAndView("redirect:/tablon-enemigos");
        }
        servicioBatalla.realizarAccion(accion, batallaDTO);

        ModelMap model = new ModelMap();
        model.put("batallaDTO", batallaDTO);
        session.setAttribute("batallaActual", batallaDTO);
        return new ModelAndView("campo-batalla", model);
    }
}
