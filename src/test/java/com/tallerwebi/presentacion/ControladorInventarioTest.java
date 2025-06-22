package com.tallerwebi.presentacion;
import com.tallerwebi.dominio.Arma;
import com.tallerwebi.dominio.Equipamiento;
import com.tallerwebi.dominio.ServicioInventario;
import com.tallerwebi.dominio.RepositorioInventario;
import com.tallerwebi.presentacion.ControladorInventario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ControladorInventarioTest {

    private ServicioInventario servicioInventarioMock;
    private RepositorioInventario repositorioInventarioMock;
    private ControladorInventario controlador;

    @BeforeEach
    public void init() {
        servicioInventarioMock = mock(ServicioInventario.class);
        repositorioInventarioMock = mock(RepositorioInventario.class);
        controlador = new ControladorInventario(servicioInventarioMock, repositorioInventarioMock);
    }

    @Test
    public void verEquipamientoYQueDevuelvaUnaListaDeEquipos() {
        List<Equipamiento> listaFalsa = Arrays.asList(new Arma(), new Arma());
        Equipamiento equipadoFalso = new Arma();

        HttpSession sessionMock = mock(HttpSession.class);
        when(sessionMock.getAttribute("idPersonaje")).thenReturn(1L);

        when(servicioInventarioMock.mostrarEquipamiento()).thenReturn(listaFalsa);
        when(servicioInventarioMock.mostrarPrimerEquipado()).thenReturn(equipadoFalso);
        when(repositorioInventarioMock.obtenerComprasDePersonaje(1L)).thenReturn(listaFalsa);

        ModelAndView mav = controlador.verEquipamiento(sessionMock);

        assertEquals("inventario", mav.getViewName());
        assertEquals(listaFalsa, mav.getModel().get("contenido"));
        assertEquals(equipadoFalso, mav.getModel().get("equipoSeleccionado"));
        assertEquals(listaFalsa, mav.getModel().get("comprasRealizadas"));
    }


@Test
    public void verInformacionDeUnEquipoEspecifico() {
        List<Equipamiento> listaFalsa = Arrays.asList(new Arma(), new Arma());
        Equipamiento equipoFalso = new Arma();

        when(servicioInventarioMock.mostrarEquipamiento()).thenReturn(listaFalsa);
        when(servicioInventarioMock.buscarEquipamientoPorId(1L)).thenReturn(equipoFalso);

        ModelAndView mav = controlador.verEquipoEspecifico(1L);

        assertEquals("inventario", mav.getViewName());
        assertEquals(listaFalsa, mav.getModel().get("contenido"));
        assertEquals(equipoFalso, mav.getModel().get("equipoSeleccionado"));
    }

    @Test
    public void queCuandoEquipoUnArmaMeRedirigaAlDetalleDelArmaQueEquipe() {
        String resultado = controlador.equipar(1L);
        assertEquals("redirect:/inventario", resultado);
    }
}
