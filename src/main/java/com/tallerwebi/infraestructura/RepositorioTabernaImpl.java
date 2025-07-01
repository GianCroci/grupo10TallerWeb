package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Personaje;
import com.tallerwebi.dominio.PersonajeTaberna;
import com.tallerwebi.dominio.RepositorioTaberna;
import com.tallerwebi.dominio.Taberna;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDate;

@Transactional
@Repository("repositorioTaberna")
public class RepositorioTabernaImpl implements RepositorioTaberna {


    private SessionFactory sessionFactory;

    public RepositorioTabernaImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public int getCantidadCervezasInvitadas(Personaje personaje, PersonajeTaberna personajeTaberna) {
        String hql = "FROM Taberna WHERE personaje = :personaje AND personajeTaberna = :personajeTaberna";
        Taberna registro = (Taberna) sessionFactory.getCurrentSession()
                .createQuery(hql)
                .setParameter("personaje", personaje)
                .setParameter("personajeTaberna", personajeTaberna)
                .uniqueResult();

        return registro != null ? registro.getCervezasInvitadas() : 0;
    }


    @Override
    public void invitarCerveza(Personaje personaje, PersonajeTaberna personajeTaberna) {
        String hql = "FROM Taberna WHERE personaje = :personaje AND personajeTaberna = :personajeTaberna";
        Taberna registro = (Taberna) sessionFactory.getCurrentSession()
                .createQuery(hql)
                .setParameter("personaje", personaje)
                .setParameter("personajeTaberna", personajeTaberna)
                .uniqueResult();

        if (registro == null) {
            registro = new Taberna();
            registro.setPersonaje(personaje);
            registro.setPersonajeTaberna(personajeTaberna);
            registro.setCervezasInvitadas(1);
            registro.setUltimaInvitacion(LocalDate.now());
            sessionFactory.getCurrentSession().save(registro);
        } else {
            registro.setCervezasInvitadas(registro.getCervezasInvitadas() + 1);
            registro.setUltimaInvitacion(LocalDate.now());
            sessionFactory.getCurrentSession().update(registro);

        }
    }


    @Override
    public boolean puedeInvitar(Personaje personaje, PersonajeTaberna personajeTaberna) {
        String hql = "FROM Taberna WHERE personaje = :personaje AND personajeTaberna = :personajeTaberna";
        Taberna registro = (Taberna) sessionFactory.getCurrentSession()
                .createQuery(hql)
                .setParameter("personaje", personaje)
                .setParameter("personajeTaberna", personajeTaberna)
                .uniqueResult();

        if (registro == null) {
            return true; // nunca le invitó → puede invitar
        }

        return !registro.getUltimaInvitacion().isEqual(LocalDate.now());
    }
}