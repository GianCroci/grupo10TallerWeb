package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Personaje;
import com.tallerwebi.dominio.RepositorioPersonaje;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Random;

@Transactional
@Repository("repositorioPersonaje")
public class RepositorioPersonajeImpl implements RepositorioPersonaje {

    private SessionFactory sessionFactory;

    @Autowired
    public RepositorioPersonajeImpl(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Personaje buscarPersonaje(Long id) {
        Session session = sessionFactory.getCurrentSession();
        return (Personaje) session.createCriteria(Personaje.class)
                .add(Restrictions.eq("id", id))
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

        // Trae todos los personajes
        List<Personaje> personajes = session.createCriteria(Personaje.class).list();

        if (personajes.isEmpty()) {
            return null;
        }

        // Selecciona uno aleatorio
        int indexAleatorio = new Random().nextInt(personajes.size());
        return personajes.get(indexAleatorio);
    }

}
