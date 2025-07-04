package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.entidad.Mensaje;
import com.tallerwebi.dominio.interfaz.repositorio.RepositorioMensaje;
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
import static org.hamcrest.Matchers.is;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {HibernateInfraestructuraTestConfig.class})
@Transactional
public class RepositorioMensajeTest {

    @Autowired
    private SessionFactory sessionFactory;
    private RepositorioMensaje repositorioMensaje;
    private Session session;

    @BeforeEach
    public void init() {
        repositorioMensaje = new RepositorioMensajeImpl(sessionFactory);
        session = sessionFactory.getCurrentSession();
    }

    @Test
    public void queSePuedaGuardarUnMensaje() {
        Mensaje mensaje = new Mensaje("usuario1", "usuario2", "Hola, ¿cómo estás?");
        session.save(mensaje);
        repositorioMensaje.guardar(mensaje);

        Long idMensajeGuardado = mensaje.getId();

        Mensaje mensajeObtenido = (Mensaje) session.createCriteria(Mensaje.class)
                .add(Restrictions.eq("id", idMensajeGuardado))
                .uniqueResult();

        assertThat(mensajeObtenido, is(mensaje));
    }

    @Test
    public void queSePuedaObtenerHistorialDeMensajes() {
        Mensaje mensaje1 = new Mensaje("usuario1", "usuario2", "Hola, usuario2");
        Mensaje mensaje2 = new Mensaje("usuario2", "usuario1", "Hola, usuario1");
        session.save(mensaje1);
        session.save(mensaje2);
        repositorioMensaje.guardar(mensaje1);
        repositorioMensaje.guardar(mensaje2);

        List<Mensaje> historial = repositorioMensaje.obtenerHistorial("usuario1", "usuario2");

        assertThat(historial.size(), is(2));
        assertThat(historial.get(0).getRemitente(), is("usuario1"));
        assertThat(historial.get(1).getRemitente(), is("usuario2"));
    }
}
