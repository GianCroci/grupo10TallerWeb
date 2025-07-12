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

        //Personajes disponibles
        PersonajeTaberna personajeDisponible = servicioTaberna.obtenerPersonajeDisponible();

        //Imagen del personaje
        String imagenParcial = servicioTaberna.obtenerVistaSegunPersonaje(personajeDisponible);

        //Cantidad de cervezas invitadas por cada personaje
        Map<PersonajeTaberna, Integer> personajes = servicioTaberna.obtenerCervezasInvitadasPorPersonaje(idPersonaje);


        modelMap.put("personajeDisponible", personajeDisponible);
        modelMap.put("imagenParcial", imagenParcial);
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

        try{
                if (servicioTaberna.puedeInvitar(idPersonaje, personajeEnum)) {
                    servicioTaberna.invitarCerveza(idPersonaje, personajeEnum);
                    int cantidad = servicioTaberna.getCantidadCervezasInvitadas(idPersonaje, personajeEnum);
                    modelMap.put("mensaje", "Has invitado un trago a " + personajeEnum.name() + ". Total de cervezas invitadas: " + cantidad);
                } else {
                    modelMap.put("mensaje", "Ya se invitó a este personaje hoy.");
                }
        } catch (IllegalArgumentException e) {
            modelMap.put("mensaje", "Ya se invito este personaje hoy.");
        }


        Map<PersonajeTaberna, Integer> personajes = servicioTaberna.obtenerCervezasInvitadasPorPersonaje(idPersonaje);
        //modelMap.put("personajeSeleccionado", personajeEnum.name()); // Esto será: MERCADER, HERRERO o GUARDIA


        modelMap.put("personajes", personajes);

        return new ModelAndView("taberna", modelMap);
    }

}
