package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioBatalla;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.mock;

public class ControladorBatallaTest {

    private ServicioBatalla servicioBatallaMock;
    private ControladorBatalla controladorBatalla;

    @BeforeEach
    public void init(){
        servicioBatallaMock = mock(ServicioBatalla.class);
        controladorBatalla = new ControladorBatalla(servicioBatallaMock);
    }

    @Test
    public void queHayaUnaVistaDeBatallaConDosPersonajes(){

        ModelAndView modelAndView = controladorBatalla.irABatalla();

        String vistaEsperada = "batalla";

        assertThat(vistaEsperada, equalTo(modelAndView.getViewName()));

    }
}
