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
@Repository("repositorioMercado")
public class RepositorioMercadoImpl implements RepositorioMercado {

    private final SessionFactory sessionFactory;

    public RepositorioMercadoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Producto> obtenerProductos() {
        Session session = sessionFactory.getCurrentSession();
        return session.createCriteria(Producto.class).list();
    }

    @Override
    public Producto buscarPorId(Long id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Producto.class, id);
    }

    @Override
    public void guardarProducto(Producto producto) {
        sessionFactory.getCurrentSession().save(producto);
    }


    @Override
    public void inicializarProductos() {
        Session session = sessionFactory.getCurrentSession();

        if (!session.createQuery("FROM Producto").list().isEmpty()) {
            return;
        }

        Mercado mercado = (Mercado) session.createQuery("FROM Mercado").uniqueResult();
        if (mercado == null) {
            mercado = new Mercado();
            session.save(mercado);
        }

        mercado.getProductos().add(new Tunica("Tunica azul", 100.0));
        mercado.getProductos().add(new Abrigo("Abrigo gris", 200.0));
        mercado.getProductos().add(new Capucha("Capucha de Sombras", 120.0));
        mercado.getProductos().add(new ZapatoUno("Zapatos grises", 250.0));
        mercado.getProductos().add(new ZapatoDos("Zapatos reforzados", 100.0));
        mercado.getProductos().add(new Cinturon("Cinturon oro", 250.0));
        session.saveOrUpdate(mercado);
    }
    @Override
    public Mercado obtenerMercadoConProductos() {
        Session session = sessionFactory.getCurrentSession();
        return (Mercado) session.createQuery("SELECT m FROM Mercado m LEFT JOIN FETCH m.productos")
                .uniqueResult();
    }

}

