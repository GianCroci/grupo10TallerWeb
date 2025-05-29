package com.tallerwebi.dominio;

import com.tallerwebi.presentacion.DatosLogin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class ServicioUsuarioTest {

    private RepositorioUsuario repoUsuarioMock;
    private ServicioUsuario servicioUsuario;
    private Personaje personajeMockeado;
    private Usuario usuarioMock1;
    private Usuario usuarioMock2;
    private DatosLogin datosLoginMock;
    private HttpServletRequest requestMock;
    private HttpSession sessionMock;

    @BeforeEach
    public void init(){
        personajeMockeado = mock(Personaje.class);
        repoUsuarioMock = mock(RepositorioUsuario.class);
        servicioUsuario = new ServicioUsuarioImpl(repoUsuarioMock);
        datosLoginMock = new DatosLogin("gian@unlam.com", "1234");
        usuarioMock1 = mock(Usuario.class);
        usuarioMock2 = mock(Usuario.class);
        requestMock = mock(HttpServletRequest.class);
        sessionMock = mock(HttpSession.class);
        List<Usuario> usuarios = List.of(usuarioMock1, usuarioMock2);
    }


    @Test
    public void queSeLePuedaSetearUnPersonajeAUnUsuarioLogueado() {
        // preparacion
        Usuario usuarioEncontradoMock = mock(Usuario.class);
        when(usuarioMock1.getEmail()).thenReturn("gian@unlam.com");
        when(repoUsuarioMock.buscar("gian@unlam.com")).thenReturn(usuarioEncontradoMock);

        servicioUsuario.setUsuario(usuarioEncontradoMock);
        Boolean seSeteo = servicioUsuario.setPersonaje(personajeMockeado, usuarioEncontradoMock);

        verify(usuarioEncontradoMock, times(1)).setPersonaje(personajeMockeado);
        assertThat(seSeteo, equalTo(true));

    }

}
