package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioBatallaWsImpl;
import com.tallerwebi.dominio.entidad.Personaje;
import com.tallerwebi.dominio.interfaz.servicio.ServicioBatallaWs;
import com.tallerwebi.dominio.interfaz.servicio.ServicioPersonaje;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@ControllerAdvice
public class ControladorNavbarAviso {

    @Autowired
    private ServicioBatallaWs servicioBatallaWs;

    @Autowired
    private ServicioPersonaje servicioPersonaje;



    @ModelAttribute
    public void notificarDesafiosPendientes(HttpSession session, Model model) {
        Personaje personaje = (Personaje) session.getAttribute("personaje");

        if (personaje != null) {
            Optional<String> sala = servicioBatallaWs.buscarSalaPendientePara(personaje.getId());

            model.addAttribute("tieneDesafio", sala.isPresent());
            model.addAttribute("salaPendienteId", sala.orElse(null));
        } else {
            model.addAttribute("tieneDesafio", false);
        }
    }


}

