package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.tallerwebi.dominio.interfaz.servicio.ServicioTaberna;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
public class ControladorTaberna {

    private ServicioTaberna servicioTaberna;

    @Autowired
    public ControladorTaberna(ServicioTaberna servicioTaberna) {

        this.servicioTaberna = servicioTaberna;
    }

    //muestra la vista de la taberna
    @GetMapping("/taberna")
    public ModelAndView mostrarTaberna(HttpSession session, RedirectAttributes redirectAttributes) {
        ModelMap modelMap = new ModelMap();

        Long idPersonaje = (Long) session.getAttribute("idPersonaje");
        if (idPersonaje == null) {
            redirectAttributes.addFlashAttribute("error", "No puede acceder a la vista taberna sin haber iniciado sesion");
            return new ModelAndView("redirect:/login");
        }

        //Cantidad de cervezas invitadas por cada personaje
        Map<PersonajeTaberna, Integer> personajes = servicioTaberna.obtenerCervezasInvitadasPorPersonaje(idPersonaje);

        //Cantidad de cervezas disponibles diarias
        int cervezasDisponibles = servicioTaberna.obtenerCervezasDisponibles(idPersonaje);


        modelMap.put("cervezasDisponibles", cervezasDisponibles);
        modelMap.put("personajes", personajes);

        return new ModelAndView("taberna", modelMap);

    }


    @PostMapping("/invitarTrago")
    public ModelAndView invitarTrago(HttpSession session, @RequestParam("personajeSeleccionado") String personajeSeleccionado) {
        ModelMap modelMap = new ModelMap();

        Long idPersonaje = (Long) session.getAttribute("idPersonaje");

        if (idPersonaje == null) {
            modelMap.put("mensaje", "El personaje no está activo en la sesión.");
            return new ModelAndView("taberna", modelMap);
        }

        PersonajeTaberna personajeEnum = PersonajeTaberna.valueOf(personajeSeleccionado.toUpperCase());
        int cervezasDisponibles = servicioTaberna.obtenerCervezasDisponibles(idPersonaje);


        try{
            if (cervezasDisponibles <= 0) {
                modelMap.put("mensaje", "No tenemos más cervezas para ti hoy. ¡Vuelve mañana!");
            } else {
                servicioTaberna.invitarCerveza(idPersonaje, personajeEnum);


                int total = servicioTaberna.getCantidadCervezasInvitadas(idPersonaje, personajeEnum);

                modelMap.put("mensaje", "Has invitado un trago a " + personajeEnum.name() +
                        ". Total de cervezas invitadas: " + total);
            }

        } catch (IllegalArgumentException e) {
            modelMap.put("mensaje", "No tienes mas cervezas, vuelve mañana.");
        }

        cervezasDisponibles = servicioTaberna.obtenerCervezasDisponibles(idPersonaje);

        Map<PersonajeTaberna, Integer> personajes = servicioTaberna.obtenerCervezasInvitadasPorPersonaje(idPersonaje);

        modelMap.put("cervezasDisponibles", cervezasDisponibles);
        modelMap.put("personajeSeleccionado", personajeEnum.name());
        modelMap.put("personajes", personajes);

        return new ModelAndView("taberna", modelMap);
    }

}
