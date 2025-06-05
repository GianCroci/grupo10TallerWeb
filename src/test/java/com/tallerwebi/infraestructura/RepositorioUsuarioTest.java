package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Personaje;
import com.tallerwebi.dominio.RepositorioPersonaje;
import com.tallerwebi.dominio.RepositorioUsuario;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.infraestructura.config.HibernateInfraestructuraTestConfig;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.Assert;

import javax.transaction.Transactional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {HibernateInfraestructuraTestConfig.class})
@Transactional
public class RepositorioUsuarioTest {

    @Autowired
    private SessionFactory sessionFactory;
    private RepositorioUsuario repositorioUsuario;
    private Session session;
    private Usuario usuario;

    @BeforeEach
    public void init(){
        repositorioUsuario = new RepositorioUsuarioImpl(sessionFactory);
        session = sessionFactory.getCurrentSession();
    }

    @Test
    public void queSePuedaGuardarUnUsuario() {
        usuario = new Usuario();
        usuario.setEmail("test@test.com");
        usuario.setPassword("test");

        repositorioUsuario.guardar(usuario);

        Usuario usuarioObtenido =  (Usuario) sessionFactory.getCurrentSession().createCriteria(Usuario.class)
                                            .add(Restrictions.eq("email", "test@test.com"))
                                            .uniqueResult();

        assertThat(usuarioObtenido, is(usuario));
    }

    @Test
    public void queSePuedaActualizarUnUsuario() {
        usuario = new Usuario();
        usuario.setEmail("test@test.com");
        usuario.setPassword("test");

        session.save(usuario);

        usuario.setPassword("cambio");

        repositorioUsuario.modificar(usuario);

        Usuario usuarioObtenido =  (Usuario) sessionFactory.getCurrentSession().createCriteria(Usuario.class)
                .add(Restrictions.eq("email", "test@test.com"))
                .uniqueResult();

        String passEsperada = "cambio";
        String passObtenido = usuarioObtenido.getPassword();

        assertThat(passObtenido, equalToIgnoringCase(passEsperada));
    }

    @Test
    public void queSePuedaBuscarUnUsuarioPorEmail() {
        usuario = new Usuario();
        usuario.setEmail("test@test.com");
        usuario.setPassword("test");

        session.save(usuario);

        Usuario usuarioObtenido =  repositorioUsuario.buscar(usuario.getEmail());

        assertThat(usuarioObtenido, is(usuario));
    }

    @Test
    public void queSePuedaBuscarUnUsuarioPorEmailYPassword() {
        usuario = new Usuario();
        usuario.setEmail("test@test.com");
        usuario.setPassword("test");

        session.save(usuario);

        Usuario usuarioObtenido =  repositorioUsuario.buscarUsuario(usuario.getEmail(), usuario.getPassword());

        assertThat(usuarioObtenido, is(usuario));
    }

}
