package com.tallerwebi.presentacion;
import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.excepcion.InventarioVacioException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.*;

public class ControladorInventarioTest {

    private ServicioInventario servicioInventarioMock;
    private ControladorInventario controladorInventario;
    private HttpSession sessionMock;


    @BeforeEach
    public void init(){
        servicioInventarioMock = mock(ServicioInventario.class);
        sessionMock = mock(HttpSession.class);
        when(sessionMock.getAttribute("idPersonaje")).thenReturn(1L);

        controladorInventario = new ControladorInventario(servicioInventarioMock);
    }

    @Test
    public void queRedirijaAlLoginSiNoHayUnaSesionIniciada() throws InventarioVacioException {
        when(sessionMock.getAttribute("idPersonaje")).thenReturn(null);

        ModelAndView mav = controladorInventario.verEquipamiento(sessionMock);

        assertThat(mav.getViewName(), equalTo("redirect:/login"));
    }

    @Test
    public void queSePuedaVerElInventarioYElPrimerEquipadoSiExiste() throws InventarioVacioException {
        List<Equipamiento> inventarioMock = List.of(new Arma(), new Arma());
        when(servicioInventarioMock.obtenerInventario(1L)).thenReturn(inventarioMock);
        when(servicioInventarioMock.obtenerPrimerEquipado(1L)).thenReturn(new Arma());

        ModelAndView mav = controladorInventario.verEquipamiento(sessionMock);

        assertThat(mav.getViewName(), equalTo("inventario"));
        assertThat(mav.getModel().get("inventario"), equalTo(inventarioMock));
        assertThat(mav.getModel().get("equipoSeleccionado"), notNullValue());
    }

    @Test
    public void queSePuedaVerElInventarioYElPrimerEquipo() throws InventarioVacioException {
        List<Equipamiento> inventarioMock = List.of(new Arma(), new Arma());
        when(servicioInventarioMock.obtenerInventario(1L)).thenReturn(inventarioMock);
        when(servicioInventarioMock.obtenerPrimerEquipado(1L)).thenReturn(null);

        ModelAndView mav = controladorInventario.verEquipamiento(sessionMock);

        assertThat(mav.getViewName(), equalTo("inventario"));
        assertThat(mav.getModel().get("equipoSeleccionado"), equalTo(inventarioMock.get(0)));
    }

    @Test
    public void queSeVeaUnMensajeSiElInventarioEstaVacio() throws InventarioVacioException {
        when(servicioInventarioMock.obtenerInventario(1L)).thenThrow(new InventarioVacioException("El inventario está vacío!"));

        ModelAndView mav = controladorInventario.verEquipamiento(sessionMock);

        assertThat(mav.getViewName(), equalTo("inventario"));
        assertThat(mav.getModel().get("mensajeError"), equalTo("El inventario está vacío!"));
    }

    @Test
    public void QueSeVeaLaInfoDelEquipoSeleccionado() throws InventarioVacioException {

        List<Equipamiento> inventarioMock = List.of(new Arma(), new Arma());
        Equipamiento equipoMock = new Arma();

        when(servicioInventarioMock.obtenerInventario(1L)).thenReturn(inventarioMock);
        when(servicioInventarioMock.obtenerEquipamientoPorId(1L, 2L)).thenReturn(equipoMock);

        ModelAndView mav = controladorInventario.verEquipoEspecifico(sessionMock, 2L);

        assertThat(mav.getViewName(), equalTo("inventario"));
        assertThat(mav.getModel().get("inventario"), equalTo(inventarioMock));
        assertThat(mav.getModel().get("equipoSeleccionado"), equalTo(equipoMock));
    }

    @Test
    public void queSeEquipeCorrectamenteYRedirijaAlInventario() throws InventarioVacioException {

        String vista = controladorInventario.equipar(sessionMock, 5L);

        assertThat(vista, equalTo("redirect:/inventario"));
        verify(servicioInventarioMock).equipar(1L, 5L);
    }

}