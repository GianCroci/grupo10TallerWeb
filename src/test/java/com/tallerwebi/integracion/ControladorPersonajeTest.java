package com.tallerwebi.integracion;

import com.tallerwebi.config.WebSocketConfig;
import com.tallerwebi.dominio.entidad.Guerrero;
import com.tallerwebi.dominio.entidad.Personaje;
import com.tallerwebi.dominio.entidad.Rol;
import com.tallerwebi.dominio.entidad.Usuario;
import com.tallerwebi.dominio.interfaz.repositorio.RepositorioPersonaje;
import com.tallerwebi.dominio.interfaz.repositorio.RepositorioRol;
import com.tallerwebi.dominio.interfaz.repositorio.RepositorioUsuario;
import com.tallerwebi.integracion.config.HibernateTestConfig;
import com.tallerwebi.integracion.config.SpringWebTestConfig;
import com.tallerwebi.presentacion.PersonajeDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {SpringWebTestConfig.class, HibernateTestConfig.class, WebSocketConfig.class})
public class ControladorPersonajeTest {

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


    @BeforeEach
    public void init(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();

        //creo un usuario real en la bdd
        usuarioReal = new Usuario();
        usuarioReal.setEmail("gian@unlam.com");
        usuarioReal.setPassword("1234");
        repoUsuario.guardar(usuarioReal);

        // Crear un rol en la bdd
        Guerrero guerrero = new Guerrero();
        guerrero.setId(1L);
        guerrero.setTipo("Guerrero");
        repositorioRol.guardarRol(guerrero);

    }

    @Test
    public void debeIrALaPaginaCreacionPersonajeCuandoSeNavegaACreacionPersonaje() throws Exception {

        MvcResult result = this.mockMvc.perform(get("/creacion-personaje"))
                .andExpect(status().isOk())
                .andReturn();

        ModelAndView modelAndView = result.getModelAndView();
        assert modelAndView != null;
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("creacion-personaje"));
        assertTrue(modelAndView.getModel().containsKey("datosPersonaje"));
        assertThat(modelAndView.getModel().get("datosPersonaje"), instanceOf(PersonajeDTO.class));
    }

    @Test
    public void debeGuardarUnPersonajeSiElUsuarioEstaLogueado() throws Exception {

        // creo el DTO que recibe el controlador
        PersonajeDTO personajeDTO = new PersonajeDTO();
        personajeDTO.setNombre("Gian");
        personajeDTO.setImagen("luchador.png");
        personajeDTO.setGenero("Masculino");
        personajeDTO.setIdRol(1L);

        // ejecuto el post de la vista
        MvcResult result = this.mockMvc.perform(post("/guardar-personaje")
                        .flashAttr("datosPersonaje", personajeDTO)
                        .sessionAttr("usuarioLogueado", usuarioReal))
                .andExpect(status().is3xxRedirection())
                .andReturn();


        // verifico que me redirija a home
        ModelAndView modelAndView = result.getModelAndView();
        assertNotNull(modelAndView);
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("redirect:/home"));

        // verifico que le asigno el pj al usuario
        Usuario usuarioActualizado = repoUsuario.buscar("gian@unlam.com");
        assertNotNull(usuarioActualizado);
        assertEquals("Gian", usuarioActualizado.getPersonaje().getNombre());

        // verifico que el personaje se creo correctamente
        Personaje personajeAsignado = usuarioActualizado.getPersonaje();
        assertEquals("Gian", personajeAsignado.getNombre());
        assertEquals("luchador.png", personajeAsignado.getImagen());
        assertEquals("Masculino", personajeAsignado.getGenero());
        assertEquals(repositorioRol.obtenerRolPorId(1L).getId(), personajeAsignado.getRol().getId());
        assertEquals(personajeAsignado, repoPersonaje.buscarPersonaje(personajeAsignado.getId()));
    }


}
