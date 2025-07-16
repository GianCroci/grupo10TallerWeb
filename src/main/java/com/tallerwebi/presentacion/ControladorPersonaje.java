package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.entidad.Personaje;
import com.tallerwebi.dominio.entidad.Usuario;
import com.tallerwebi.dominio.interfaz.servicio.ServicioPersonaje;
import com.tallerwebi.dominio.interfaz.servicio.ServicioUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public ModelAndView creacionPersonaje(RedirectAttributes redirectAttributes, HttpSession session) {

        Boolean accesoCreacionPersonaje = (Boolean) session.getAttribute("accesoCreacionPersonaje");

        Long idPersonaje = (Long) session.getAttribute("idPersonaje");
        if (idPersonaje != null) {
            redirectAttributes.addFlashAttribute("error", "No se puede crear un segundo personaje");
            return new ModelAndView("redirect:/home");
        }
        if(accesoCreacionPersonaje == null || !accesoCreacionPersonaje){
            redirectAttributes.addFlashAttribute("error", "No puede acceder a la creacion de personaje sin haberse registrado");
            return new ModelAndView("redirect:/login");
        }

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
            session.setAttribute( "idPersonaje", usuarioLogueado.getPersonaje().getId());
            session.setAttribute("nombrePersonaje", personajeCreado.getNombre());
            session.setAttribute("imagenPersonaje", personajeCreado.getImagen());
            return new ModelAndView("redirect:/home", modelMap);
        }

        return new ModelAndView("redirect:/creacion-personaje", modelMap);
    }




}
