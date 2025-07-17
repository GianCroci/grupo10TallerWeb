package com.tallerwebi.integracion;

import com.tallerwebi.config.WebSocketConfig;
import com.tallerwebi.dominio.Estadisticas;
import com.tallerwebi.dominio.ServicioUsuarioImpl;
import com.tallerwebi.dominio.entidad.Guerrero;
import com.tallerwebi.dominio.entidad.Personaje;
import com.tallerwebi.dominio.entidad.Rol;
import com.tallerwebi.dominio.entidad.Usuario;
import com.tallerwebi.dominio.interfaz.repositorio.RepositorioPersonaje;
import com.tallerwebi.dominio.interfaz.repositorio.RepositorioRol;
import com.tallerwebi.dominio.interfaz.repositorio.RepositorioUsuario;
import com.tallerwebi.dominio.interfaz.servicio.ServicioUsuario;
import com.tallerwebi.integracion.config.HibernateTestConfig;
import com.tallerwebi.integracion.config.SpringWebTestConfig;
import com.tallerwebi.presentacion.PersonajeDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import javax.transaction.Transactional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {SpringWebTestConfig.class, HibernateTestConfig.class, WebSocketConfig.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ControladorBatallaTest {

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private RepositorioUsuario repoUsuario;

    @Autowired
    private RepositorioPersonaje repoPersonaje;


    @Autowired
    private RepositorioRol repositorioRol;

    private MockMvc mockMvc;
    private Usuario usuarioReal;
    private Usuario usuario2;
    private Rol rolGuerrero;
    private Personaje personaje;
    private Personaje personaje2;
    private ServicioUsuario servicioUsuario;
    private Estadisticas estadisticas;

    @BeforeEach
    public void init() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();

        for (Usuario usuario : repoUsuario.obtenerTodos()) {
            if (usuario.getPersonaje() != null) {
                repoPersonaje.eliminar(usuario.getPersonaje());
            }
            repoUsuario.eliminar(usuario);
        }


        // limpiar roles para evitar duplicados
        for (Rol rol : repositorioRol.obtenerTodos()) {
            repositorioRol.eliminar(rol);
        }

        // crear usuario
        usuarioReal = new Usuario();
        usuarioReal.setEmail("gian@unlam.com");
        usuarioReal.setPassword("1234");
        repoUsuario.guardar(usuarioReal);

        usuario2 = new Usuario();
        usuario2.setEmail("gian2@unlam.com");
        usuario2.setPassword("1234");
        repoUsuario.guardar(usuario2);


        // crear rol con nombre único
        Guerrero guerrero = new Guerrero();
        guerrero.setTipo("Guerrero_" + System.nanoTime());
        repositorioRol.guardarRol(guerrero);
        rolGuerrero = guerrero;

        //crear estadisticas
        estadisticas = new Estadisticas();
        estadisticas.setFuerza(10);
        estadisticas.setInteligencia(10);
        estadisticas.setArmadura(10);
        estadisticas.setAgilidad(10);


        // crear personaje
        personaje = new Personaje();
        personaje.setNombre("Gian");
        personaje.setImagen("luchador.png");
        personaje.setGenero("Masculino");
        personaje.setRol(rolGuerrero);
        personaje.setEstadisticas(estadisticas);






        servicioUsuario = new ServicioUsuarioImpl(repoUsuario);
        servicioUsuario.setPersonaje(personaje, usuarioReal);


    }

    @Test
    public  void debeRetornarAHomeSIQuieroIngresarABatallaTNoHayRival() throws Exception {

        MvcResult result = this.mockMvc.perform(get("/batalla")
                        .sessionAttr("idPersonaje", usuarioReal.getPersonaje().getId()))
                .andExpect(status().is3xxRedirection())
                .andReturn();

        ModelAndView modelAndView = result.getModelAndView();
        assertNotNull(modelAndView);
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("redirect:/home"));
    }

    @Test
    public  void debeIrALaPaginaBatallaCuandoSeNavegaABatallaYEncontrarUnRival() throws Exception {
        personaje2 = new Personaje();
        personaje2.setNombre("Gian2");
        personaje2.setImagen("luchador.png");
        personaje2.setGenero("Masculino");
        personaje2.setRol(rolGuerrero);
        personaje2.setEstadisticas(estadisticas);

        servicioUsuario.setPersonaje(personaje2, usuario2);

        MvcResult result = this.mockMvc.perform(get("/batalla")
                        .sessionAttr("idPersonaje", usuarioReal.getPersonaje().getId()))
                .andExpect(status().isOk())
                .andReturn();

        ModelAndView modelAndView = result.getModelAndView();
        assert modelAndView != null;
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("batalla"));
        assertTrue(modelAndView.getModel().containsKey("personaje"));
        assertTrue(modelAndView.getModel().containsKey("rival"));
        assertThat(modelAndView.getModel().get("personaje"), instanceOf(Personaje.class));
        assertThat(modelAndView.getModel().get("rival"), instanceOf(Personaje.class));
    }

    @Test
    public void cuandoAprietoEnIrALaBatallaDebeIrALaSalaDeBatallaWS() throws Exception {
        personaje2 = new Personaje();
        personaje2.setNombre("Gian2");
        personaje2.setImagen("luchador.png");
        personaje2.setGenero("Masculino");
        personaje2.setRol(rolGuerrero);
        personaje2.setEstadisticas(estadisticas);

        servicioUsuario.setPersonaje(personaje2, usuario2);
        Long idRival = usuario2.getPersonaje().getId();

        MvcResult result = this.mockMvc.perform(get("/batalla-websocket/" + idRival)
                .sessionAttr("idPersonaje", usuarioReal.getPersonaje().getId()))
                .andExpect(status().isOk())
                .andReturn();

        ModelAndView modelAndView = result.getModelAndView();
        assert modelAndView != null;
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("batalla-ws"));
        assertTrue(modelAndView.getModel().containsKey("personaje"));
        assertTrue(modelAndView.getModel().containsKey("rival"));
        assertTrue(modelAndView.getModel().containsKey("salaId"));
        assertThat(modelAndView.getModel().get("personaje"), instanceOf(Personaje.class));
        assertThat(modelAndView.getModel().get("rival"), instanceOf(Personaje.class));
        assertThat(modelAndView.getModel().get("salaId"), notNullValue());
    }
}
