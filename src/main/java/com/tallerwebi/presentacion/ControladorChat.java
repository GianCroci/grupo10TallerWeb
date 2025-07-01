package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Mensaje;
import com.tallerwebi.dominio.Personaje;
import com.tallerwebi.dominio.ServicioChat;
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
    private ServicioChat servicioChat;


    public ControladorChat(ServicioPersonaje servicioPersonaje, ServicioChat servicioChat) {
        this.servicioPersonaje = servicioPersonaje;
        this.servicioChat = servicioChat;

    }

    @RequestMapping(path = "/chat/{idAmigo}", method = RequestMethod.GET)
    public ModelAndView chatearConAmigo(HttpSession session, @PathVariable Long idAmigo) {
        Long idPersonaje = (Long) session.getAttribute("idPersonaje");

        if (idPersonaje == null) {
            return new ModelAndView("redirect:/login");
        }

        Personaje yo = servicioPersonaje.buscarPersonaje(idPersonaje);
        Personaje amigo = servicioPersonaje.buscarPersonaje(idAmigo);

        List<Mensaje> historial = servicioChat.obtenerHistorial(yo.getNombre(), amigo.getNombre());

        ModelMap model = new ModelMap();
        model.put("usuario", yo.getNombre());
        model.put("destinatario", amigo.getNombre());
        model.put("historial", historial);

        return new ModelAndView("sala-chat", model);
    }


}
