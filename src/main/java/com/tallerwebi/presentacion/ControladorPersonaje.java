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
    private ServicioPersonaje servicioPersonaje;

    @Autowired
    public ControladorPersonaje(ServicioUsuario servicioUsuario, ServicioPersonaje servicioPersonaje) {
        this.servicioUsuario = servicioUsuario;
        this.servicioPersonaje = servicioPersonaje;
    }

    @GetMapping("/creacion-personaje")
    public ModelAndView creacionPersonaje() {

        ModelMap modelMap = new ModelMap();
        modelMap.put("datosPersonaje", new PersonajeDTO());
        return new ModelAndView("creacion-personaje", modelMap);
    }

    @PostMapping("/guardar-personaje")
    public ModelAndView guardarPersonaje(@ModelAttribute("datosPersonaje") PersonajeDTO personajeDTO, HttpSession session) {
        ModelMap modelMap = new ModelMap();

        Usuario usuarioLogueado = (Usuario) session.getAttribute("usuarioLogueado");

        if (usuarioLogueado != null) {
            Personaje personajeCreado = servicioPersonaje.crearPersonaje(personajeDTO.getNombre(), personajeDTO.getGenero(), personajeDTO.getImagen(), personajeDTO.getIdRol());
            servicioUsuario.setPersonaje(personajeCreado, usuarioLogueado);
            modelMap.put("datosPersonaje", usuarioLogueado.getPersonaje());
            session.setAttribute( "idPersonaje", usuarioLogueado.getPersonaje().getId());
            return new ModelAndView("home", modelMap);
        }

        return new ModelAndView("redirect:/creacion-personaje", modelMap);
    }


}
