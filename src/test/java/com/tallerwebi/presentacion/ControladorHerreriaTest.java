package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.excepcion.NivelDeEquipamientoMaximoException;
import com.tallerwebi.dominio.excepcion.OroInsuficienteException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

public class ControladorHerreriaTest {

    private ServicioHerreria servicioHerreriaMock;
    private ControladorHerreria controladorHerreria;
    private MejoraDto mejoraDtoMock;
    private List<Equipamiento> equipamientosMock;
    private HttpSession sessionMock;
    private Long idPersonajeMock;


    @BeforeEach
    public void init(){
        servicioHerreriaMock = mock(ServicioHerreria.class);
        controladorHerreria = new ControladorHerreria(servicioHerreriaMock);
        mejoraDtoMock = mock(MejoraDto.class);
        sessionMock = mock(HttpSession.class);
        idPersonajeMock = 1L;
        when(sessionMock.getAttribute("idPersonaje")).thenReturn(idPersonajeMock);
        equipamientosMock = new ArrayList<>();
        when(servicioHerreriaMock.obtenerInventario(idPersonajeMock)).thenReturn(equipamientosMock);
        Integer oroPersonaje = 50;
        when(servicioHerreriaMock.obtenerOroDelPersonaje(idPersonajeMock)).thenReturn(oroPersonaje);
    }

    @Test
    public void queSePuedaIrALaHerreria() {

        ModelAndView mav = controladorHerreria.irALaHerreria(sessionMock);

        String vistaEsperada = "herreria";
        String vistaObtenida = mav.getViewName();

        assertThat(vistaObtenida, equalToIgnoringCase(vistaEsperada));
    }

    @Test
    public void queAlIrALaHerreriaSeGuardeEnLaVistaUnModelMapConMejoraDto() {

        ModelAndView mav = controladorHerreria.irALaHerreria(sessionMock);

        String vistaEsperada = "herreria";
        String vistaObtenida = mav.getViewName();

        assertThat(vistaObtenida, equalToIgnoringCase(vistaEsperada));
        assertThat(mav.getModel().get("mejoraDto"), notNullValue());
    }

    @Test
    public void queAlIrALaHerreriaSeGuardeEnLaVistaUnModelMapConUnInventario() {

        ModelAndView mav = controladorHerreria.irALaHerreria(sessionMock);

        String vistaEsperada = "herreria";
        String vistaObtenida = mav.getViewName();

        assertThat(vistaObtenida, equalToIgnoringCase(vistaEsperada));
        assertThat(mav.getModel().get("inventario"), notNullValue());
    }

    @Test
    public void queAlIrALaHerreriaSeGuardeUnMensajeDeErrorSiElInventarioEstaVacio() {

        ModelAndView mav = controladorHerreria.irALaHerreria(sessionMock);

        String vistaEsperada = "herreria";
        String vistaObtenida = mav.getViewName();

        String mensajeEsperado = "No hay equipamientos para mejorar";
        String mensajeObtenida = mav.getModel().get("vacio").toString();

        assertThat(vistaObtenida, equalToIgnoringCase(vistaEsperada));
        assertThat(mensajeObtenida, equalToIgnoringCase(mensajeEsperado));
    }

    @Test
    public void queElMetodoIrALaHerreriaRecibaUnObjetoHttpSessionQueContengaElIdDelPersonaje() {

        ModelAndView mav = controladorHerreria.irALaHerreria(sessionMock);

        String vistaEsperada = "herreria";
        String vistaObtenida = mav.getViewName();

        String mensajeEsperado = "No hay equipamientos para mejorar";
        String mensajeObtenida = mav.getModel().get("vacio").toString();

        assertThat(vistaObtenida, equalToIgnoringCase(vistaEsperada));
        assertThat(mensajeObtenida, equalToIgnoringCase(mensajeEsperado));
    }

    @Test
    public void queElMetodoIrALaHerreriaRecibaElOroDelPersonajeYLoGuardeEnElModel() {

        ModelAndView mav = controladorHerreria.irALaHerreria(sessionMock);

        String vistaEsperada = "herreria";
        String vistaObtenida = mav.getViewName();

        Integer mensajeEsperado = 50;
        Integer mensajeObtenida = (Integer) (mav.getModel().get("oroPersonaje"));

        assertThat(vistaObtenida, equalToIgnoringCase(vistaEsperada));
        assertThat(mensajeObtenida, equalTo(mensajeEsperado));
    }

    @Test
    public void queAlMejorarUnEquipamientoSeMuestreUnMensajeDeExitoSiSePudoMejorarCorrectamente() throws NivelDeEquipamientoMaximoException, OroInsuficienteException {

        doNothing().when(servicioHerreriaMock).mejorarEquipamiento(any(), any());

        ModelAndView mav = controladorHerreria.mejorarEquipamiento(mejoraDtoMock);

        String vistaEsperada = "redirect:/herreria";
        String vistaObtenida = mav.getViewName();

        String mensajeEsperado = "El equipamiento se ha mejorado correctamente";
        String mensajeObtenida = mav.getModel().get("mensaje").toString();

        assertThat(vistaObtenida, equalToIgnoringCase(vistaEsperada));
        assertThat(mensajeObtenida, equalToIgnoringCase(mensajeEsperado));
    }

    @Test
    public void queAlMejorarUnEquipamientoSeMuestreUnMensajeDeFalloDeNivelDeEquipamientoMaximoExceptionSiElEquipamientoTieneNivelMaximo() throws NivelDeEquipamientoMaximoException, OroInsuficienteException {

        doThrow(new NivelDeEquipamientoMaximoException("Se ha alcanzado el nivel maximo de este equipamiento"))
                .when(servicioHerreriaMock)
                .mejorarEquipamiento(any(), any());

        ModelAndView mav = controladorHerreria.mejorarEquipamiento(mejoraDtoMock);


        String vistaEsperada = "redirect:/herreria";
        String vistaObtenida = mav.getViewName();

        String mensajeEsperado = "Se ha alcanzado el nivel maximo de este equipamiento";
        String mensajeObtenida = mav.getModel().get("mensaje").toString();

        assertThat(vistaObtenida, equalToIgnoringCase(vistaEsperada));
        assertThat(mensajeObtenida, equalToIgnoringCase(mensajeEsperado));
    }

    @Test
    public void queAlMejorarUnEquipamientoSeMuestreUnMensajeDeFalloDeOroInsuficienteExceptionSiElOroDelPersonajeEsInsuficiente() throws NivelDeEquipamientoMaximoException, OroInsuficienteException {

        doThrow(new OroInsuficienteException("Tu oro no es suficiente para realizar esta accion"))
                .when(servicioHerreriaMock)
                .mejorarEquipamiento(any(), any());

        ModelAndView mav = controladorHerreria.mejorarEquipamiento(mejoraDtoMock);


        String vistaEsperada = "redirect:/herreria";
        String vistaObtenida = mav.getViewName();

        String mensajeEsperado = "Tu oro no es suficiente para realizar esta accion";
        String mensajeObtenida = mav.getModel().get("mensaje").toString();

        assertThat(vistaObtenida, equalToIgnoringCase(vistaEsperada));
        assertThat(mensajeObtenida, equalToIgnoringCase(mensajeEsperado));
    }

}
