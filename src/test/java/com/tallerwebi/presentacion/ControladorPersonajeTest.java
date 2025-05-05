package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Personaje;
import com.tallerwebi.dominio.ServicioPersonaje;
import com.tallerwebi.dominio.ServicioPersonajeImpl;
import org.dom4j.rule.Mode;
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

    @Test
    public void queSeDefinaElRolDelPersonaje(){
        Personaje personaje = new Personaje();

        ServicioPersonaje servicioPersonaje = new ServicioPersonajeImpl(personaje);
        ControladorPersonaje controladorPersonaje = new ControladorPersonaje(servicioPersonaje);

        controladorPersonaje.setRol("Mago");

        String rolEsperado = "Mago";
        String rolObtenido = controladorPersonaje.getRol();

        assertThat(rolEsperado, equalTo(rolObtenido));
    }

    @Test
    public void queSiEsLuchadorEmpieceCon100DeFuerza(){
        Personaje personaje = new Personaje();

        ServicioPersonaje servicioPersonaje = new ServicioPersonajeImpl(personaje);
        ControladorPersonaje controladorPersonaje = new ControladorPersonaje(servicioPersonaje);

        controladorPersonaje.setRol("Luchador");

        Integer fuerzaEsperado = 100;
        Integer fuerzaObtenida = controladorPersonaje.getFuerza();

        assertThat(fuerzaEsperado, equalTo(fuerzaObtenida));
    }

    @Test
    public void queSeSeteenLasHabilidadesInicialesDelLuchador(){
        Personaje personaje = new Personaje();

        ServicioPersonaje servicioPersonaje = new ServicioPersonajeImpl(personaje);
        ControladorPersonaje controladorPersonaje = new ControladorPersonaje(servicioPersonaje);

        controladorPersonaje.setRol("Luchador");

        Integer fuerzaEsperado = 100;
        Integer fuerzaObtenida = controladorPersonaje.getFuerza();
        Integer inteligenciaEsperado = 40;
        Integer inteligenciaObtenida = controladorPersonaje.getInteligencia();
        Integer armaduraEsperado = 80;
        Integer armaduraObtenida = controladorPersonaje.getArmadura();
        Integer agilidadEsperado = 60;
        Integer agilidadObtenida = controladorPersonaje.getAgilidad();

        assertThat(fuerzaEsperado, equalTo(fuerzaObtenida));
        assertThat(inteligenciaEsperado, equalTo(inteligenciaObtenida));
        assertThat(armaduraEsperado, equalTo(armaduraObtenida));
        assertThat(agilidadEsperado, equalTo(agilidadObtenida));
    }

    @Test
    public void queSeSeteenLasHabilidadesInicialesDelMago(){
        Personaje personaje = new Personaje();

        ServicioPersonaje servicioPersonaje = new ServicioPersonajeImpl(personaje);
        ControladorPersonaje controladorPersonaje = new ControladorPersonaje(servicioPersonaje);

        controladorPersonaje.setRol("Mago");

        Integer fuerzaEsperado = 30;
        Integer fuerzaObtenida = controladorPersonaje.getFuerza();
        Integer inteligenciaEsperado = 100;
        Integer inteligenciaObtenida = controladorPersonaje.getInteligencia();
        Integer armaduraEsperado = 20;
        Integer armaduraObtenida = controladorPersonaje.getArmadura();
        Integer agilidadEsperado = 40;
        Integer agilidadObtenida = controladorPersonaje.getAgilidad();

        assertThat(fuerzaEsperado, equalTo(fuerzaObtenida));
        assertThat(inteligenciaEsperado, equalTo(inteligenciaObtenida));
        assertThat(armaduraEsperado, equalTo(armaduraObtenida));
        assertThat(agilidadEsperado, equalTo(agilidadObtenida));
    }

    @Test
    public void queSeSeteenLasHabilidadesInicialesDelBandido(){
        Personaje personaje = new Personaje();

        ServicioPersonaje servicioPersonaje = new ServicioPersonajeImpl(personaje);
        ControladorPersonaje controladorPersonaje = new ControladorPersonaje(servicioPersonaje);

        controladorPersonaje.setRol("Bandido");

        Integer fuerzaEsperado = 50;
        Integer fuerzaObtenida = controladorPersonaje.getFuerza();
        Integer inteligenciaEsperado = 70;
        Integer inteligenciaObtenida = controladorPersonaje.getInteligencia();
        Integer armaduraEsperado = 30;
        Integer armaduraObtenida = controladorPersonaje.getArmadura();
        Integer agilidadEsperado = 100;
        Integer agilidadObtenida = controladorPersonaje.getAgilidad();

        assertThat(fuerzaEsperado, equalTo(fuerzaObtenida));
        assertThat(inteligenciaEsperado, equalTo(inteligenciaObtenida));
        assertThat(armaduraEsperado, equalTo(armaduraObtenida));
        assertThat(agilidadEsperado, equalTo(agilidadObtenida));
    }

    @Test
    public void queHayaUnaVistaDeCreacionDePersonaje(){
        Personaje personaje = new Personaje();
        ServicioPersonaje servicioPersonaje = new ServicioPersonajeImpl(personaje);
        ControladorPersonaje controladorPersonaje = new ControladorPersonaje(servicioPersonaje);

        ModelAndView modelAndView = controladorPersonaje.creacionPersonaje();

        String vistaEsperada = "creacion-personaje";

        assertThat(vistaEsperada, equalTo(modelAndView.getViewName()));
    }
}
