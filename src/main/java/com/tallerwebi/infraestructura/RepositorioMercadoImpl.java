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
    public List<Equipamiento> obtenerProductos() {
        Session session = sessionFactory.getCurrentSession();
        return session.createCriteria(Equipamiento.class).list();
    }

    @Override
    public Equipamiento buscarPorId(Long id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Equipamiento.class, id);
    }


    @Override
    public void inicializarProductos() {
        Session session = sessionFactory.getCurrentSession();

        if (!session.createQuery("FROM Equipamiento").list().isEmpty()) {
            return;
        }

        Mercado mercado = (Mercado) session.createQuery("FROM Mercado").uniqueResult();
        if (mercado == null) {
            mercado = new Mercado();
            session.save(mercado);
        }

        Tunica tunica = new Tunica("Tunica azul", 100);
        tunica.setMercado(mercado);

        Abrigo abrigo = new Abrigo("Abrigo gris", 200);
        abrigo.setMercado(mercado);

        Capucha capucha = new Capucha("Capucha de Sombras", 120);
        capucha.setMercado(mercado);

        ZapatoUno zapatoUno = new ZapatoUno("Zapatos grises", 250);
        zapatoUno.setMercado(mercado);

        ZapatoDos zapatoDos = new ZapatoDos("Zapatos reforzados", 100);
        zapatoDos.setMercado(mercado);

        Cinturon cinturon = new Cinturon("Cinturon oro", 250);
        cinturon.setMercado(mercado);

        mercado.getProductos().add(tunica);
        mercado.getProductos().add(abrigo);
        mercado.getProductos().add(capucha);
        mercado.getProductos().add(zapatoUno);
        mercado.getProductos().add(zapatoDos);
        mercado.getProductos().add(cinturon);

        session.saveOrUpdate(mercado);
        session.save(tunica);
        session.save(abrigo);
        session.save(capucha);
        session.save(zapatoUno);
        session.save(zapatoDos);
        session.save(cinturon);

    }

    @Override
    public Mercado obtenerMercadoConProductos() {
        Session session = sessionFactory.getCurrentSession();

        return (Mercado) session.createQuery("SELECT m FROM Mercado m LEFT JOIN FETCH m.productos")
                .setMaxResults(1)
                .uniqueResult();
    }

}

