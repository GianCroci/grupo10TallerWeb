package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.*;
import com.tallerwebi.infraestructura.config.HibernateInfraestructuraTestConfig;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.hamcrest.Matchers.is;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {HibernateInfraestructuraTestConfig.class})
@Transactional
public class RepositorioInventarioTest {

    @Autowired
    private SessionFactory sessionFactory;
    private RepositorioInventario repositorioInventario;
    private Personaje personaje;
    private Personaje personaje2;
    private Long idPersonaje1Mock;
    private Long idPersonaje2Mock;
    private Equipamiento equipamiento1;
    private Equipamiento equipamiento2;
    private Equipamiento equipamiento3;
    private Long idEquipamientoMock;
    private Estadisticas statsMock;

    @BeforeEach
    public void init() {
        repositorioInventario = new RepositorioInventarioImpl(sessionFactory);

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

        idPersonaje1Mock = personaje.getId();

        personaje2 = new Personaje();
        personaje2.setNombre("Si");
        session.save(personaje2);

        idPersonaje2Mock = personaje2.getId();

        equipamiento1 = new Arma();
        equipamiento1.setNombre("espada");
        equipamiento1.setPersonaje(personaje);
        session.save(equipamiento1);

        equipamiento2 = new Arma();
        equipamiento2.setNombre("baston");
        equipamiento2.setPersonaje(personaje);
        session.save(equipamiento2);
        idEquipamientoMock = equipamiento2.getId();

        equipamiento3 = new Arma();
        equipamiento3.setNombre("daga");
        equipamiento3.setPersonaje(personaje2);
        session.save(equipamiento3);

    }

    @Test
    public void queSePuedanObtenerTodosLosEquipamientosDelPersonajeDeId1() {

        List<Equipamiento> equipamientosObtenidosDeUnPersonaje = repositorioInventario.obtenerInventario(idPersonaje1Mock);
        assertThat(equipamientosObtenidosDeUnPersonaje.size(), is(2));
    }

    @Test
    public void queSePuedanObtenerTodosLosEquipamientosDelPersonajeDeId2() {

        List<Equipamiento> equipamientosObtenidosDeUnPersonaje = repositorioInventario.obtenerInventario(idPersonaje2Mock);
        assertThat(equipamientosObtenidosDeUnPersonaje.size(), is(1));
    }

    @Test
    public void queSePuedanObtenerElEquipamientosDelPersonajeDeId2() {

        Equipamiento equipamientoObtenido = repositorioInventario.obtenerEquipamientoPorId(idEquipamientoMock);
        assertThat(equipamientoObtenido.getNombre(), equalToIgnoringCase("baston"));
    }



}
