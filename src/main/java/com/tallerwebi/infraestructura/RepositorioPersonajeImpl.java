package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Personaje;
import com.tallerwebi.dominio.RepositorioPersonaje;
import com.tallerwebi.dominio.Usuario;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

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
    public Boolean guardar(Long id, Personaje personaje) {
    return false;
    }

    @Override
    public void modificar(Personaje personaje) {

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
