package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Personaje;
import com.tallerwebi.dominio.PersonajeTaberna;
import com.tallerwebi.dominio.RepositorioTaberna;
import com.tallerwebi.dominio.Taberna;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
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
    public int getCantidadCervezasInvitadas(Long idPersonaje, PersonajeTaberna personajeTaberna) {

        Session session= sessionFactory.getCurrentSession();

        Taberna registro = (Taberna) session.createCriteria(Taberna.class)
                .add(Restrictions.eq("personaje.id", idPersonaje))
                .add(Restrictions.eq("personajeTaberna", personajeTaberna))
                .uniqueResult();

        return registro != null ? registro.getCervezasInvitadas() : 0;
    }


    @Override
    public void invitarCerveza(Long idPersonaje, PersonajeTaberna personajeTaberna) {
        Session session = sessionFactory.getCurrentSession();

        Taberna registro = (Taberna) session.createCriteria(Taberna.class)
                .add(Restrictions.eq("personaje.id", idPersonaje))
                .add(Restrictions.eq("personajeTaberna", personajeTaberna))
                .uniqueResult();

        if (registro == null) {
            Personaje personaje = session.get(Personaje.class, idPersonaje);

            registro = new Taberna();
            registro.setPersonaje(personaje);
            registro.setPersonajeTaberna(personajeTaberna);
            registro.setCervezasInvitadas(1);
            registro.setUltimaInvitacion(LocalDate.now());

            session.save(registro);
        } else {
            registro.setCervezasInvitadas(registro.getCervezasInvitadas() + 1);
            registro.setUltimaInvitacion(LocalDate.now());

            session.update(registro);
        }
    }


    @Override
    public boolean puedeInvitar(Long idPersonaje, PersonajeTaberna personajeTaberna) {
        Session session = sessionFactory.getCurrentSession();

        Taberna registro = (Taberna) session.createCriteria(Taberna.class)
                .add(Restrictions.eq("personaje.id", idPersonaje))
                .add(Restrictions.eq("personajeTaberna", personajeTaberna))
                .uniqueResult();

        if (registro == null) {
            return true;
        }

        return !registro.getUltimaInvitacion().isEqual(LocalDate.now());
    }

    @Override
    public Personaje buscarPorId(Long idPersonaje) {

        Session session = sessionFactory.getCurrentSession();
        return session.get(Personaje.class, idPersonaje);

    }
}