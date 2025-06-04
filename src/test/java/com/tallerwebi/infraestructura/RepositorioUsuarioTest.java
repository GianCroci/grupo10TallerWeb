package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Personaje;
import com.tallerwebi.dominio.RepositorioUsuario;
import com.tallerwebi.dominio.Usuario;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.*;

public class RepositorioUsuarioTest {

    private SessionFactory sessionFactoryMock;
    private Session sessionMock;
    private Criteria criteriaMock;
    private Usuario usuarioMockeado;
    private RepositorioUsuario repoUsuario;

    @BeforeEach
    public void init(){
        sessionFactoryMock = mock(SessionFactory.class);
        sessionMock = mock(Session.class);
        criteriaMock = mock(Criteria.class);
        usuarioMockeado = mock(Usuario.class);
        repoUsuario = new RepositorioUsuarioImpl(sessionFactoryMock);
    }

    @Test
    public void queSePuedaGuardarUnUsuario() {
        //preparacion
        when(sessionFactoryMock.getCurrentSession()).thenReturn(sessionMock);

        //ejecucion
        repoUsuario.guardar(usuarioMockeado);

        //verificacion
        verify(sessionFactoryMock, times(1)).getCurrentSession();
        verify(sessionMock, times(1)).save(usuarioMockeado);
    }

    @Test
    public void queSePuedaBuscarUnUsuario() {
        //preparacion
        when(usuarioMockeado.getEmail()).thenReturn("gian@gmail.com");
        when(usuarioMockeado.getPassword()).thenReturn("1234");
        when(sessionFactoryMock.getCurrentSession()).thenReturn(sessionMock);
        when(sessionMock.createCriteria(Usuario.class)).thenReturn(criteriaMock);
        when(criteriaMock.add(any())).thenReturn(criteriaMock);
        when(criteriaMock.uniqueResult()).thenReturn(usuarioMockeado);

        //ejecucion
        Usuario usuarioEncontrado = repoUsuario.buscarUsuario("gian@gmail.com", "1234");

        //verificacion

        assertThat(usuarioMockeado.getEmail(), equalTo(usuarioEncontrado.getEmail()));
        assertThat(usuarioMockeado.getPassword(), equalTo(usuarioEncontrado.getPassword()));
        verify(sessionFactoryMock, times(1)).getCurrentSession();
        verify(sessionMock, times(1)).createCriteria(Usuario.class);
        verify(criteriaMock, times(2)).add(any());
        verify(criteriaMock, times(1)).uniqueResult();
    }

    @Test
    public void queSePuedaActualizarUnUsuario() {
        //preparacion
        when(sessionFactoryMock.getCurrentSession()).thenReturn(sessionMock);

        //ejecucion
        repoUsuario.modificar(usuarioMockeado);

        //verificacion
        verify(sessionFactoryMock, times(1)).getCurrentSession();
        verify(sessionMock, times(1)).update(usuarioMockeado);
    }
}
