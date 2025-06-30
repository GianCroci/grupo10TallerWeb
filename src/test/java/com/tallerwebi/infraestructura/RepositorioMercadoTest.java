package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.*;
import com.tallerwebi.infraestructura.config.HibernateInfraestructuraTestConfig;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {HibernateInfraestructuraTestConfig.class})
@Transactional
public class RepositorioMercadoTest {

    @Autowired
    private SessionFactory sessionFactory;
    private RepositorioMercado repositorioMercado;
    private Session session;

    @BeforeEach
    public void init() {
        repositorioMercado = new RepositorioMercadoImpl(sessionFactory);
        session = sessionFactory.getCurrentSession();
    }

    @Test
    public void queObtengaMercadoConProductos() {
        Mercado mercado = new Mercado();

        Pechera item1 = new Pechera();
        item1.setNombre("Pechera de Hierro");
        item1.setCostoCompra(100);
        item1.setStats(new Estadisticas());
        item1.setImagen("pechera.png");

        Pechera item2 = new Pechera();
        item2.setNombre("Pechera de Acero");
        item2.setCostoCompra(200);
        item2.setStats(new Estadisticas());
        item2.setImagen("pechera2.png");

        mercado.setProductos(List.of(item1, item2));

        session.save(item1);
        session.save(item2);
        session.save(mercado);
        session.flush();

        Mercado mercadoObtenido = repositorioMercado.obtenerMercadoConProductos();

        assertThat(mercadoObtenido, notNullValue());
        assertThat(mercadoObtenido.getProductos(), hasSize(2));
        assertThat(mercadoObtenido.getProductos().get(0).getNombre(), is("Pechera de Hierro"));
    }
}
