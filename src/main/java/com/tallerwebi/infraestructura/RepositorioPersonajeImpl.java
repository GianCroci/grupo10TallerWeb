package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.entidad.Personaje;
import com.tallerwebi.dominio.interfaz.repositorio.RepositorioPersonaje;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Transactional
@Repository("repositorioPersonaje")
public class RepositorioPersonajeImpl implements RepositorioPersonaje {

    private SessionFactory sessionFactory;

    @Autowired
    public RepositorioPersonajeImpl(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
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
    public Personaje buscarRival(Long idPersonaje) {
        Session session = sessionFactory.getCurrentSession();
        List<Personaje> personajes = session.createCriteria(Personaje.class).list();

        // Filtrar los que no sean el personaje actual
        List<Personaje> rivales = personajes.stream()
                .filter(p -> !p.getId().equals(idPersonaje))
                .collect(Collectors.toList());

        if (rivales.isEmpty()) {
            return null;
        }

        int indexAleatorio = new Random().nextInt(rivales.size());
        return rivales.get(indexAleatorio);
    }


    @Override
    public Integer buscarOroPersonaje(Long idPersonaje) {
        Session session = sessionFactory.getCurrentSession();
        return (Integer) session.createCriteria(Personaje.class)
                .add(Restrictions.eq("id", idPersonaje))
                .setProjection(Projections.property("oro"))
                .uniqueResult();
    }

    @Override
    public Personaje buscarPersonajePorCodigoAmigo(String codigoAmigo) {
        Session session = sessionFactory.getCurrentSession();
        return (Personaje) session.createCriteria(Personaje.class)
                .add(Restrictions.eq("codigoAmigo", codigoAmigo))
                .uniqueResult();
    }

    @Override
    public List<Personaje> obtenerAmigos(Long idPersonaje) {
        Session session = sessionFactory.getCurrentSession();
        Personaje obtenido = (Personaje) session.createCriteria(Personaje.class)
                .add(Restrictions.eq("id", idPersonaje))
                .uniqueResult();
        return obtenido.getAmigos();
    }

    @Override
    public String obtenerCodigoAmigoPropio(Long idPersonaje) {
        Session session = sessionFactory.getCurrentSession();
        return (String) session.createCriteria(Personaje.class)
                .add(Restrictions.eq("id", idPersonaje))
                .setProjection(Projections.property("codigoAmigo"))
                .uniqueResult();
    }

    @Override
    public void eliminar(Personaje personaje) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(personaje);
    }

}
