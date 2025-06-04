package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Personaje;
import com.tallerwebi.dominio.RepositorioPersonaje;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.*;

public class RepositorioPersonajeTest {

    private SessionFactory sessionFactoryMock;
    private Session sessionMock;
    private Criteria criteriaMock;
    private Personaje personajeMockeado;
    private RepositorioPersonaje repoPersonaje;

    @BeforeEach
    public void init(){
        sessionFactoryMock = mock(SessionFactory.class);
        sessionMock = mock(Session.class);
        criteriaMock = mock(Criteria.class);
        personajeMockeado = mock(Personaje.class);
        repoPersonaje = new RepositorioPersonajeImpl(sessionFactoryMock);
    }

    @Test
    public void queSePuedaGuardarUnPersonaje() {
        //preparacion
        when(sessionFactoryMock.getCurrentSession()).thenReturn(sessionMock);

        //ejecucion
        repoPersonaje.guardar(personajeMockeado);

        verify(sessionFactoryMock, times(1)).getCurrentSession();
        verify(sessionMock, times(1)).save(personajeMockeado);
    }

    @Test
    public void queSePuedaBuscarUnPersonaje() {
        //preparacion
        when(personajeMockeado.getId()).thenReturn(1L);
        when(sessionFactoryMock.getCurrentSession()).thenReturn(sessionMock);
        when(sessionMock.createCriteria(Personaje.class)).thenReturn(criteriaMock);
        when(criteriaMock.add(any())).thenReturn(criteriaMock);
        when(criteriaMock.uniqueResult()).thenReturn(personajeMockeado);

        //ejecucion
        Personaje personajeEncontrado = repoPersonaje.buscarPersonaje(1L);

        //verificacion

        assertThat(personajeMockeado.getId(), equalTo(personajeEncontrado.getId()));
        verify(sessionFactoryMock, times(1)).getCurrentSession();
        verify(sessionMock, times(1)).createCriteria(Personaje.class);
        verify(criteriaMock, times(1)).add(any());
        verify(criteriaMock, times(1)).uniqueResult();
    }

    @Test
    public void queSePuedaActualizarUnPersonaje() {
        //preparacion
        when(sessionFactoryMock.getCurrentSession()).thenReturn(sessionMock);

        //ejecucion
        repoPersonaje.modificar(personajeMockeado);

        //verificacion
        verify(sessionFactoryMock, times(1)).getCurrentSession();
        verify(sessionMock, times(1)).update(personajeMockeado);
    }
}
