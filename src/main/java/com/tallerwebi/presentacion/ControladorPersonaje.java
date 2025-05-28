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


    private ServicioPersonaje servicioPersonaje;
    private ServicioUsuario servicioUsuario;

    @Autowired
    public ControladorPersonaje(ServicioPersonaje servicioPersonaje, ServicioUsuario servicioUsuario) {
        this.servicioPersonaje = servicioPersonaje;
        this.servicioUsuario = servicioUsuario;
    }

    public ControladorPersonaje(ServicioPersonaje servicioPersonaje) {
        this.servicioPersonaje = servicioPersonaje;

    }

    @GetMapping("/creacion-personaje")
    public ModelAndView creacionPersonaje() {

        ModelMap modelMap = new ModelMap();
        modelMap.put("datosPersonaje", new Personaje());
        return new ModelAndView("creacion-personaje", modelMap);
    }

    @PostMapping("/guardar-personaje")
    public ModelAndView guardarPersonaje(@ModelAttribute("datosPersonaje") Personaje personaje, HttpSession session, Model model) {
        Usuario usuarioLogueado = (Usuario) session.getAttribute("usuarioLogueado");
        servicioUsuario.setUsuario(usuarioLogueado);
        ModelAndView mav = new ModelAndView("home");
        if (usuarioLogueado != null) {
            servicioUsuario.setPersonaje(personaje);
            mav.addObject("datosPersonaje", usuarioLogueado.getPersonaje());

            return mav;
        }
        return mav;
    }


    @GetMapping("/nuevo-personaje")
    public ModelAndView nuevoPersonaje() {
        ModelMap model = new ModelMap();
        model.put("datosPersonaje", new Personaje());
        return new ModelAndView("home", model);
    }
}
