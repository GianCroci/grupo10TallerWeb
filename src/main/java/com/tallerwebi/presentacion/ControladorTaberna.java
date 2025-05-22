package com.tallerwebi.presentacion;


import com.tallerwebi.dominio.PersonajeTaberna;
import com.tallerwebi.dominio.ServicioTaberna;
import com.tallerwebi.dominio.Taberna;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControladorTaberna {

    private ServicioTaberna servicioTaberna;

    private Taberna taberna;

    public ControladorTaberna(ServicioTaberna servicioTaberna) {

        this.taberna = new Taberna();
        this.servicioTaberna = servicioTaberna;
    }

    //muestra la vista de la taberna
    @GetMapping("/taberna")
    public ModelAndView mostrarTaberna() {
        ModelMap modelMap = new ModelMap();
        PersonajeTaberna personajeDisponible = servicioTaberna.obtenerPersonajeDisponible();
        String imagenParcial = servicioTaberna.obtenerVistaSegunPersonaje(personajeDisponible);

        modelMap.put("personajeDisponible", personajeDisponible);
        modelMap.put("imagenParcial", imagenParcial);

        return new ModelAndView("taberna", modelMap);

    }


    @PostMapping("/invitarTrago")
    public ModelAndView invitarTrago(@RequestParam("personaje") String personaje) {
        ModelMap modelMap = new ModelMap();

        PersonajeTaberna personajeDisponible = servicioTaberna.obtenerPersonajeDisponible();

        try {
            PersonajeTaberna personajeEnum = PersonajeTaberna.valueOf(personaje);

            if (personajeEnum.equals(personajeDisponible)) {
                servicioTaberna.invitarTrago(personajeEnum);
                modelMap.put("mensaje", "Has invitado un trago a " + personajeEnum.name() + ". Total de cervezas invitadas:" + servicioTaberna.getCervezasInvitadas(personajeEnum));
            } else {
                modelMap.put("mensaje", "No puedes invitar un trago a " + personajeEnum.name() + " en este momento.");
            }

        } catch (IllegalArgumentException e) {
            String mensaje = e.getMessage();
            if (mensaje != null && mensaje.contains("Ya invitaste")) {
                modelMap.put("mensaje", mensaje);
            } else {
                modelMap.put("mensaje", "Personaje no válido.");
            }
        }

        String vistaParcial = servicioTaberna.mostrarTaberna();
        modelMap.put("vistaParcial", vistaParcial);
        modelMap.put("personajeDisponible", personajeDisponible != null ? personajeDisponible.name() : null);

        return new ModelAndView("taberna", modelMap);
    }



}
