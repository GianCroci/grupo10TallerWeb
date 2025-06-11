package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Personaje;
import com.tallerwebi.dominio.RepositorioPersonaje;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Random;

@Transactional
@Repository("repositorioPersonaje")
public class RepositorioPersonajeImpl implements RepositorioPersonaje {

    private SessionFactory sessionFactory;
    private HttpServletRequest request;

    @Autowired
    public RepositorioPersonajeImpl(SessionFactory sessionFactory, HttpServletRequest request){
        this.sessionFactory = sessionFactory;
        this.request = request;
    }

    @Override
    public Personaje buscarPersonaje(Long idPersonaje) {
        Session session = sessionFactory.getCurrentSession();
        return (Personaje) session.createCriteria(Personaje.class)
                .add(Restrictions.eq("id", idPersonaje))
                .uniqueResult();
    }

    @Override
    public void guardar(Personaje personaje) {
    sessionFactory.getCurrentSession().save(personaje);
    }

    @Override
    public void modificar(Personaje personaje) {
        sessionFactory.getCurrentSession().update(personaje);
    }

    @Override
    public Personaje buscarRival() {
        Session session = sessionFactory.getCurrentSession();
        Long idPersonaje = (Long) request.getSession().getAttribute("idPersonaje");

        // Trae todos los personajes
        List<Personaje> personajes = session.createCriteria(Personaje.class).list();

        for (Personaje personaje : personajes) {
            if (!personaje.getId().equals(idPersonaje)) {
                return personaje;
            }
        }

        // Selecciona uno aleatorio
        int indexAleatorio = new Random().nextInt(personajes.size());
        return personajes.get(indexAleatorio);
    }

    @Override
    public Integer buscarOroPersonaje(Long idPersonaje) {
        Session session = sessionFactory.getCurrentSession();
        return (Integer) session.createCriteria(Personaje.class)
                .add(Restrictions.eq("id", idPersonaje))
                .setProjection(Projections.property("oro"))
                .uniqueResult();
    }

}
