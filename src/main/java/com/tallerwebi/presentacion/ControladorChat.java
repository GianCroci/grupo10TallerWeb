package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Personaje;
import com.tallerwebi.dominio.ServicioPersonaje;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ControladorChat {

    @Autowired
    private ServicioPersonaje servicioPersonaje;

    @RequestMapping(path = "/sala-chat", method = RequestMethod.GET)
    public ModelAndView irAChat(HttpServletRequest request) {
        Long idPersonaje = (Long) request.getSession().getAttribute("idPersonaje");

        Personaje personaje = servicioPersonaje.buscarPersonaje(idPersonaje);
        Personaje rival = servicioPersonaje.buscarRival(idPersonaje);

        ModelMap model = new ModelMap();
        model.put("usuario", personaje.getNombre());
        model.put("destinatario", rival.getNombre());

        return new ModelAndView("sala-chat", model);
    }
}
