package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import com.tallerwebi.infraestructura.RepositorioInventarioImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ServicioHerreriaTest {

    private RepositorioInventario repositorioInventario;
    private ServicioHerreria servicioHerreria;

    private ServicioTaberna servicioTaberna;
    private MejoraDto mejoraDtoMock;

    @BeforeEach
    public void init(){
        repositorioInventario = mock(RepositorioInventarioImpl.class);
        mejoraDtoMock = mock(MejoraDto.class);
        servicioTaberna = mock(ServicioTaberna.class);
        servicioHerreria = new ServicioHerreriaImpl(repositorioInventario);

    }

    @Test
    public void queSePuedanObtenerLosTodosLosEquipamientosDelPersonaje() {

        List<Equipamiento> inventario = servicioHerreria.obtenerInventario();

        assertThat(inventario, hasItems());
    }
/*
    @Test
    public void queSePuedaRealizarUnaMejoraDeEquipamientoSiLaCantidadDeOroEsSuficiente() {
        when(mejoraDtoMock.getEquipamiento()).thenReturn(new Equipamiento("espada", 100.0, 100.0, 100.0, 15, 10, 10, 10, false));
        when(mejoraDtoMock.getOroUsuario()).thenReturn(200.0);

        Boolean estadoMejora = servicioHerreria.mejorarEquipamiento(mejoraDtoMock.getEquipamiento(), mejoraDtoMock.getOroUsuario());

        assertThat(estadoMejora, is(true));
    }
*/
    @Test
    public void queNoSePuedaRealizarUnaMejoraDeEquipamientoSiLaCantidadDeOroEsInsuficiente() {
        when(mejoraDtoMock.getEquipamiento()).thenReturn(new Equipamiento("espada", 100.0, 100.0, 100.0, 15, 10, 10, 10, false));

        when(mejoraDtoMock.getOroUsuario()).thenReturn(5.0);

        when(servicioTaberna.getCervezasInvitadas(PersonajeTaberna.HERRERO)).thenReturn(5);

        Boolean estadoMejora = servicioHerreria.mejorarEquipamiento(mejoraDtoMock.getEquipamiento(), mejoraDtoMock.getOroUsuario());

        assertThat(estadoMejora, is(false));
    }

}
