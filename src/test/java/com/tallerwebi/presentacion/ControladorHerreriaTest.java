package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ControladorHerreriaTest {

    private ServicioHerreria servicioHerreriaMock;
    private ControladorHerreria controladorHerreria;
    private MejoraDto mejoraDtoMock;


    @BeforeEach
    public void init(){
        servicioHerreriaMock = mock(ServicioHerreria.class);
        controladorHerreria = new ControladorHerreria(servicioHerreriaMock);
        mejoraDtoMock = mock(MejoraDto.class);
    }

    @Test
    public void queSePuedaIrALaHerreria() {

        ModelAndView mav = controladorHerreria.irALaHerreria();

        String vistaEsperada = "herreria";
        String vistaObtenida = mav.getViewName();

        assertThat(vistaObtenida, equalToIgnoringCase(vistaEsperada));
    }

    @Test
    public void queAlIrALaHerreriaSeGuardeEnLaVistaUnModelMapConMejoraDto() {

        ModelAndView mav = controladorHerreria.irALaHerreria();

        String vistaEsperada = "herreria";
        String vistaObtenida = mav.getViewName();

        assertThat(vistaObtenida, equalToIgnoringCase(vistaEsperada));
        assertThat(mav.getModel().get("mejoraDto"), notNullValue());
    }

    @Test
    public void queAlIrALaHerreriaSeGuardeEnLaVistaUnModelMapConUnInventario() {

        ModelAndView mav = controladorHerreria.irALaHerreria();

        String vistaEsperada = "herreria";
        String vistaObtenida = mav.getViewName();

        assertThat(vistaObtenida, equalToIgnoringCase(vistaEsperada));
        assertThat(mav.getModel().get("inventario"), notNullValue());
    }

    @Test
    public void queAlMejorarUnEquipamientoSeMuestreUnMensajeDeExitoSiSePudoMejorarCorrectamente() {

        when(servicioHerreriaMock.mejorarEquipamiento(mejoraDtoMock.getEquipamiento(), mejoraDtoMock.getOroUsuario())).thenReturn(true);

        ModelAndView mav = controladorHerreria.mejorarEquipamiento(mejoraDtoMock);

        String vistaEsperada = "herreria";
        String vistaObtenida = mav.getViewName();

        String mensajeEsperado = "El equipamiento se ha mejorado correctamente";
        String mensajeObtenida = mav.getModel().get("mensaje").toString();

        assertThat(vistaObtenida, equalToIgnoringCase(vistaEsperada));
        assertThat(mensajeObtenida, equalToIgnoringCase(mensajeEsperado));
    }

    @Test
    public void queAlMejorarUnEquipamientoSeMuestreUnMensajeDeFalloSiNoSePudoMejorarCorrectamente() {

        when(servicioHerreriaMock.mejorarEquipamiento(mejoraDtoMock.getEquipamiento(), mejoraDtoMock.getOroUsuario())).thenReturn(false);

        ModelAndView mav = controladorHerreria.mejorarEquipamiento(mejoraDtoMock);

        String vistaEsperada = "herreria";
        String vistaObtenida = mav.getViewName();

        String mensajeEsperado = "El equipamiento no se ha podido mejorar";
        String mensajeObtenida = mav.getModel().get("mensaje").toString();


        assertThat(vistaObtenida, equalToIgnoringCase(vistaEsperada));
        assertThat(mensajeObtenida, equalToIgnoringCase(mensajeEsperado));
    }

    @Test
    public void queAlMejorarUnEquipamientoSeVuelvaAMostrarElInventario() {

        when(servicioHerreriaMock.mejorarEquipamiento(mejoraDtoMock.getEquipamiento(), mejoraDtoMock.getOroUsuario())).thenReturn(false);

        ModelAndView mav = controladorHerreria.mejorarEquipamiento(mejoraDtoMock);

        String vistaEsperada = "herreria";
        String vistaObtenida = mav.getViewName();

        assertThat(vistaObtenida, equalToIgnoringCase(vistaEsperada));
        assertThat(mav.getModel().get("inventario"), notNullValue());
    }

}
