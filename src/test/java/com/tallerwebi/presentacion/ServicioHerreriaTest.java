package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Equipamiento;
import com.tallerwebi.dominio.ServicioHerreria;
import com.tallerwebi.dominio.ServicioHerreriaImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ServicioHerreriaTest {

    private ServicioHerreria servicioHerreria;
    private MejoraDto mejoraDtoMock;

    @BeforeEach
    public void init(){
        mejoraDtoMock = mock(MejoraDto.class);
        servicioHerreria = new ServicioHerreriaImpl();

    }
    /*
    @Test
    public void queSePuedaRealizarUnaMejoraDeEquipamientoSiLaCantidadDeOroEsSuficiente() {
        when(mejoraDtoMock.getEquipamiento()).thenReturn(new Equipamiento("espadita", "Espada"));
        when(mejoraDtoMock.getOro()).thenReturn(50);

        Boolean estadoMejora = servicioHerreria.mejorarEquipamiento(mejoraDtoMock.getEquipamiento(), mejoraDtoMock.getOro());

        assertThat(estadoMejora, is(true));
    }

    @Test
    public void queNoSePuedaRealizarUnaMejoraDeEquipamientoSiLaCantidadDeOroEsInsuficiente() {
        when(mejoraDtoMock.getEquipamiento()).thenReturn(new Equipamiento("espadita", "Espada"));
        when(mejoraDtoMock.getOro()).thenReturn(5);

        Boolean estadoMejora = servicioHerreria.mejorarEquipamiento(mejoraDtoMock.getEquipamiento(), mejoraDtoMock.getOro());

        assertThat(estadoMejora, is(false));
    }

    @Test
    public void queSePuedaMejorarUnEquipamientoSiSeEncuentraEnLaBaseDeDatos() {
        when(mejoraDtoMock.getEquipamiento()).thenReturn(new Equipamiento("espadita", "Espada"));
        when(mejoraDtoMock.getOro()).thenReturn(50);

        Boolean estadoMejora = servicioHerreria.mejorarEquipamiento(mejoraDtoMock.getEquipamiento(), mejoraDtoMock.getOro());

        assertThat(estadoMejora, is(true));
    }
*/

}
