package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Personaje;
import com.tallerwebi.dominio.ServicioPersonaje;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControladorPersonaje {

    @Autowired
    private ServicioPersonaje servicioPersonaje;

    public ControladorPersonaje(ServicioPersonaje servicioPersonaje) {
        this.servicioPersonaje = servicioPersonaje;
    }

    public void setNombre(String nombre) {
        servicioPersonaje.setNombre(nombre);
    }

    public String getNombre() {
       return servicioPersonaje.getNombre();
    }

    public void setRol(String rol) {
        servicioPersonaje.setRol(rol);
    }

    public String getRol() {
        return servicioPersonaje.getRol();
    }

    public Integer getFuerza() {
        return servicioPersonaje.getFuerza();
    }

    public Integer getInteligencia() {
        return servicioPersonaje.getInteligencia();
    }

    public Integer getArmadura() {
        return servicioPersonaje.getArmadura();
    }

    public Integer getAgilidad() {
        return servicioPersonaje.getAgilidad();
    }

    @GetMapping("/creacion-personaje")
    public ModelAndView creacionPersonaje() {

        ModelMap modelMap = new ModelMap();
        modelMap.put("datosPersonaje", new Personaje());
        return new ModelAndView("creacion-personaje", modelMap);
    }

    @PostMapping("/validar-personaje")
    public ModelAndView validarPersonaje(@ModelAttribute("datosPersonaje") Personaje personaje) {

    }
}
