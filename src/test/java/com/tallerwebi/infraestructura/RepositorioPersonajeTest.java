package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Personaje;
import com.tallerwebi.dominio.RepositorioPersonaje;
import com.tallerwebi.infraestructura.config.HibernateInfraestructuraTestConfig;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {HibernateInfraestructuraTestConfig.class})
@Transactional
public class RepositorioPersonajeTest {

    @Autowired
    private SessionFactory sessionFactory;
    private RepositorioPersonaje repositorioPersonaje;
    private Session session;
    private Personaje personaje;
    private Long idPersonajeGuardado;

    @BeforeEach
    public void init() {
        repositorioPersonaje = new RepositorioPersonajeImpl(sessionFactory);
        session = sessionFactory.getCurrentSession();
    }

    @Test
    public void queSePuedaGuardarUnPersonaje() {
        personaje = new Personaje();
        personaje.setNombre("Arthas");
        personaje.setGenero("Masculino");
        personaje.setRol("Guerrero");
        personaje.setFuerza(10);
        personaje.setInteligencia(5);
        personaje.setArmadura(8);
        personaje.setAgilidad(6);
        personaje.setImagen("guerrero.png");
        personaje.setOro(500);

        repositorioPersonaje.guardar(personaje);

        Long idPersonajeGuardado = personaje.getId();

        Personaje personajeObtenido = (Personaje) session.createCriteria(Personaje.class)
                                        .add(Restrictions.eq("id", idPersonajeGuardado))
                                        .uniqueResult();

        assertThat(personajeObtenido, is(personaje));

    }

    @Test
    public void queSePuedaActualizarUnPersonaje() {
        personaje = new Personaje();
        personaje.setNombre("Arthas");
        personaje.setGenero("Masculino");
        personaje.setRol("Guerrero");
        personaje.setFuerza(10);
        personaje.setInteligencia(5);
        personaje.setArmadura(8);
        personaje.setAgilidad(6);
        personaje.setImagen("guerrero.png");
        personaje.setOro(500);

        session.save(personaje);
        Long idPersonajeGuardado = personaje.getId();

        personaje.setNombre("hola");

        repositorioPersonaje.modificar(personaje);

        Personaje personajeObtenido = (Personaje) session.createCriteria(Personaje.class)
                                        .add(Restrictions.eq("id", idPersonajeGuardado))
                                        .uniqueResult();

        String nombreEsperado = "hola";
        String nombreObtenido = personajeObtenido.getNombre();

        assertThat(nombreObtenido, equalToIgnoringCase(nombreEsperado));
    }

    @Test
    public void queSePuedaObtenerElOroDeUnPersonajeATravesDeSuIdDePersonaje() {
        personaje = new Personaje();
        personaje.setNombre("Arthas");
        personaje.setGenero("Masculino");
        personaje.setRol("Guerrero");
        personaje.setFuerza(10);
        personaje.setInteligencia(5);
        personaje.setArmadura(8);
        personaje.setAgilidad(6);
        personaje.setImagen("guerrero.png");
        personaje.setOro(500);

        session.save(personaje);

        idPersonajeGuardado = personaje.getId();

        Integer valorEsperado = 500;
        Integer valorObtenido = repositorioPersonaje.buscarOroPersonaje(idPersonajeGuardado);

        assertThat(valorObtenido, is(valorEsperado));
    }

    @Test
    public void queSePuedaObtenerUnPersonajeATravesDeSuIdDePersonaje() {
        personaje = new Personaje();
        personaje.setNombre("Arthas");
        personaje.setGenero("Masculino");
        personaje.setRol("Guerrero");
        personaje.setFuerza(10);
        personaje.setInteligencia(5);
        personaje.setArmadura(8);
        personaje.setAgilidad(6);
        personaje.setImagen("guerrero.png");
        personaje.setOro(500);

        session.save(personaje);

        idPersonajeGuardado = personaje.getId();

        Personaje personajeEsperado = personaje;
        Personaje personajeObtenido = repositorioPersonaje.buscarPersonaje(idPersonajeGuardado);

        assertThat(personajeObtenido, is(personajeEsperado));
    }
}
