package com.tallerwebi.presentacion;
import com.tallerwebi.dominio.entidad.Mercado;
import com.tallerwebi.dominio.interfaz.servicio.ServicioMercado;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.*;

public class ControladorMercadoTest {

    private ControladorMercado controladorMercado;
    private ServicioMercado servicioMercado;
    private HttpSession session;

    @BeforeEach
    public void init() {
        servicioMercado = mock(ServicioMercado.class);
        session = mock(HttpSession.class);
        controladorMercado = new ControladorMercado(servicioMercado);
    }

    @Test
    public void queRedirijaAlLoginSiNoHaySesionAlMostrarMercado() {
        when(session.getAttribute("idPersonaje")).thenReturn(null);
        ModelAndView modelAndView = controladorMercado.mostrarMercado(session);
        assertThat(modelAndView.getViewName(), equalTo("redirect:/login"));
    }


    @Test
    public void queRedirijaAlLoginSiNoHaySesionAlRealizarCompra() {
        when(session.getAttribute("idPersonaje")).thenReturn(null);
        ModelAndView modelAndView = controladorMercado.realizarCompra(List.of("Objeto1"), session);
        assertThat(modelAndView.getViewName(), equalTo("redirect:/login"));
    }

    @Test
    public void queSePuedaVerVistaDeMercadoConSesionValida() {
        Long idPersonaje = 1L;
        when(session.getAttribute("idPersonaje")).thenReturn(idPersonaje);
        when(servicioMercado.obtenerOroDelPersonaje(idPersonaje)).thenReturn(100);
        when(servicioMercado.mostrarMercado()).thenReturn(new Mercado());

        ModelAndView modelAndView = controladorMercado.mostrarMercado(session);

        assertThat(modelAndView.getViewName(), equalTo("mercado"));
        assertThat(modelAndView.getModel().get("oroPersonaje"), equalTo(100));
    }


    @Test
    public void queSePuedaProcesarUnaCompraExitosaConSesionValida() {
        Long idPersonaje = 1L;
        List<String> seleccionados = List.of("Tunica azul", "Cinturon oro");

        when(session.getAttribute("idPersonaje")).thenReturn(idPersonaje);
        when(servicioMercado.procesarCompra(seleccionados, idPersonaje))
                .thenReturn("¡Compra realizada con éxito! Has comprado: Tunica azul, Cinturon oro");
        when(servicioMercado.obtenerOroDelPersonaje(idPersonaje)).thenReturn(50);
        when(servicioMercado.mostrarMercado()).thenReturn(new Mercado());

        ModelAndView modelAndView = controladorMercado.realizarCompra(seleccionados, session);

        assertThat(modelAndView.getViewName(), equalTo("mercado"));
        assertThat(modelAndView.getModel().get("compraExitosa"),
                equalTo("¡Compra realizada con éxito! Has comprado: Tunica azul, Cinturon oro"));
        assertThat(modelAndView.getModel().get("oroPersonaje"), equalTo(50));
        assertThat(modelAndView.getModel().get("verInventario"), equalTo(true));
    }

    @Test
    public void queDevuelvaMensajeErrorSiNoSeleccionoNada() {
        Long idPersonaje = 1L;

        when(session.getAttribute("idPersonaje")).thenReturn(idPersonaje);
        when(servicioMercado.procesarCompra(null, idPersonaje)).thenReturn("No seleccionaste ningún objeto");
        when(servicioMercado.obtenerOroDelPersonaje(idPersonaje)).thenReturn(30);
        when(servicioMercado.mostrarMercado()).thenReturn(new Mercado());

        ModelAndView modelAndView = controladorMercado.realizarCompra(null, session);

        assertThat(modelAndView.getViewName(), equalTo("mercado"));
        assertThat(modelAndView.getModel().get("error"), equalTo("No seleccionaste ningún objeto"));
        assertThat(modelAndView.getModel().get("oroPersonaje"), equalTo(30));
    }
}
