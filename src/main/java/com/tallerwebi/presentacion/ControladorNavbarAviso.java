package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioBatallaWsImpl;
import com.tallerwebi.dominio.entidad.Personaje;
import com.tallerwebi.dominio.interfaz.servicio.ServicioBatallaWs;
import com.tallerwebi.dominio.interfaz.servicio.ServicioPersonaje;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@ControllerAdvice
public class ControladorNavbarAviso {

    @Autowired
    private ServicioBatallaWs servicioBatallaWs;

    @Autowired
    private ServicioPersonaje servicioPersonaje;

    @ModelAttribute
    public void notificarDesafiosPendientes(HttpSession session, Model model, HttpServletRequest request) {
        Long idPersonaje = (Long) session.getAttribute("idPersonaje");
        Personaje personaje = servicioPersonaje.buscarPersonaje(idPersonaje);

        if (personaje != null) {
            Optional<String> sala = servicioBatallaWs.buscarSalaPendientePara(personaje.getId());

            // Solo agregar al request scope, no al modelo
            request.setAttribute("tieneDesafio", sala.isPresent());
            request.setAttribute("salaPendienteId", sala.orElse(null));
        } else {
            request.setAttribute("tieneDesafio", false);
        }
    }
}