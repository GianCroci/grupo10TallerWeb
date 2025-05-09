package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioHerreriaImpl;
import com.tallerwebi.dominio.ServicioLoginImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.hamcrest.Matchers.notNullValue;

public class ControladorHerreriaTest {


    @Test
    public void queSePuedaIrALaHerreria() {

        ServicioHerreriaImpl servicioHerreria = new ServicioHerreriaImpl();
        ControladorHerreria controladorHerreria = new ControladorHerreria(servicioHerreria);

        ModelAndView mav = controladorHerreria.irALaHerreria();

        String vistaEsperada = "herreria";
        String vistaObtenida = mav.getViewName();

        assertThat(vistaObtenida, equalToIgnoringCase(vistaEsperada));
    }

    @Test
    public void queAlIrALaHerreriaSeGuardeEnLaVistaUnModelMapConMejoraDto() {
        ServicioHerreriaImpl servicioHerreria = new ServicioHerreriaImpl();
        ControladorHerreria controladorHerreria = new ControladorHerreria(servicioHerreria);

        ModelAndView mav = controladorHerreria.irALaHerreria();

        String vistaEsperada = "herreria";
        String vistaObtenida = mav.getViewName();

        assertThat(vistaObtenida, equalToIgnoringCase(vistaEsperada));
        assertThat(mav.getModel().get("mejoraDto"), notNullValue());
    }

    @Test
    public void queAlMejorarUnEquipamientoSeMuestreUnMensajeDeExitoSiSePudoMejorarCorrectamente() {
        ServicioHerreriaImpl servicioHerreria = new ServicioHerreriaImpl();
        ControladorHerreria controladorHerreria = new ControladorHerreria(servicioHerreria);

        MejoraDto mejoraDto = new MejoraDto();
        mejoraDto.setOro(10);

        ModelAndView mav = controladorHerreria.mejorarEquipamiento(mejoraDto);

        String vistaEsperada = "herreria";
        String vistaObtenida = mav.getViewName();

        String mensajeEsperado = "El equipamiento se ha mejorado correctamente";
        String mensajeObtenida = mav.getModel().get("mensaje").toString();

        assertThat(vistaObtenida, equalToIgnoringCase(vistaEsperada));
        assertThat(mensajeObtenida, equalToIgnoringCase(mensajeEsperado));
    }

    @Test
    public void queAlMejorarUnEquipamientoSeMuestreUnMensajeDeFalloSiNoSePudoMejorarCorrectamente() {

        ServicioHerreriaImpl servicioHerreria = new ServicioHerreriaImpl();
        ControladorHerreria controladorHerreria = new ControladorHerreria(servicioHerreria);

        MejoraDto mejoraDto = new MejoraDto();
        mejoraDto.setOro(9);

        ModelAndView mav = controladorHerreria.mejorarEquipamiento(mejoraDto);

        String vistaEsperada = "herreria";
        String vistaObtenida = mav.getViewName();

        String mensajeEsperado = "El equipamiento no se ha podido mejorar";
        String mensajeObtenida = mav.getModel().get("mensaje").toString();


        assertThat(vistaObtenida, equalToIgnoringCase(vistaEsperada));
        assertThat(mensajeObtenida, equalToIgnoringCase(mensajeEsperado));
    }

}
