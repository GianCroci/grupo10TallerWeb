package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.entidad.Enemigo;
import com.tallerwebi.dominio.entidad.Equipamiento;
import com.tallerwebi.dominio.entidad.Producto;
import com.tallerwebi.dominio.interfaz.repositorio.RepositorioProducto;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository("repositorioProducto")
public class RepositorioProductoImpl implements RepositorioProducto {

    private final SessionFactory sessionFactory;

    @Autowired
    public RepositorioProductoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Producto buscarProductoPorId(Long idProducto) {
        Session session = sessionFactory.getCurrentSession();
        return (Producto) session.createCriteria(Producto.class)
                .add(Restrictions.eq("id", idProducto))
                .uniqueResult();
    }

    @Override
    public List<Producto> obtenerTodosLosProductos() {
        Session session = sessionFactory.getCurrentSession();
        return (List<Producto>) session.createCriteria(Producto.class).list();
    }
}
