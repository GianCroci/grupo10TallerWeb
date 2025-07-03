package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Personaje;
import com.tallerwebi.dominio.PersonajeTaberna;
import com.tallerwebi.dominio.RepositorioTaberna;
import com.tallerwebi.infraestructura.config.HibernateInfraestructuraTestConfig;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {HibernateInfraestructuraTestConfig.class})
@Transactional
public class RepositorioTabernaTest {

    @Autowired
    private SessionFactory sessionFactory;

    private RepositorioTaberna repositorioTaberna;

    private PersonajeTaberna personajeTaberna;


    @BeforeEach
    public void init() {
        repositorioTaberna = new RepositorioTabernaImpl(sessionFactory);
        personajeTaberna =  PersonajeTaberna.MERCADER;

    }

    @Test
    public void queSePuedaInvitarCerveza() {
        Personaje personaje = new Personaje();
        personaje.setNombre("Arthas");
        personaje.setGenero("Masculino");

        sessionFactory.getCurrentSession().save(personaje);


        int cantidadDeCervezasEsperadas=1;

        repositorioTaberna.invitarCerveza(personaje, personajeTaberna);

        assertEquals(cantidadDeCervezasEsperadas, repositorioTaberna.getCantidadCervezasInvitadas(personaje, personajeTaberna));
    }

    @Test
    public void queSePuedaInvitarVariasCervezas() {
        Personaje personaje = new Personaje();
        personaje.setNombre("Arthas");
        personaje.setGenero("Masculino");

        sessionFactory.getCurrentSession().save(personaje);

        int cantidadDeCervezasEsperadas = 1;

        for (int i = 0; i < cantidadDeCervezasEsperadas; i++) {
            repositorioTaberna.invitarCerveza(personaje, personajeTaberna);
        }

        assertEquals(cantidadDeCervezasEsperadas, repositorioTaberna.getCantidadCervezasInvitadas(personaje, personajeTaberna));
    }

    @Test
    public void queNoSePuedaInvitarCervezaSiYaSeInvitoHoy() {
        Personaje personaje = new Personaje();
        personaje.setNombre("Arthas");
        personaje.setGenero("Masculino");

        sessionFactory.getCurrentSession().save(personaje);

        repositorioTaberna.invitarCerveza(personaje, personajeTaberna);

        // Intentar invitar de nuevo el mismo día
        repositorioTaberna.invitarCerveza(personaje, personajeTaberna);

        // Verificar que la cantidad de cervezas invitadas sigue siendo 1
        assertEquals(1, repositorioTaberna.getCantidadCervezasInvitadas(personaje, personajeTaberna));
    }
}
