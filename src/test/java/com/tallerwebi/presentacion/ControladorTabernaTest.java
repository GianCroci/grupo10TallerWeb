package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioTaberna;

import com.tallerwebi.dominio.Taberna;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.*;
public class ControladorTabernaTest {

    private ControladorTaberna controladorTaberna;
    private ServicioTaberna servicioTaberna;

    @BeforeEach
    public void init() {
        servicioTaberna = mock(ServicioTaberna.class);
        controladorTaberna = new ControladorTaberna(servicioTaberna);
    }


    @Test
    public void queSePuedaVerLaTaberna(){

        ModelAndView modelAndView= controladorTaberna.mostrarTaberna();

        assertThat(modelAndView.getViewName(), equalTo("taberna"));
    }


}
