package com.tallerwebi.presentacion;
import com.tallerwebi.dominio.ServicioMercado;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.mock;

public class ControladorMercadoTest {

    private ControladorMercado controladorMercado;
    private ServicioMercado servicioMercado;

    @BeforeEach
    public void init() {
        servicioMercado = mock(ServicioMercado.class);
        controladorMercado = new ControladorMercado(servicioMercado);
    }

    @Test
    public void queSePuedaVerVistaDeMercado(){

        ModelAndView modelAndView= controladorMercado.mostrarMercado();

        assertThat(modelAndView.getViewName(), equalTo("mercado"));
    }
}
