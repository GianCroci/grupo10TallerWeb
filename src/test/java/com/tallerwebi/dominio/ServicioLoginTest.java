package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidad.Usuario;
import com.tallerwebi.dominio.excepcion.UsuarioExistente;
import com.tallerwebi.dominio.interfaz.repositorio.RepositorioUsuario;
import com.tallerwebi.dominio.interfaz.servicio.ServicioLogin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class ServicioLoginTest {

    private RepositorioUsuario repoUsuarioMock;
    private Usuario usuarioMock;
    private ServicioLogin servicioLogin;

    @BeforeEach
    public void init() {
        repoUsuarioMock = mock(RepositorioUsuario.class);
        usuarioMock = mock(Usuario.class);
        servicioLogin = new ServicioLoginImpl(repoUsuarioMock);
    }

    @Test
    public void queSePuedaConsultarUsuarioConMailYPassword() {
        //preparacion
        String mailUsuario = "gian@unlam.com";
        String contrasenia = "1234";
        when(repoUsuarioMock.buscarUsuario(mailUsuario, contrasenia)).thenReturn(usuarioMock);

        //ejecucion
        Usuario usuarioEncontrado = servicioLogin.consultarUsuario(mailUsuario, contrasenia);

        //verificacion
        assertThat(usuarioEncontrado, equalTo(usuarioMock));
    }

    @Test
    public void queSePuedaRegistrarUnUsuario() throws UsuarioExistente {
        // preparación
        Usuario usuario = new Usuario();
        usuario.setEmail("gian@unlam.com");

        when(repoUsuarioMock.buscar("gian@unlam.com")).thenReturn(null);

        // ejecución
        servicioLogin.registrar(usuario);

        // verificación
        verify(repoUsuarioMock).guardar(usuario);
    }

    @Test
    public void queNoSePuedaRegistrarUnUsuarioConEmailExistente() {
        // preparación
        Usuario usuario = new Usuario();
        usuario.setEmail("gian@unlam.com");

        when(repoUsuarioMock.buscar("gian@unlam.com")).thenReturn(new Usuario()); // ya existe

        // ejecución + verificación
        assertThrows(UsuarioExistente.class, () -> {
            servicioLogin.registrar(usuario);
        });
    }



}
