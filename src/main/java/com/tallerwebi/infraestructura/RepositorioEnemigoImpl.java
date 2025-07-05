package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.entidad.Enemigo;
import com.tallerwebi.dominio.entidad.Personaje;
import com.tallerwebi.dominio.interfaz.repositorio.RepositorioEnemigo;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository("repositorioEnemigo")
public class RepositorioEnemigoImpl implements RepositorioEnemigo {

    private final SessionFactory sessionFactory;

    public RepositorioEnemigoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Enemigo> obtenerEnemigos() {
        Session session = sessionFactory.getCurrentSession();
        return (List<Enemigo>) session.createCriteria(Enemigo.class).list();
    }

    @Override
    public Enemigo obtenerEnemigoPorId(Long idEnemigoMock) {
        Session session = sessionFactory.getCurrentSession();
        return (Enemigo) session.createCriteria(Enemigo.class)
                .add(Restrictions.eq("id", idEnemigoMock))
                .uniqueResult();
    }
}
