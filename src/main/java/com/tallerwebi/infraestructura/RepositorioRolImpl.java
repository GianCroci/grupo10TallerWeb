package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.RepositorioRol;
import com.tallerwebi.dominio.Rol;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Transactional
@Repository("repositorioRol")
public class RepositorioRolImpl implements RepositorioRol {

    private SessionFactory sessionFactory;

    @Autowired
    public RepositorioRolImpl(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Rol obtenerRolPorId(Long idRol) {
        Session session = sessionFactory.getCurrentSession();
        return (Rol) session.createCriteria(Rol.class)
                .add(Restrictions.eq("id", idRol))
                .uniqueResult();
    }
}
