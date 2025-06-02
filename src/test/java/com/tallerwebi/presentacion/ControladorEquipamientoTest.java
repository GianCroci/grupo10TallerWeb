package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Arma;
import com.tallerwebi.dominio.Equipamiento;
import com.tallerwebi.dominio.ServicioEquipamiento;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ControladorEquipamientoTest {

    private ServicioEquipamiento servicioEquipamiento;
    private ControladorEquipamiento controlador;

    @BeforeEach
    void init() {
        servicioEquipamiento = mock(ServicioEquipamiento.class);
        controlador = new ControladorEquipamiento(servicioEquipamiento);
    }

    @Test
    void queSePuedaVerLaPaginaDeEquipamiento() {
        Equipamiento eq1 = new Arma();
        eq1.setNombre("espada");
        eq1.setEquipado(true);

        Equipamiento eq2 = new Arma();
        eq2.setNombre("daga");
        eq2.setEquipado(false);

        List<Equipamiento> listaMock = Arrays.asList(eq1, eq2);

        when(servicioEquipamiento.mostrarEquipamiento()).thenReturn(listaMock);

        ModelAndView mav = controlador.verEquipamiento();

        assertEquals("equipamiento", mav.getViewName());

        List<Equipamiento> contenido = (List<Equipamiento>) mav.getModel().get("contenido");
        assertEquals(2, contenido.size());
        assertEquals("espada", ((Equipamiento) mav.getModel().get("equipoSeleccionado")).getNombre());

        verify(servicioEquipamiento).mostrarEquipamiento();
    }
}
