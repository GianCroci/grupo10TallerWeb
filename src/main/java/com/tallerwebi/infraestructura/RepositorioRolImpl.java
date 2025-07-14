package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.interfaz.repositorio.RepositorioRol;
import com.tallerwebi.dominio.entidad.Rol;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

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

    @Override
    public void guardarRol(Rol rol) {
        sessionFactory.getCurrentSession().save(rol);
    }

    @Override
    public List<Rol> obtenerTodos() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("FROM Rol", Rol.class).list();
    }

    @Override
    public void eliminar(Rol rol) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(rol);
    }


}
