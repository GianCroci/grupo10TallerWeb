package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class ControladorInventarioTest {

    ServicioEquipamientoImpl servicioEquipamiento = new ServicioEquipamientoImpl();

    @Test
    public void verEquipamiento(){
        //preparación
        ControladorEquipamiento controlador = new ControladorEquipamiento(servicioEquipamiento);
        String vistaEsperada = "equipamiento";


        //ejecución
        ModelAndView mav = controlador.verEquipamiento();


        //verificación
        assertThat(vistaEsperada, equalTo(mav.getViewName()));
        assertThat(mav.getModel().get("contenido"),notNullValue());
    }
}