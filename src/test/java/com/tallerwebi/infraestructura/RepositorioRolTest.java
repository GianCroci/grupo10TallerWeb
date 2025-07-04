package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.entidad.Bandido;
import com.tallerwebi.dominio.entidad.Guerrero;
import com.tallerwebi.dominio.entidad.Mago;
import com.tallerwebi.dominio.entidad.Rol;
import com.tallerwebi.dominio.interfaz.repositorio.RepositorioRol;
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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalToIgnoringCase;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {HibernateInfraestructuraTestConfig.class})
@Transactional
public class RepositorioRolTest {

    @Autowired
    private SessionFactory sessionFactory;
    private RepositorioRol repositorioRol;
    private Session session;
    private Rol rol;

    @BeforeEach
    public void init() {
        repositorioRol = new RepositorioRolImpl(sessionFactory);
        session = sessionFactory.getCurrentSession();
    }

    @Test
    public void queSePuedaObtenerElRolGuerreroPorSuId() {
        rol = new Guerrero();

        session.save(rol);

        Long idRolGuerrero = 1l;

        Rol rolObtenido = repositorioRol.obtenerRolPorId(idRolGuerrero);

        String nombreRolEsperado = "Guerrero";
        String nombreRolObtenido = rolObtenido.getTipo();

        assertThat(nombreRolEsperado, equalToIgnoringCase(nombreRolObtenido));
    }

    @Test
    public void queSePuedaObtenerElRolMagoPorSuId() {
        rol = new Mago();

        session.save(rol);

        Long idRolMago = 2l;

        Rol rolObtenido = repositorioRol.obtenerRolPorId(idRolMago);

        String nombreRolEsperado = "Mago";
        String nombreRolObtenido = rolObtenido.getTipo();

        assertThat(nombreRolEsperado, equalToIgnoringCase(nombreRolObtenido));
    }

    @Test
    public void queSePuedaObtenerElRolBandidoPorSuId() {
        rol = new Bandido();

        session.save(rol);

        Long idRolBandido = 3l;

        Rol rolObtenido = repositorioRol.obtenerRolPorId(idRolBandido);

        String nombreRolEsperado = "Bandido";
        String nombreRolObtenido = rolObtenido.getTipo();

        assertThat(nombreRolEsperado, equalToIgnoringCase(nombreRolObtenido));
    }
}