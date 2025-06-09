package com.tallerwebi.presentacion;
import com.tallerwebi.dominio.RepositorioMercado;
import com.tallerwebi.dominio.ServicioMercado;

import com.tallerwebi.dominio.ServicioMercadoImpl;
import com.tallerwebi.dominio.ServicioTaberna;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.mock;

public class ControladorMercadoTest {

    private ControladorMercado controladorMercado;
    private ServicioMercado servicioMercado;
    private RepositorioMercado repositorioMercado;
    private ServicioTaberna servicioTabernaMock;
    @BeforeEach
    public void init() {
        repositorioMercado = mock(RepositorioMercado.class);
        servicioTabernaMock = mock(ServicioTaberna.class);
        servicioMercado = new ServicioMercadoImpl(repositorioMercado, servicioTabernaMock);
        controladorMercado = new ControladorMercado(servicioMercado);
    }

    @Test
    public void queSePuedaVerVistaDeMercado(){

        ModelAndView modelAndView= controladorMercado.mostrarMercado();

        assertThat(modelAndView.getViewName(), equalTo("mercado"));
    }

    //@Test
    //public void queDevuelvaMensajeExitoAlComprar() {
    //    List<String> seleccionados = Arrays.asList("espada-corta", "pan-duro");

    //    String resultado = servicioMercado.procesarCompra(seleccionados);

    //    assertThat(resultado, equalTo("¡Compra realizada con éxito! Has comprado: espada-corta, pan-duro"));
    //}

    //@Test
    //public void queDevuelvaMensajeErrorSiNoSeleccionoNada() {
    //    List<String> seleccionados = Collections.emptyList();

    //   String resultado = servicioMercado.procesarCompra(seleccionados);

    //    assertThat(resultado, equalTo("No seleccionaste ningún objeto"));
    //}
}
