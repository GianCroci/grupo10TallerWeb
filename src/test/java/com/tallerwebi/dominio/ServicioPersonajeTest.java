package com.tallerwebi.dominio;

import com.tallerwebi.presentacion.ControladorPersonaje;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.RequestBuilder;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ServicioPersonajeTest {

    private Personaje personajeMockeado;
    private RepositorioPersonaje repoPersonajeMock;
    private ServicioPersonaje servicioPersonaje;

    @BeforeEach
    public void init(){
        personajeMockeado = mock(Personaje.class);
        repoPersonajeMock = mock(RepositorioPersonaje.class);
        servicioPersonaje = new ServicioPersonajeImpl(repoPersonajeMock);
    }

    @Test
    public void queSePuedaCrearUnPersonajeConNombre(){

        servicioPersonaje.setNombre("Lenorwix");
        when(repoPersonajeMock.buscarPersonaje(1L)).thenReturn(personajeMockeado);
        when(personajeMockeado.getNombre()).thenReturn("Lenorwix");

        String nombreEsperado = "Lenorwix";
        String nombreObtenido = servicioPersonaje.getNombre();

        assertThat(nombreEsperado, equalTo(nombreObtenido));
    }

    @Test
    public void queSePuedaGuardarElPersonajeEnElRepositorio(){

        when(personajeMockeado.getId()).thenReturn(1L);
        when(repoPersonajeMock.guardar(1L, personajeMockeado)).thenReturn(true);

        Boolean resultado = servicioPersonaje.guardarPersonaje(personajeMockeado);

        assertThat(resultado, equalTo(true));
    }

    @Test
    public void queSeDefinaElRolDelPersonaje(){
        Personaje personaje = new Personaje();



        servicioPersonaje.setRol("Mago");

        String rolEsperado = "Mago";
        String rolObtenido = servicioPersonaje.getRol();

        assertThat(rolEsperado, equalTo(rolObtenido));
    }

    @Test
    public void queSiEsLuchadorEmpieceCon100DeFuerza(){
        Personaje personaje = new Personaje();



        servicioPersonaje.setRol("Luchador");

        Integer fuerzaEsperado = 100;
        Integer fuerzaObtenida = servicioPersonaje.getFuerza();

        assertThat(fuerzaEsperado, equalTo(fuerzaObtenida));
    }

    @Test
    public void queSeSeteenLasHabilidadesInicialesDelLuchador(){
        Personaje personaje = new Personaje();



        servicioPersonaje.setRol("Luchador");

        Integer fuerzaEsperado = 100;
        Integer fuerzaObtenida = servicioPersonaje.getFuerza();
        Integer inteligenciaEsperado = 40;
        Integer inteligenciaObtenida = servicioPersonaje.getInteligencia();
        Integer armaduraEsperado = 80;
        Integer armaduraObtenida = servicioPersonaje.getArmadura();
        Integer agilidadEsperado = 60;
        Integer agilidadObtenida = servicioPersonaje.getAgilidad();

        assertThat(fuerzaEsperado, equalTo(fuerzaObtenida));
        assertThat(inteligenciaEsperado, equalTo(inteligenciaObtenida));
        assertThat(armaduraEsperado, equalTo(armaduraObtenida));
        assertThat(agilidadEsperado, equalTo(agilidadObtenida));
    }

    @Test
    public void queSeSeteenLasHabilidadesInicialesDelMago(){
        Personaje personaje = new Personaje();



        servicioPersonaje.setRol("Mago");

        Integer fuerzaEsperado = 30;
        Integer fuerzaObtenida = servicioPersonaje.getFuerza();
        Integer inteligenciaEsperado = 100;
        Integer inteligenciaObtenida = servicioPersonaje.getInteligencia();
        Integer armaduraEsperado = 20;
        Integer armaduraObtenida = servicioPersonaje.getArmadura();
        Integer agilidadEsperado = 40;
        Integer agilidadObtenida = servicioPersonaje.getAgilidad();

        assertThat(fuerzaEsperado, equalTo(fuerzaObtenida));
        assertThat(inteligenciaEsperado, equalTo(inteligenciaObtenida));
        assertThat(armaduraEsperado, equalTo(armaduraObtenida));
        assertThat(agilidadEsperado, equalTo(agilidadObtenida));
    }

    @Test
    public void queSeSeteenLasHabilidadesInicialesDelBandido(){
        Personaje personaje = new Personaje();



        servicioPersonaje.setRol("Bandido");

        Integer fuerzaEsperado = 50;
        Integer fuerzaObtenida = servicioPersonaje.getFuerza();
        Integer inteligenciaEsperado = 70;
        Integer inteligenciaObtenida = servicioPersonaje.getInteligencia();
        Integer armaduraEsperado = 30;
        Integer armaduraObtenida = servicioPersonaje.getArmadura();
        Integer agilidadEsperado = 100;
        Integer agilidadObtenida = servicioPersonaje.getAgilidad();

        assertThat(fuerzaEsperado, equalTo(fuerzaObtenida));
        assertThat(inteligenciaEsperado, equalTo(inteligenciaObtenida));
        assertThat(armaduraEsperado, equalTo(armaduraObtenida));
        assertThat(agilidadEsperado, equalTo(agilidadObtenida));
    }

    @Test
    public void queSePuedaSetearSiElPersonajeEsFemeninoOMasculino(){
        Personaje personaje = new Personaje();

        servicioPersonaje.setGenero("Femenino");

        String generoEsperado = "Femenino";
        String generoObtenido = servicioPersonaje.getGenero();

        assertThat(generoEsperado, equalTo(generoObtenido));
    }
}
