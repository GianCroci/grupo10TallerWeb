package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Estadisticas;
import com.tallerwebi.dominio.entidad.Enemigo;
import com.tallerwebi.dominio.entidad.Slime;
import com.tallerwebi.dominio.entidad.Trol;
import com.tallerwebi.dominio.interfaz.repositorio.RepositorioEnemigo;
import com.tallerwebi.dominio.interfaz.repositorio.RepositorioPersonaje;
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
import java.util.Collection;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.hamcrest.Matchers.is;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {HibernateInfraestructuraTestConfig.class})
@Transactional
public class RepositorioEnemigoTest {

    @Autowired
    private SessionFactory sessionFactory;
    private RepositorioEnemigo repositorioEnemigo;
    private Session session;

    @BeforeEach
    public void init() {
        repositorioEnemigo = new RepositorioEnemigoImpl(sessionFactory);
        session = sessionFactory.getCurrentSession();
    }

    @Test
    public void queElMetodoObtenerEnemigosDevuelvaUnaColeccionConTodosLosEnemigosGuardadosEnLaBaseDeDatos() {
        Enemigo slime = new Slime();
        slime.setId(1L);
        session.save(slime);
        Enemigo trol = new Trol();
        trol.setId(2L);
        session.save(trol);

        List<Enemigo> enemigosObtenidos = repositorioEnemigo.obtenerEnemigos();

        assertThat(enemigosObtenidos.size(), is(2));
    }

    @Test
    public void queElMetodoObtenerEnemigoPorIdMeDevuelvaElEnemigoCorrectoGuardadoEnLaBaseDeDatos() {
        Enemigo slime = new Slime();
        slime.setId(1L);
        slime.setNombre("slime");
        session.save(slime);

        Enemigo trol = new Trol();
        trol.setId(2L);
        trol.setNombre("trol");
        session.save(trol);

        Enemigo enemigoObtenido = repositorioEnemigo.obtenerEnemigoPorId(2L);

        String nombreEnemigoEsperado = "trol";
        String nombreEnemigoObtenido = enemigoObtenido.getNombre();


        assertThat(nombreEnemigoObtenido, equalToIgnoringCase(nombreEnemigoEsperado));
    }
}
