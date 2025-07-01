package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Arma;
import com.tallerwebi.dominio.ServicioAmigo;
import com.tallerwebi.dominio.ServicioHerreria;
import com.tallerwebi.dominio.excepcion.InventarioVacioException;
import com.tallerwebi.dominio.excepcion.PersonajeInvalidoException;
import com.tallerwebi.dominio.excepcion.SolicitudInvalidaException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.*;

public class ControladorAmigoTest {

    private ControladorAmigo controladorAmigo;
    private ServicioAmigo servicioAmigoMock;
    private ModelAndView mav;
    private HttpSession sessionMock;
    private RedirectAttributes redirectAttributesMock;
    private Long idPersonajeMock;
    private String codigoAmigoMock;

    @BeforeEach
    public void init(){
        servicioAmigoMock = mock(ServicioAmigo.class);
        controladorAmigo = new ControladorAmigo(servicioAmigoMock);
        sessionMock = mock(HttpSession.class);
        redirectAttributesMock = mock(RedirectAttributes.class);
        idPersonajeMock = 1l;
        codigoAmigoMock = "codigoAmigoMock";
    }

    @Test
    public void queSiElIdDePersonajeNoEstaSeteadoEnLaSesionSeHagaRedirectALoginYdejeUnMensajeDeError() {
        when(sessionMock.getAttribute("idPersonaje")).thenReturn(null);

        this.mav = controladorAmigo.verAmigos(sessionMock, redirectAttributesMock);

        String vistaEsperada = "redirect:/login";
        String vistaObtenida = mav.getViewName();

        assertThat(vistaObtenida, equalToIgnoringCase(vistaEsperada));

        verify(redirectAttributesMock).addFlashAttribute("error", "No puede acceder a la vista amigos sin haber iniciado sesion");
    }

    @Test
    public void queElMetodoVerAmigosGuardeTodaLaInformacionNecesariaEnElModelYMuestreLaVistaAmigos() {
        when(sessionMock.getAttribute("idPersonaje")).thenReturn(idPersonajeMock);
        when(servicioAmigoMock.obtenerCodigoAmigoPropio(idPersonajeMock)).thenReturn(codigoAmigoMock);
        when(servicioAmigoMock.obtenerAmigos(idPersonajeMock)).thenReturn(new ArrayList<>());
        when(servicioAmigoMock.obtenerSolicitudesEnviadas(idPersonajeMock)).thenReturn(new ArrayList<>());
        when(servicioAmigoMock.obtenerSolicitudesRecibidas(idPersonajeMock)).thenReturn(new ArrayList<>());

        this.mav = controladorAmigo.verAmigos(sessionMock, redirectAttributesMock);

        String vistaEsperada = "amigos";
        String vistaObtenida = mav.getViewName();

        assertThat(vistaObtenida, equalToIgnoringCase(vistaEsperada));
        assertThat(mav.getModel().get("codigoAmigo"), notNullValue());
        assertThat(mav.getModel().get("amigos"), notNullValue());
        assertThat(mav.getModel().get("solicitudesRecibidas"), notNullValue());
        assertThat(mav.getModel().get("solicitudesEnviadas"), notNullValue());
    }

    @Test
    public void queElMetodoEnviarSolicitudRedirijaALaVistaAmigosConUnMensajeDeExitoSiSeEnviaLaSolicitud() throws PersonajeInvalidoException {
        doNothing().when(servicioAmigoMock).enviarSolicitud(anyLong(), anyString());

        this.mav = controladorAmigo.enviarSolicitudAmistad(codigoAmigoMock, sessionMock, redirectAttributesMock);

        String vistaEsperada = "redirect:/amigos";
        String vistaObtenida = mav.getViewName();

        assertThat(vistaObtenida, equalToIgnoringCase(vistaEsperada));
        verify(redirectAttributesMock).addFlashAttribute("mensaje", "Solicitud enviada");
    }

