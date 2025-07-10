package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.entidad.Personaje;
import com.tallerwebi.dominio.interfaz.repositorio.RepositorioProducto;
import com.tallerwebi.dominio.interfaz.servicio.ServicioMercadoPago;
import com.tallerwebi.dominio.interfaz.servicio.ServicioPersonaje;
import com.tallerwebi.dominio.excepcion.InventarioVacioException;
import com.tallerwebi.dominio.interfaz.servicio.ServicioProducto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.isNotNull;
import static org.mockito.Mockito.*;

public class ControladorHomeTest {

    private ControladorHome controladorHome;
    private ModelAndView mav;
    private HttpSession sessionMock;
    private ServicioPersonaje servicioPersonaje;
    private Personaje personajeMock;
    private RedirectAttributes redirectAttributesMock;
    private ServicioProducto servicioProducto;
    private ServicioMercadoPago servicioMercadoPago;
    private String operacionMPMock;

    @BeforeEach
    public void init() throws InventarioVacioException {
        servicioMercadoPago = mock(ServicioMercadoPago.class);
        servicioProducto = mock(ServicioProducto.class);
        servicioPersonaje = mock(ServicioPersonaje.class);
        controladorHome = new ControladorHome(servicioPersonaje, servicioProducto, servicioMercadoPago);
        sessionMock = mock(HttpSession.class);
        personajeMock = mock(Personaje.class);
        redirectAttributesMock = mock(RedirectAttributes.class);
        mav = null;
        operacionMPMock = "";
    }

    @Test
    public void queElControladorHomeTeEnvieALaVistaHomeConLosDatosDelPersonaje(){
        when(sessionMock.getAttribute("idPersonaje")).thenReturn(1L);
        when(servicioPersonaje.buscarPersonaje(1L)).thenReturn(personajeMock);
        mav = controladorHome.irAlHome(sessionMock, redirectAttributesMock);

        String vistaEsperada = "home";
        String vistaObtenida = mav.getViewName();

        assertThat(vistaEsperada, equalToIgnoringCase(vistaObtenida));
        assertThat(mav.getModel().get("infoPersonaje"), notNullValue());
    }

    @Test
    public void queElControladorHomeTeEnvieAlLoginSiNoHayUnIdPersonajeEnElHttpSession(){
        when(sessionMock.getAttribute("idPersonaje")).thenReturn(null);
        when(servicioPersonaje.buscarPersonaje(1L)).thenReturn(personajeMock);
        mav = controladorHome.irAlHome(sessionMock, redirectAttributesMock);

        String vistaEsperada = "redirect:/login";
        String vistaObtenida = mav.getViewName();

        assertThat(vistaEsperada, equalToIgnoringCase(vistaObtenida));
        verify(redirectAttributesMock).addFlashAttribute("error", "No puede acceder a la vista home sin haber iniciado sesion");
    }
}
