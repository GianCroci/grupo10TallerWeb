package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Personaje;
import com.tallerwebi.dominio.ServicioPersonaje;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class ControladorChat {

    @Autowired
    private ServicioPersonaje servicioPersonaje;


    public ControladorChat(ServicioPersonaje servicioPersonaje) {
        this.servicioPersonaje = servicioPersonaje;

    }


    @RequestMapping(path = "/chat/{idAmigo}", method = RequestMethod.GET)
    public ModelAndView chatearConAmigo(HttpSession session, @PathVariable Long idAmigo) {
        Long idPersonaje = (Long) session.getAttribute("idPersonaje");

        if (idPersonaje == null) {
            return new ModelAndView("redirect:/login");
        }

        Personaje yo = servicioPersonaje.buscarPersonaje(idPersonaje);
        Personaje amigo = servicioPersonaje.buscarPersonaje(idAmigo);


        ModelMap model = new ModelMap();
        model.put("usuario", yo.getNombre());
        model.put("destinatario", amigo.getNombre());
        //model.put("historial", List.of());

        return new ModelAndView("sala-chat", model);
    }

}
