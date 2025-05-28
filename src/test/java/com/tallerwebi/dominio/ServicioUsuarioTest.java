package com.tallerwebi.dominio;

import com.tallerwebi.presentacion.DatosLogin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class ServicioUsuarioTest {

    private RepositorioUsuario repoUsuarioMock;
    private ServicioUsuario servicioUsuario;
    private Personaje personajeMockeado;
    private Usuario usuarioMock;
    private DatosLogin datosLoginMock;
    private HttpServletRequest requestMock;
    private HttpSession sessionMock;

    @BeforeEach
    public void init(){
        personajeMockeado = mock(Personaje.class);
        repoUsuarioMock = mock(RepositorioUsuario.class);
        servicioUsuario = new ServicioUsuarioImpl(repoUsuarioMock);
        datosLoginMock = new DatosLogin("gian@unlam.com", "1234");
        usuarioMock = mock(Usuario.class);
        requestMock = mock(HttpServletRequest.class);
        sessionMock = mock(HttpSession.class);
    }


    @Test
    public void queSeLePuedaSetearUnPersonajeAUnUsuarioLogueado() {
        // preparacion
        Usuario usuarioEncontradoMock = mock(Usuario.class);
        when(usuarioMock.getEmail()).thenReturn("gian@unlam.com");
        when(repoUsuarioMock.buscar("gian@unlam.com")).thenReturn(usuarioEncontradoMock);

        servicioUsuario.setUsuario(usuarioMock);
        Boolean seSeteo = servicioUsuario.setPersonaje(personajeMockeado);

        verify(usuarioEncontradoMock, times(1)).setPersonaje(personajeMockeado);
        assertThat(seSeteo, equalTo(true));

    }

    @Test
    public void queSePuedaBUscarUnUsuarioLogueado() {

        when(repoUsuarioMock.buscar("gian@unlam.com")).thenReturn(usuarioMock);

        servicioUsuario.setUsuario(usuarioMock);
        Usuario usuarioEncontrado = servicioUsuario.buscar("gian@unlam.com");

        assertThat(usuarioEncontrado, equalTo(usuarioMock));
    }
}
