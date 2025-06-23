package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.excepcion.InventarioVacioException;
import com.tallerwebi.dominio.excepcion.NivelDeEquipamientoMaximoException;
import com.tallerwebi.dominio.excepcion.OroInsuficienteException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    private List<Equipamiento> equipamientosVacioMock;
    private List<Equipamiento> equipamientosConCosasMock;
    private HttpSession sessionMock;
    private Long idPersonajeMock;
    private RedirectAttributes redirectAttributesMock;


    @BeforeEach
    public void init() throws InventarioVacioException {
        redirectAttributesMock = mock(RedirectAttributes.class);
        servicioHerreriaMock = mock(ServicioHerreria.class);
        controladorHerreria = new ControladorHerreria(servicioHerreriaMock);
        mejoraDtoMock = mock(MejoraDto.class);
        sessionMock = mock(HttpSession.class);
        idPersonajeMock = 1L;
        when(sessionMock.getAttribute("idPersonaje")).thenReturn(idPersonajeMock);
        equipamientosVacioMock = new ArrayList<>();
        equipamientosConCosasMock = new ArrayList<>();
        equipamientosConCosasMock.add(new Arma());
        when(servicioHerreriaMock.obtenerInventario(idPersonajeMock)).thenReturn(equipamientosVacioMock);
        Integer oroPersonaje = 50;
        when(servicioHerreriaMock.obtenerOroDelPersonaje(idPersonajeMock)).thenReturn(oroPersonaje);
    }

    @Test
    public void queSePuedaIrALaHerreria() {
        ModelAndView mav = controladorHerreria.irALaHerreria(sessionMock, redirectAttributesMock);

        String vistaEsperada = "herreria";
        String vistaObtenida = mav.getViewName();

        assertThat(vistaObtenida, equalToIgnoringCase(vistaEsperada));
    }

    @Test
    public void queAlIrALaHerreriaSeGuardeEnLaVistaUnModelMapConMejoraDto() {
        ModelAndView mav = controladorHerreria.irALaHerreria(sessionMock, redirectAttributesMock);

        String vistaEsperada = "herreria";
        String vistaObtenida = mav.getViewName();

        assertThat(vistaObtenida, equalToIgnoringCase(vistaEsperada));
        assertThat(mav.getModel().get("mejoraDto"), notNullValue());
    }

    @Test
    public void queAlIrALaHerreriaSeGuardeEnLaVistaUnModelMapConUnInventario() throws InventarioVacioException {
        when(servicioHerreriaMock.obtenerInventario(idPersonajeMock)).thenReturn(equipamientosConCosasMock);
        ModelAndView mav = controladorHerreria.irALaHerreria(sessionMock, redirectAttributesMock);

        String vistaEsperada = "herreria";
        String vistaObtenida = mav.getViewName();

        assertThat(vistaObtenida, equalToIgnoringCase(vistaEsperada));
        assertThat(mav.getModel().get("inventario"), equalTo(equipamientosConCosasMock));
    }

    @Test
    public void queAlIrALaHerreriaSeLanceUnaInventarioVacioExceptionSiElInventarioObtenidoEstaVacio() throws InventarioVacioException {
        doThrow(new InventarioVacioException("No se han encontrado equipamientos en su inventario"))
                .when(servicioHerreriaMock)
                .obtenerInventario(any());
        ModelAndView mav = controladorHerreria.irALaHerreria(sessionMock, redirectAttributesMock);

        String vistaEsperada = "herreria";
        String vistaObtenida = mav.getViewName();

        String mensajeEsperado = "No se han encontrado equipamientos en su inventario";
        String mensajeObtenida = mav.getModel().get("error").toString();

        assertThat(vistaObtenida, equalToIgnoringCase(vistaEsperada));
        assertThat(mensajeObtenida, equalToIgnoringCase(mensajeEsperado));
    }

    @Test
    public void queAlIrALaHerreriaYSeLanceUnaInventarioVacioExceptionSeElimineELCampoInventarioDelModel() throws InventarioVacioException {
        doThrow(new InventarioVacioException("No se han encontrado equipamientos en su inventario"))
                .when(servicioHerreriaMock)
                .obtenerInventario(any());
        ModelAndView mav = controladorHerreria.irALaHerreria(sessionMock, redirectAttributesMock);

        String vistaEsperada = "herreria";
        String vistaObtenida = mav.getViewName();

        assertThat(vistaObtenida, equalToIgnoringCase(vistaEsperada));
        assertThat(mav.getModel().get("inventario"), nullValue());
    }

    @Test
    public void queSiElMetodoIrALaHerreriaNoRecibeElIdDePersonajeDelObjetoHttpSessionLanceUnMensajeDeErrorYHagaRedirectLogin() {
        when(sessionMock.getAttribute("idPersonaje")).thenReturn(null);
        ModelAndView mav = controladorHerreria.irALaHerreria(sessionMock, redirectAttributesMock);

        String vistaEsperada = "redirect:/login";
        String vistaObtenida = mav.getViewName();

        assertThat(vistaObtenida, equalToIgnoringCase(vistaEsperada));

        verify(redirectAttributesMock).addFlashAttribute("error", "No puede acceder a la vista herreria sin haber iniciado sesion");
    }

    @Test
    public void queElMetodoIrALaHerreriaRecibaElOroDelPersonajeYLoGuardeEnElModel() {
        ModelAndView mav = controladorHerreria.irALaHerreria(sessionMock, redirectAttributesMock);

        String vistaEsperada = "herreria";
        String vistaObtenida = mav.getViewName();

        Integer mensajeEsperado = 50;
        Integer mensajeObtenida = (Integer) (mav.getModel().get("oroPersonaje"));

        assertThat(vistaObtenida, equalToIgnoringCase(vistaEsperada));
        assertThat(mensajeObtenida, equalTo(mensajeEsperado));
    }

    @Test
    public void queAlMejorarUnEquipamientoSeMuestreUnMensajeDeExitoSiSePudoMejorarCorrectamente() throws NivelDeEquipamientoMaximoException, OroInsuficienteException {
        doNothing().when(servicioHerreriaMock).mejorarEquipamiento(any(), any(), any());

        ModelAndView mav = controladorHerreria.mejorarEquipamiento(mejoraDtoMock, sessionMock, redirectAttributesMock);

        String vistaEsperada = "redirect:/herreria";
        String vistaObtenida = mav.getViewName();

        assertThat(vistaObtenida, equalToIgnoringCase(vistaEsperada));
        verify(redirectAttributesMock).addFlashAttribute("estadoMejora", "El equipamiento se ha mejorado correctamente");
        verify(redirectAttributesMock).addFlashAttribute("tipoEstadoMejora", "success");
    }

    @Test
    public void queAlMejorarUnEquipamientoSeMuestreUnMensajeDeFalloDeNivelDeEquipamientoMaximoExceptionSiElEquipamientoTieneNivelMaximo() throws NivelDeEquipamientoMaximoException, OroInsuficienteException {
        doThrow(new NivelDeEquipamientoMaximoException("Se ha alcanzado el nivel maximo de este equipamiento"))
                .when(servicioHerreriaMock)
                .mejorarEquipamiento(any(), any(), any());

        ModelAndView mav = controladorHerreria.mejorarEquipamiento(mejoraDtoMock, sessionMock, redirectAttributesMock);

        String vistaEsperada = "redirect:/herreria";
        String vistaObtenida = mav.getViewName();

        assertThat(vistaObtenida, equalToIgnoringCase(vistaEsperada));
        verify(redirectAttributesMock).addFlashAttribute("estadoMejora", "Se ha alcanzado el nivel maximo de este equipamiento");
        verify(redirectAttributesMock).addFlashAttribute("tipoEstadoMejora", "danger");
    }

    @Test
    public void queAlMejorarUnEquipamientoSeMuestreUnMensajeDeFalloDeOroInsuficienteExceptionSiElOroDelPersonajeEsInsuficiente() throws NivelDeEquipamientoMaximoException, OroInsuficienteException {
        doThrow(new OroInsuficienteException("Tu oro no es suficiente para realizar esta accion"))
                .when(servicioHerreriaMock)
                .mejorarEquipamiento(any(), any(), any());

        ModelAndView mav = controladorHerreria.mejorarEquipamiento(mejoraDtoMock, sessionMock, redirectAttributesMock);

        String vistaEsperada = "redirect:/herreria";
        String vistaObtenida = mav.getViewName();

        assertThat(vistaObtenida, equalToIgnoringCase(vistaEsperada));
        verify(redirectAttributesMock).addFlashAttribute("estadoMejora", "Tu oro no es suficiente para realizar esta accion");
        verify(redirectAttributesMock).addFlashAttribute("tipoEstadoMejora", "danger");
    }
}