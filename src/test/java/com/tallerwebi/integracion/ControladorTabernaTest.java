package com.tallerwebi.integracion;

import com.tallerwebi.config.WebSocketConfig;
import com.tallerwebi.dominio.Estadisticas;
import com.tallerwebi.dominio.PersonajeTaberna;
import com.tallerwebi.dominio.entidad.Arma;
import com.tallerwebi.dominio.entidad.Personaje;
import com.tallerwebi.dominio.entidad.Usuario;
import com.tallerwebi.dominio.interfaz.repositorio.RepositorioPersonaje;
import com.tallerwebi.dominio.interfaz.repositorio.RepositorioTaberna;
import com.tallerwebi.dominio.interfaz.repositorio.RepositorioUsuario;
import com.tallerwebi.dominio.interfaz.servicio.ServicioTaberna;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.stream.IntStream;

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
        personaje.setId(1L);
        personaje.setNombre("TomasTest");
        personaje.setGenero("Masculino");
        personaje.setImagen("imagen.png");
        personaje.setEstadisticas(new Estadisticas());
        personaje.setOro(0);

        repoPersonaje.guardar(personaje);


        usuario.setPersonaje(personaje);

    }

    @Test
    public void noDebePermitirAccederATabernaSiNoEstaLogueado() throws Exception {
        MvcResult result = mockMvc.perform(get("/taberna"))
                .andExpect(status().is3xxRedirection())
                .andReturn();

        ModelAndView mav = result.getModelAndView();
        assertThat(mav.getViewName(), equalToIgnoringCase("redirect:/login"));
    }

    @Test
    public void queAlEntrarATabernaSeMuestreLaVistaCorrecta() throws Exception {

        MvcResult result = mockMvc.perform(get("/taberna")
                        .sessionAttr("idPersonaje", personaje.getId()))
                .andExpect(status().isOk())
                .andReturn();
        personaje = repoPersonaje.buscarPersonaje(personaje.getId());

        ModelAndView mav = result.getModelAndView();
        assertNotNull(mav);
        assertThat(mav.getViewName(), equalToIgnoringCase("taberna"));
        assertTrue(mav.getModel().containsKey("cervezasDisponibles"));
        assertTrue(mav.getModel().containsKey("personajes"));
    }

    @Test
    public void debePermitirInvitarUnaCerveza() throws Exception {
        mockMvc.perform(post("/invitarTrago")
                        .param("personajeSeleccionado", "MERCADER")
                        .sessionAttr("idPersonaje", personaje.getId()))
                .andExpect(status().isOk())
                .andReturn();

        int cervezas = repoTaberna.getCantidadCervezasInvitadas(personaje.getId(), PersonajeTaberna.MERCADER);
        assertEquals(1, cervezas);
    }

    @Test
    public void noDebePermitirInvitarMasDeDosCervezas() throws Exception {

        // Invita cuatro veces pero solo debería contar dos
        for (int i = 0; i < 4; i++) {
            mockMvc.perform(post("/invitarTrago")
                            .param("personajeSeleccionado", "GUARDIA")
                            .sessionAttr("idPersonaje", personaje.getId()))
                    .andExpect(status().isOk());
        }

        int cervezas = repoTaberna.getCantidadCervezasInvitadas(personaje.getId(), PersonajeTaberna.GUARDIA);
        assertEquals(2, cervezas);


    }

}
