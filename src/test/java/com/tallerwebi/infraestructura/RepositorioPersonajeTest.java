package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.*;
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
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
    private Personaje rival;
    private Long idPersonajeGuardado;
    private Estadisticas estadisticas;
    private Guerrero guerrero;

    @BeforeEach
    public void init() {
        repositorioPersonaje = new RepositorioPersonajeImpl(sessionFactory);
        session = sessionFactory.getCurrentSession();
        estadisticas = new Estadisticas();

    }

    @Test
    public void queSePuedaGuardarUnPersonaje() {
        personaje = new Personaje();
        personaje.setNombre("Arthas");
        personaje.setGenero("Masculino");
        guerrero = new Guerrero();
        personaje.setRol(guerrero);
        estadisticas.setFuerza(10);
        estadisticas.setInteligencia(5);
        estadisticas.setArmadura(8);
        estadisticas.setAgilidad(6);
        personaje.setEstadisticas(estadisticas);
        personaje.setImagen("guerrero.png");
        personaje.setOro(500);

        session.save(guerrero);
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
        guerrero = new Guerrero();
        personaje.setRol(guerrero);
        estadisticas.setFuerza(10);
        estadisticas.setInteligencia(5);
        estadisticas.setArmadura(8);
        estadisticas.setAgilidad(6);
        personaje.setEstadisticas(estadisticas);
        personaje.setImagen("guerrero.png");
        personaje.setOro(500);

        session.save(guerrero);
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
        guerrero = new Guerrero();
        personaje.setRol(guerrero);
        estadisticas.setFuerza(10);
        estadisticas.setInteligencia(5);
        estadisticas.setArmadura(8);
        estadisticas.setAgilidad(6);
        personaje.setEstadisticas(estadisticas);
        personaje.setImagen("guerrero.png");
        personaje.setOro(500);

        session.save(guerrero);
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
        guerrero = new Guerrero();
        personaje.setRol(guerrero);
        estadisticas.setFuerza(10);
        estadisticas.setInteligencia(5);
        estadisticas.setArmadura(8);
        estadisticas.setAgilidad(6);
        personaje.setEstadisticas(estadisticas);
        personaje.setImagen("guerrero.png");
        personaje.setOro(500);

        session.save(guerrero);
        session.save(personaje);

        idPersonajeGuardado = personaje.getId();

        Personaje personajeEsperado = personaje;
        Personaje personajeObtenido = repositorioPersonaje.buscarPersonaje(idPersonajeGuardado);

        assertThat(personajeObtenido, is(personajeEsperado));
    }

    @Test
    public void queSePuedaBuscarUnRival() {
        personaje = new Personaje();
        personaje.setNombre("Arthas");
        personaje.setGenero("Masculino");
        guerrero = new Guerrero();
        personaje.setRol(guerrero);
        estadisticas.setFuerza(10);
        estadisticas.setInteligencia(5);
        estadisticas.setArmadura(8);
        estadisticas.setAgilidad(6);
        personaje.setEstadisticas(estadisticas);
        personaje.setImagen("guerrero.png");
        personaje.setOro(500);

        session.save(guerrero);
        session.save(personaje);

        rival = new Personaje();
        rival.setNombre("nacho");
        rival.setGenero("Masculino");
        rival.setRol(guerrero);
        rival.setEstadisticas(estadisticas);
        rival.setImagen("guerrero.png");
        rival.setOro(500);

        session.save(rival);

        session.flush();

        Personaje rivalObtenido = repositorioPersonaje.buscarRival(personaje.getId());

        assertNotEquals(rivalObtenido.getId(), personaje.getId());
        assertNotNull(rivalObtenido);

    }
}
