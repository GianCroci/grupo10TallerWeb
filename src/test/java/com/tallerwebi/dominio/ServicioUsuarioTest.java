package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidad.Personaje;
import com.tallerwebi.dominio.entidad.Usuario;
import com.tallerwebi.dominio.interfaz.repositorio.RepositorioUsuario;
import com.tallerwebi.dominio.interfaz.servicio.ServicioUsuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.hamcrest.MatcherAssert.assertThat;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class ServicioUsuarioTest {

    private RepositorioUsuario repoUsuarioMock;
    private ServicioUsuario servicioUsuario;
    private Personaje personajeMockeado;
    private Usuario usuarioMock1;


    @BeforeEach
    public void init(){
        personajeMockeado = mock(Personaje.class);
        repoUsuarioMock = mock(RepositorioUsuario.class);
        servicioUsuario = new ServicioUsuarioImpl(repoUsuarioMock);
        usuarioMock1 = mock(Usuario.class);

    }


    @Test
    public void queSeLePuedaSetearUnPersonajeAUnUsuarioLogueado() {
        // preparacion
        Usuario usuarioEncontradoMock = mock(Usuario.class);
        when(usuarioMock1.getEmail()).thenReturn("gian@unlam.com");
        when(repoUsuarioMock.buscar("gian@unlam.com")).thenReturn(usuarioEncontradoMock);

        servicioUsuario.setUsuario(usuarioEncontradoMock);
        servicioUsuario.setPersonaje(personajeMockeado, usuarioEncontradoMock);

        verify(usuarioEncontradoMock, times(1)).setPersonaje(personajeMockeado);

    }

    @Test
    public void queSePuedaBuscarUnUsuarioPorMail(){
        // preparacion
        when(repoUsuarioMock.buscar("gian@unlam.com")).thenReturn(usuarioMock1);
        // ejecucion
        Usuario usuarioEncontrado = servicioUsuario.buscar("gian@unlam.com");
        // verificacion
        assertThat(usuarioEncontrado, equalTo(usuarioMock1));
    }

}
