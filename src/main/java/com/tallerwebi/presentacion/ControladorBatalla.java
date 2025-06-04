package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Personaje;
import com.tallerwebi.dominio.ServicioBatalla;
import com.tallerwebi.dominio.ServicioPersonaje;
import com.tallerwebi.dominio.ServicioUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ControladorBatalla {

    private ServicioPersonaje servicioPersonaje;
    private ServicioBatalla servicioBatalla;

    @Autowired
    public ControladorBatalla(ServicioPersonaje servicioPersonaje, ServicioBatalla servicioBatalla) {
        this.servicioPersonaje = servicioPersonaje;
        this.servicioBatalla = servicioBatalla;
    }

    @GetMapping("/batalla")
    public ModelAndView irABatalla(HttpServletRequest request) {
        Long idPersonaje = (Long) request.getSession().getAttribute("idPersonaje");
        Personaje personaje = servicioPersonaje.buscarPersonaje(idPersonaje);
        Personaje rival = servicioBatalla.buscarRival();
        request.getSession().setAttribute("idRival", rival.getId());

        ModelMap modelMap = new ModelMap();
        modelMap.put("personaje", personaje);
        modelMap.put("rival", rival);


        return new ModelAndView("batalla", modelMap);
    }

    @PostMapping("/batalla")
    public ModelAndView atacarRival(HttpServletRequest request) {
        Long idPersonaje = (Long) request.getSession().getAttribute("idPersonaje");
        Long idRival = (Long) request.getSession().getAttribute("idRival");

        Personaje personaje = servicioPersonaje.buscarPersonaje(idPersonaje);
        Personaje rival = servicioPersonaje.buscarPersonaje(idRival);

        servicioBatalla.atacarRival(personaje, rival);

        ModelMap modelMap = new ModelMap();
        modelMap.put("personaje", personaje);
        modelMap.put("rival", rival);
        modelMap.put("resultado", "Derrota");

        if (servicioBatalla.getResultado() == "Victoria") {
            modelMap.put("resultado", "Victoria");
        }

        return new ModelAndView("batalla", modelMap);
    }
    /*
    when(servicioBatallaMock.getResultado()).thenReturn("Victoria");

        //ejecucion
        ModelAndView modelAndView = controladorBatalla.atacarRival();

        String vistaEsperada = "victoria";

        //verificacion
        verify(servicioBatallaMock, times(1)).atacarRival(rivalMockeado); */
}
