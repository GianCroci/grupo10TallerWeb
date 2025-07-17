package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.entidad.Arma;
import com.tallerwebi.dominio.entidad.Equipamiento;
import com.tallerwebi.dominio.excepcion.InventarioVacioException;
import com.tallerwebi.dominio.interfaz.servicio.ServicioInventario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

public class ControladorInventarioTest {

    private ServicioInventario servicioInventarioMock;
    private ControladorInventario controladorInventario;
    private HttpSession sessionMock;

    @BeforeEach
    public void init() {
        servicioInventarioMock = mock(ServicioInventario.class);
        sessionMock = mock(HttpSession.class);
        when(sessionMock.getAttribute("idPersonaje")).thenReturn(1L);

        controladorInventario = new ControladorInventario(servicioInventarioMock);
    }

    @Test
    public void redirigeAlLoginSiNoHaySesion() {
        when(sessionMock.getAttribute("idPersonaje")).thenReturn(null);

        ModelAndView mav = controladorInventario.verEquipamiento(null, sessionMock);

        assertThat(mav.getViewName(), equalTo("redirect:/login"));
    }

    @Test
    public void muestraInventarioFiltradoYEquipoSeleccionado() throws InventarioVacioException {
        List<Equipamiento> inventarioMock = List.of(new Arma(), new Arma());
        Equipamiento seleccionadoMock = inventarioMock.get(0);

        when(servicioInventarioMock.obtenerInventarioFiltradoPorTipo(1L, "Arma")).thenReturn(inventarioMock);
        when(servicioInventarioMock.obtenerEquipoSeleccionado(inventarioMock, 1L)).thenReturn(seleccionadoMock);

        ModelAndView mav = controladorInventario.verEquipamiento("Arma", sessionMock);

        assertThat(mav.getViewName(), equalTo("inventario"));
        assertThat(mav.getModel().get("inventario"), equalTo(inventarioMock));
        assertThat(mav.getModel().get("equipoSeleccionado"), equalTo(seleccionadoMock));
        assertThat(mav.getModel().get("tipoSeleccionado"), equalTo("Arma"));
    }

    @Test
    public void muestraMensajeErrorSiElInventarioEstaVacio() throws InventarioVacioException {
        when(servicioInventarioMock.obtenerInventarioFiltradoPorTipo(1L, null))
                .thenThrow(new InventarioVacioException("El inventario está vacío!"));

        ModelAndView mav = controladorInventario.verEquipamiento(null, sessionMock);

        assertThat(mav.getViewName(), equalTo("inventario"));
        assertThat(mav.getModel().get("mensajeError"), equalTo("El inventario está vacío!"));
    }

    @Test
    public void muestraEquipoEspecificoSiExiste() throws InventarioVacioException {
        List<Equipamiento> inventarioCompleto = List.of(new Arma(), new Arma());
        List<Equipamiento> inventarioFiltrado = List.of(new Arma());
        Equipamiento equipoPorId = new Arma();

        when(servicioInventarioMock.obtenerInventario(1L)).thenReturn(inventarioCompleto);
        when(servicioInventarioMock.obtenerInventarioFiltradoPorTipo(1L, "Arma")).thenReturn(inventarioFiltrado);
        when(servicioInventarioMock.obtenerEquipamientoPorId(1L, 99L)).thenReturn(equipoPorId);
        when(servicioInventarioMock.validarEquipoSeleccionadoEnFiltro(inventarioFiltrado, equipoPorId, inventarioCompleto))
                .thenReturn(equipoPorId);

        ModelAndView mav = controladorInventario.verEquipoEspecifico(99L, "Arma", sessionMock);

        assertThat(mav.getViewName(), equalTo("inventario"));
        assertThat(mav.getModel().get("inventario"), equalTo(inventarioFiltrado));
        assertThat(mav.getModel().get("equipoSeleccionado"), equalTo(equipoPorId));
        assertThat(mav.getModel().get("tipoSeleccionado"), equalTo("Arma"));
    }

    @Test
    public void redirigeAlLoginSiNoHaySesionParaEquipar() {
        when(sessionMock.getAttribute("idPersonaje")).thenReturn(null);

        String vista = controladorInventario.equipar(3L, null, sessionMock);

        assertThat(vista, equalTo("redirect:/login"));
    }

    @Test
    public void equipoSeEquipaCorrectamenteYRedirigeATodosLosEquipos() throws InventarioVacioException {
        String vista = controladorInventario.equipar(5L, null, sessionMock);

        assertThat(vista, equalTo("redirect:/inventario"));
        verify(servicioInventarioMock).equipar(1L, 5L);
    }

    @Test
    public void equipoSeEquipaCorrectamenteYRedirigeConTipo() throws InventarioVacioException {
        String vista = controladorInventario.equipar(5L, "Arma", sessionMock);

        assertThat(vista, equalTo("redirect:/inventario?tipo=Arma"));
        verify(servicioInventarioMock).equipar(1L, 5L);
    }

    @Test
    public void muestraMensajeDeErrorSiNosePudoEquipar() throws InventarioVacioException {
        doThrow(new InventarioVacioException("Error al equipar")).when(servicioInventarioMock).equipar(1L, 5L);

        String vista = controladorInventario.equipar(5L, "Arma", sessionMock);

        assertThat(vista, equalTo("redirect:/inventario?tipo=Arma"));
        verify(servicioInventarioMock).equipar(1L, 5L);
    }
}
