package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
@Transactional
@Repository("repositorioInventario")
public class RepositorioInventarioImpl implements RepositorioInventario {

    private SessionFactory sessionFactory;

    @Autowired
    public RepositorioInventarioImpl(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

        @Override
        public List<Equipamiento> obtenerInventario(Long idPersonaje) {
            Session session = sessionFactory.getCurrentSession();
            return session.createCriteria(Equipamiento.class)
                    .add(Restrictions.eq("personaje.id", idPersonaje))
                    .list();
        }

    @Override
    public void modificarEquipamiento(Equipamiento equipamiento) {
        sessionFactory.getCurrentSession().save(equipamiento);
    }

    @Override
    public Equipamiento obtenerEquipamientoPorId(Long idEquipamiento) {
        Session session = sessionFactory.getCurrentSession();
        return (Equipamiento) session.createCriteria(Equipamiento.class)
                .add(Restrictions.eq("id", idEquipamiento))
                .uniqueResult();
    }
    @Override
    public void agregarEquipamiento(Equipamiento equipamiento) {
        sessionFactory.getCurrentSession().save(equipamiento);
    }


}
