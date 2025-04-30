package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Personaje;
import com.tallerwebi.dominio.ServicioPersonaje;
import com.tallerwebi.dominio.ServicioPersonajeImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;

public class ControladorPersonajeTest {

    @Test
    public void queSePuedaCrearUnPersonajeConNombre(){
        Personaje personaje = new Personaje();

        ServicioPersonaje servicioPersonaje = new ServicioPersonajeImpl(personaje);
        ControladorPersonaje controladorPersonaje = new ControladorPersonaje(servicioPersonaje);

        controladorPersonaje.setNombre("Lenorwix");

        String nombreEsperado = "Lenorwix";
        String nombreObtenido = controladorPersonaje.getNombre();

        assertThat(nombreEsperado, equalTo(nombreObtenido));
    }
}
