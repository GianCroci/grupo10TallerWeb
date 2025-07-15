package com.tallerwebi.integracion;

import com.tallerwebi.config.WebSocketConfig;
import com.tallerwebi.dominio.Estadisticas;
import com.tallerwebi.dominio.entidad.Personaje;
import com.tallerwebi.dominio.entidad.Usuario;
import com.tallerwebi.dominio.interfaz.repositorio.RepositorioPersonaje;
import com.tallerwebi.dominio.interfaz.repositorio.RepositorioTaberna;
import com.tallerwebi.dominio.interfaz.repositorio.RepositorioUsuario;
import com.tallerwebi.integracion.config.HibernateTestConfig;
import com.tallerwebi.integracion.config.SpringWebTestConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;



@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {SpringWebTestConfig.class, HibernateTestConfig.class, WebSocketConfig.class})
public class ControladorTabernaTest {

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private RepositorioUsuario repoUsuario;

    @Autowired
    private RepositorioPersonaje repoPersonaje;

    @Autowired
    private RepositorioTaberna repoTaberna;


    private MockMvc mockMvc;
    private Usuario usuario;
    private Personaje personaje;

    @BeforeEach
    public void init() {

        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();

        for (Usuario u : repoUsuario.obtenerTodos()) {
            repoUsuario.eliminar(u);
        }

        usuario = new Usuario();
        usuario.setEmail("tomas@unlam.com");
        usuario.setPassword("1234");
        repoUsuario.guardar(usuario);


        personaje = new Personaje();
        personaje.setNombre("TomasTest");
        personaje.setGenero("Masculino");
        personaje.setImagen("imagen.png");
        personaje.setEstadisticas(new Estadisticas());
        personaje.setOro(0);
        repoPersonaje.guardar(personaje);
    }

}
