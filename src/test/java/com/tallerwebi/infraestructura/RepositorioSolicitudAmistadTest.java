package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.entidad.Personaje;
import com.tallerwebi.dominio.entidad.SolicitudAmistad;
import com.tallerwebi.dominio.interfaz.repositorio.RepositorioSolicitudAmistad;
import com.tallerwebi.infraestructura.config.HibernateInfraestructuraTestConfig;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
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
public class RepositorioSolicitudAmistadTest {

    @Autowired
    private SessionFactory sessionFactory;
    private RepositorioSolicitudAmistad repositorioSolicitudAmistad;
    private Session session;
    private SolicitudAmistad solicitudAmistad;

    @BeforeEach
    public void init() {
        repositorioSolicitudAmistad = new RepositorioSolicitudAmistadImpl(sessionFactory);
        session = sessionFactory.getCurrentSession();
    }

    @Test
    public void queSePuedaGuardarUnaSolicitud() {
        solicitudAmistad = new SolicitudAmistad();

        repositorioSolicitudAmistad.guardarSolicitud(solicitudAmistad);

        Long idSolicitud = solicitudAmistad.getId();

        SolicitudAmistad solicitudGuardada = (SolicitudAmistad) session.createCriteria(SolicitudAmistad.class)
                                                    .add(Restrictions.eq("id", idSolicitud))
                                                    .uniqueResult();

        assertThat(solicitudAmistad, equalTo(solicitudGuardada));
    }

    @Test
    public void queSePuedaModificarUnaSolicitud() {
        solicitudAmistad = new SolicitudAmistad();

        solicitudAmistad.setEstado(EstadoSolicitud.PENDIENTE);

        session.save(solicitudAmistad);

        Long idSolicitud = solicitudAmistad.getId();

        assertThat(solicitudAmistad.getEstado(), is(EstadoSolicitud.PENDIENTE));

        solicitudAmistad.setEstado(EstadoSolicitud.ACEPTADA);

        repositorioSolicitudAmistad.modificarSolicitud(solicitudAmistad);

        SolicitudAmistad solicitudGuardada = (SolicitudAmistad) session.createCriteria(SolicitudAmistad.class)
                                                    .add(Restrictions.eq("id", idSolicitud))
                                                    .uniqueResult();

        assertThat(solicitudGuardada.getEstado(), is(EstadoSolicitud.ACEPTADA));
    }

    @Test
    public void queSePuedaBuscarUnaSolicitudDeAmistadPorId() {
        solicitudAmistad = new SolicitudAmistad();

        session.save(solicitudAmistad);

        Long idSolicitud = solicitudAmistad.getId();

        repositorioSolicitudAmistad.modificarSolicitud(solicitudAmistad);

        SolicitudAmistad solicitudGuardada = repositorioSolicitudAmistad.buscarPorId(idSolicitud);

        assertThat(solicitudAmistad, equalTo(solicitudGuardada));
    }

    @Test
    public void queSePuedaObtenerSolicitudesDeAmistadRecibidasPendientes() {
        solicitudAmistad = new SolicitudAmistad();
        Personaje personaje = new Personaje();
        Personaje personaje2 = new Personaje();

        solicitudAmistad.setDestinatario(personaje);
        solicitudAmistad.setRemitente(personaje2);
        solicitudAmistad.setEstado(EstadoSolicitud.PENDIENTE);
        session.save(personaje);
        session.save(personaje2);
        session.save(solicitudAmistad);

        List<SolicitudAmistad> solicitudesBuscadas = repositorioSolicitudAmistad.obtenerSolicitudesDeAmistadRecibidasPendientes(personaje.getId());

        assertThat(solicitudesBuscadas, hasSize(1));
    }

    @Test
    public void queSePuedaObtenerSolicitudesDeAmistadEnviadasPendientes() {
        solicitudAmistad = new SolicitudAmistad();
        Personaje personaje = new Personaje();
        Personaje personaje2 = new Personaje();

        solicitudAmistad.setDestinatario(personaje);
        solicitudAmistad.setRemitente(personaje2);
        solicitudAmistad.setEstado(EstadoSolicitud.PENDIENTE);
        session.save(personaje);
        session.save(personaje2);
        session.save(solicitudAmistad);

        List<SolicitudAmistad> solicitudesBuscadas = repositorioSolicitudAmistad.obtenerSolicitudesDeAmistadEnviadasPendientes(personaje2.getId());

        assertThat(solicitudesBuscadas, hasSize(1));
    }
}
