package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
public class ControladorPersonaje {

    private ServicioUsuario servicioUsuario;

    @Autowired
    public ControladorPersonaje(ServicioUsuario servicioUsuario) {
        this.servicioUsuario = servicioUsuario;
    }

    @GetMapping("/creacion-personaje")
    public ModelAndView creacionPersonaje() {

        ModelMap modelMap = new ModelMap();
        modelMap.put("datosPersonaje", new Personaje());
        return new ModelAndView("creacion-personaje", modelMap);
    }

    @PostMapping("/guardar-personaje")
    public ModelAndView guardarPersonaje(@ModelAttribute("datosPersonaje") Personaje personaje, HttpSession session) {
        ModelMap modelMap = new ModelMap();
        Personaje personajeGuardado = new Personaje();

        Usuario usuarioLogueado = (Usuario) session.getAttribute("usuarioLogueado");

        if (usuarioLogueado != null) {
            servicioUsuario.setPersonaje(personaje, usuarioLogueado);
            modelMap.put("datosPersonaje", usuarioLogueado.getPersonaje());
            session.setAttribute( "idPersonaje", usuarioLogueado.getPersonaje().getId());
            return new ModelAndView("home", modelMap);
        }

        modelMap.put("datosPersonaje", personajeGuardado);
        return new ModelAndView("creacion-personaje", modelMap);
    }


}
