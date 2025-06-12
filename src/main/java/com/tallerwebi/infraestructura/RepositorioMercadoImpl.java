package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository("repositorioMercado")
public class RepositorioMercadoImpl implements RepositorioMercado {

    private final SessionFactory sessionFactory;

    public RepositorioMercadoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
    public Mercado obtenerMercadoConProductos() {
        Session session = sessionFactory.getCurrentSession();

        return (Mercado) session.createQuery("SELECT m FROM Mercado m LEFT JOIN FETCH m.productos")
                .setMaxResults(1)
                .uniqueResult();
    }

}

