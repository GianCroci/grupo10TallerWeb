package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Personaje;
import com.tallerwebi.dominio.RepositorioPersonaje;
import com.tallerwebi.infraestructura.config.HibernateInfraestructuraTestConfig;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {HibernateInfraestructuraTestConfig.class})
@Transactional
public class RepositorioPersonajeTest {

    @Autowired
    private SessionFactory sessionFactory;
    private RepositorioPersonaje repositorioPersonaje;
    private Personaje personaje;
    private Long idPersonajeMock;

    @BeforeEach
    public void init() {
        repositorioPersonaje = new RepositorioPersonajeImpl(sessionFactory);

        Session session = sessionFactory.getCurrentSession();

        personaje = new Personaje();
        personaje.setNombre("Arthas");
        personaje.setGenero("Masculino");
        personaje.setRol("Guerrero");
        personaje.setFuerza(10);
        personaje.setInteligencia(5);
        personaje.setArmadura(8);
        personaje.setAgilidad(6);
        personaje.setImagen("arthas.png");
        personaje.setOro(500);

        session.save(personaje);

        idPersonajeMock = personaje.getId();
    }

    @Test
    public void queSePuedaObtenerElOroDeUnPersonajeATravesDeSuIdDePersonaje() {
        Integer valorEsperado = 500;
        Integer valorObtenido = repositorioPersonaje.buscarOroPersonaje(idPersonajeMock);

        assertThat(valorObtenido, is(valorEsperado));
    }

    @Test
    public void queSePuedaObtenerUnPersonajeATravesDeSuIdDePersonaje() {

        Personaje personajeEsperado = personaje;
        Personaje personajeObtenido = repositorioPersonaje.buscarPersonaje(idPersonajeMock);

        assertThat(personajeObtenido, equalTo(personajeEsperado));
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
