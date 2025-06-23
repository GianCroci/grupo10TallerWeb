package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Mercado;
import com.tallerwebi.dominio.ServicioMercado;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ControladorMercadoTest {

    private ControladorMercado controladorMercado;
    private ServicioMercado servicioMercado;

    @BeforeEach
    public void init() {
        servicioMercado = mock(ServicioMercado.class);
        controladorMercado = new ControladorMercado(servicioMercado);
    }
/*
    @Test
    public void queSePuedaVerVistaDeMercado() {
        when(servicioMercado.mostrarMercado()).thenReturn(new Mercado());

        ModelAndView modelAndView = controladorMercado.mostrarMercado();

        assertThat(modelAndView.getViewName(), equalTo("mercado"));
    }



    @Test
    public void queSePuedaProcesarUnaCompraExitosa() {
        List<String> seleccionados = List.of("Tunica azul", "Cinturon oro");

        when(servicioMercado.procesarCompra(seleccionados)).thenReturn("¡Compra realizada con éxito! Has comprado: Tunica azul, Cinturon oro");
        when(servicioMercado.mostrarMercado()).thenReturn(new Mercado());

        ModelAndView modelAndView = controladorMercado.realizarCompra(seleccionados);

        assertThat(modelAndView.getViewName(), equalTo("mercado"));
        assertThat(modelAndView.getModel().get("compraExitosa"), equalTo("¡Compra realizada con éxito! Has comprado: Tunica azul, Cinturon oro"));
    }


    @Test
    public void queDevuelvaMensajeErrorSiNoSeleccionoNada() {
        when(servicioMercado.procesarCompra(null)).thenReturn("No seleccionaste ningún objeto");
        when(servicioMercado.mostrarMercado()).thenReturn(new Mercado());

        ModelAndView modelAndView = controladorMercado.realizarCompra(null);

        assertThat(modelAndView.getViewName(), equalTo("mercado"));
        assertThat(modelAndView.getModel().get("compraExitosa"), equalTo("No seleccionaste ningún objeto"));
    }
    */

}