    @Test
    public void queElMetodoEnviarSolicitudRedirijaALaVistaAmigosConUnMensajeDeErrorSiNoSeEnviaLaSolicitud() throws PersonajeInvalidoException {
        when(sessionMock.getAttribute("idPersonaje")).thenReturn(idPersonajeMock);

        doThrow(new PersonajeInvalidoException("No existe un personaje con este codigo de amistad"))
                .when(servicioAmigoMock)
                .enviarSolicitud(idPersonajeMock, codigoAmigoMock);


        this.mav = controladorAmigo.enviarSolicitudAmistad(codigoAmigoMock, sessionMock, redirectAttributesMock);

        String vistaEsperada = "redirect:/amigos";
        String vistaObtenida = this.mav.getViewName();

        assertThat(vistaObtenida, equalToIgnoringCase(vistaEsperada));
        verify(redirectAttributesMock).addFlashAttribute("error", "No existe un personaje con este codigo de amistad");
    }

    @Test
    public void queElMetodoAceptarSolicitudRedirijaALaVistaAmigosConUnMensajeDeExitoSiSeAceptaLaSolicitud() throws SolicitudInvalidaException {
        doNothing().when(servicioAmigoMock).aceptarSolicitud(idPersonajeMock);

        this.mav = controladorAmigo.aceptarSolicitud(idPersonajeMock, redirectAttributesMock);

        String vistaEsperada = "redirect:/amigos";
        String vistaObtenida = mav.getViewName();

        assertThat(vistaObtenida, equalToIgnoringCase(vistaEsperada));
        verify(redirectAttributesMock).addFlashAttribute("mensaje", "Solicitud aceptada");
    }

    @Test
    public void queElMetodoAceptarSolicitudRedirijaALaVistaAmigosConUnMensajeDeErrorSiNoSePuedeAceptarLaSolicitud() throws SolicitudInvalidaException {
        doThrow(new SolicitudInvalidaException("La solicitud de amistad no existe"))
                .when(servicioAmigoMock)
                .aceptarSolicitud(idPersonajeMock);

        this.mav = controladorAmigo.aceptarSolicitud(idPersonajeMock, redirectAttributesMock);

        String vistaEsperada = "redirect:/amigos";
        String vistaObtenida = mav.getViewName();

        assertThat(vistaObtenida, equalToIgnoringCase(vistaEsperada));
        verify(redirectAttributesMock).addFlashAttribute("error", "La solicitud de amistad no existe");
    }

    @Test
    public void queElMetodoRechazarSolicitudRedirijaALaVistaAmigosConUnMensajeDeExitoSiSeRechazaLaSolicitud() throws SolicitudInvalidaException {
        doNothing().when(servicioAmigoMock).rechazarSolicitud(idPersonajeMock);

        this.mav = controladorAmigo.rechazarSolicitud(idPersonajeMock, redirectAttributesMock);

        String vistaEsperada = "redirect:/amigos";
        String vistaObtenida = mav.getViewName();

        assertThat(vistaObtenida, equalToIgnoringCase(vistaEsperada));
        verify(redirectAttributesMock).addFlashAttribute("mensaje", "Solicitud rechazada");
    }

    @Test
    public void queElMetodoRechazarSolicitudRedirijaALaVistaAmigosConUnMensajeDeErrorSiNoSePuedeRechazarLaSolicitud() throws SolicitudInvalidaException {
        doThrow(new SolicitudInvalidaException("La solicitud de amistad no existe"))
                .when(servicioAmigoMock)
                .rechazarSolicitud(idPersonajeMock);

        this.mav = controladorAmigo.rechazarSolicitud(idPersonajeMock, redirectAttributesMock);

        String vistaEsperada = "redirect:/amigos";
        String vistaObtenida = mav.getViewName();

        assertThat(vistaObtenida, equalToIgnoringCase(vistaEsperada));
        verify(redirectAttributesMock).addFlashAttribute("error", "La solicitud de amistad no existe");
    }


}
