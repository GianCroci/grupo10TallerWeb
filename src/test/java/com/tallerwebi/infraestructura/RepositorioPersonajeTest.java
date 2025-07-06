package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.entidad.Guerrero;
import com.tallerwebi.dominio.entidad.Personaje;
import com.tallerwebi.dominio.interfaz.repositorio.RepositorioPersonaje;
import com.tallerwebi.infraestructura.config.HibernateInfraestructuraTestConfig;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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

        assertThat(personajeObtenido, equalTo(personaje));

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

        assertThat(personajeObtenido, equalTo(personajeEsperado));
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

    @Test
    public void queSePuedaObtenerUnPersonajeATravesDeSuCodigoDeAmigo() {
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
        personaje.setCodigoAmigo("codigo01");

        session.save(guerrero);
        session.save(personaje);

        String codigoAmigo = "codigo01";

        Personaje personajeEsperado = personaje;
        Personaje personajeObtenido = repositorioPersonaje.buscarPersonajePorCodigoAmigo(codigoAmigo);

        assertThat(personajeEsperado, equalTo(personajeObtenido));
    }

    @Test
    public void queSePuedaObtenerLaListaDeAmigosDeUnPersonajeATravesDeSuId() {
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
        personaje.setCodigoAmigo("codigo01");



        Personaje personaje2 = new Personaje();
        personaje2.setNombre("Arthas");
        personaje2.setGenero("Masculino");
        personaje2.setRol(guerrero);
        personaje2.setEstadisticas(estadisticas);
        personaje2.setImagen("guerrero.png");
        personaje2.setOro(500);
        personaje2.setCodigoAmigo("codigo01");

        personaje.getAmigos().add(personaje2);

        session.save(guerrero);
        session.save(personaje);
        session.save(personaje2);

        Long idPersonaje = personaje.getId();

        List<Personaje> listaAmigos = repositorioPersonaje.obtenerAmigos(idPersonaje);

        Long idEsperado = personaje2.getId();
        Long idAmigoObtenido = listaAmigos.get(0).getId();

        assertThat(idEsperado, is(idAmigoObtenido));
    }

    @Test
    public void queSePuedaObtenerElCodigoDeAmigoDeUnPersonaje() {
        personaje = new Personaje();
        personaje.setCodigoAmigo("codigo01");

        session.save(personaje);

        String codigoAmigoEsperado = "codigo01";
        String codigoAmigoObtenido = repositorioPersonaje.obtenerCodigoAmigoPropio(personaje.getId());

        assertThat(codigoAmigoEsperado, equalToIgnoringCase(codigoAmigoObtenido));
    }
}
