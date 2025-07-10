package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidad.Bandido;
import com.tallerwebi.dominio.entidad.Guerrero;
import com.tallerwebi.dominio.entidad.Mago;
import com.tallerwebi.dominio.entidad.Personaje;
import com.tallerwebi.dominio.interfaz.repositorio.RepositorioPersonaje;
import com.tallerwebi.dominio.interfaz.repositorio.RepositorioRol;
import com.tallerwebi.dominio.interfaz.servicio.ServicioPersonaje;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.*;

public class ServicioPersonajeTest {

    private Personaje personajeMockeado;
    private RepositorioPersonaje repoPersonajeMock;
    private ServicioPersonaje servicioPersonaje;
    private RepositorioRol repositorioRolMock;

    @BeforeEach
    public void init(){
        personajeMockeado = mock(Personaje.class);
        repoPersonajeMock = mock(RepositorioPersonaje.class);
        repositorioRolMock = mock(RepositorioRol.class);
        servicioPersonaje = new ServicioPersonajeImpl(repoPersonajeMock, repositorioRolMock);
    }

    @Test
    public void queSePuedaGuardarUnPersonaje(){

        servicioPersonaje.guardarPersonaje(personajeMockeado);

        verify(repoPersonajeMock, times(1)).guardar(personajeMockeado);
    }

    @Test
    public void queSePuedaBuscarUnPersonajePorSuId(){
        //preparacion
        when(repoPersonajeMock.buscarPersonaje(1L)).thenReturn(personajeMockeado);

        //ejecucion
        Personaje personajeObtenido = servicioPersonaje.buscarPersonaje(1L);

        //verificacion
        verify(repoPersonajeMock, times(1)).buscarPersonaje(1L);
        assertThat(personajeObtenido, equalTo(personajeMockeado));
    }

    @Test
    public void queSePuedaModificarUnPersonaje(){
        //preparacion
        Personaje personaje = new Personaje();
        personaje.setGenero("Femenino");
        servicioPersonaje.guardarPersonaje(personaje);
        personaje.setGenero("Masculino");
        when(repoPersonajeMock.buscarPersonaje(1L)).thenReturn(personaje);

        //ejecucion
        servicioPersonaje.modificar(personaje);
        Personaje personajeObtenido = servicioPersonaje.buscarPersonaje(1L);

        //verificacion
        verify(repoPersonajeMock, times(1)).modificar(personaje);
        assertThat(personajeObtenido.getGenero(), equalTo("Masculino"));
    }

    @Test
    public void queAlCrearUnPersonajeLasEstadisticasDeUnPersonajeConRolGuerreroSeSeteenDeFormaCorrecta(){

        doNothing().when(repoPersonajeMock).guardar(any());
        String nombre = "jorge";
        String genero = "Masculino";
        String imagen = "img/jorge";
        Long idRol = 1L;
        when(repositorioRolMock.obtenerRolPorId(idRol)).thenReturn(new Guerrero());
        Personaje personajeCreado = servicioPersonaje.crearPersonaje(nombre, genero, imagen, idRol);

        Integer fuerzaEsperada = 30;
        Integer fuerzaObtenida = personajeCreado.getEstadisticas().getFuerza();

        Integer inteligenciaEsperada = 0;
        Integer inteligenciaObtenida = personajeCreado.getEstadisticas().getInteligencia();

        Integer armaduraEsperada = 15;
        Integer armaduraObtenida = personajeCreado.getEstadisticas().getArmadura();

        Integer agilidadEsperada = 5;
        Integer agilidadObtenida = personajeCreado.getEstadisticas().getAgilidad();

        assertThat(fuerzaEsperada, equalTo(fuerzaObtenida));
        assertThat(inteligenciaEsperada, equalTo(inteligenciaObtenida));
        assertThat(armaduraEsperada, equalTo(armaduraObtenida));
        assertThat(agilidadEsperada, equalTo(agilidadObtenida));

    }

    @Test
    public void queAlCrearUnPersonajeLasEstadisticasDeUnPersonajeConRolMagoSeSeteenDeFormaCorrecta(){

        doNothing().when(repoPersonajeMock).guardar(any());
        String nombre = "jorge";
        String genero = "Masculino";
        String imagen = "img/jorge";
        Long idRol = 2L;
        when(repositorioRolMock.obtenerRolPorId(idRol)).thenReturn(new Mago());
        Personaje personajeCreado = servicioPersonaje.crearPersonaje(nombre, genero, imagen, idRol);

        Integer fuerzaEsperada = 0;
        Integer fuerzaObtenida = personajeCreado.getEstadisticas().getFuerza();

        Integer inteligenciaEsperada = 30;
        Integer inteligenciaObtenida = personajeCreado.getEstadisticas().getInteligencia();

        Integer armaduraEsperada = 10;
        Integer armaduraObtenida = personajeCreado.getEstadisticas().getArmadura();

        Integer agilidadEsperada = 10;
        Integer agilidadObtenida = personajeCreado.getEstadisticas().getAgilidad();

        assertThat(fuerzaEsperada, equalTo(fuerzaObtenida));
        assertThat(inteligenciaEsperada, equalTo(inteligenciaObtenida));
        assertThat(armaduraEsperada, equalTo(armaduraObtenida));
        assertThat(agilidadEsperada, equalTo(agilidadObtenida));

    }

    @Test
    public void queAlCrearUnPersonajeLasEstadisticasDeUnPersonajeConRolBandidoSeSeteenDeFormaCorrecta(){

        doNothing().when(repoPersonajeMock).guardar(any());
        String nombre = "jorge";
        String genero = "Masculino";
        String imagen = "img/jorge";
        Long idRol = 3L;
        when(repositorioRolMock.obtenerRolPorId(idRol)).thenReturn(new Bandido());
        Personaje personajeCreado = servicioPersonaje.crearPersonaje(nombre, genero, imagen, idRol);

        Integer fuerzaEsperada = 10;
        Integer fuerzaObtenida = personajeCreado.getEstadisticas().getFuerza();

        Integer inteligenciaEsperada = 5;
        Integer inteligenciaObtenida = personajeCreado.getEstadisticas().getInteligencia();

        Integer armaduraEsperada = 5;
        Integer armaduraObtenida = personajeCreado.getEstadisticas().getArmadura();

        Integer agilidadEsperada = 30;
        Integer agilidadObtenida = personajeCreado.getEstadisticas().getAgilidad();

        assertThat(fuerzaEsperada, equalTo(fuerzaObtenida));
        assertThat(inteligenciaEsperada, equalTo(inteligenciaObtenida));
        assertThat(armaduraEsperada, equalTo(armaduraObtenida));
        assertThat(agilidadEsperada, equalTo(agilidadObtenida));

    }

    @Test
    public void queDevuelvaElNombreDelPersonaje() {
        Personaje personaje = new Personaje();
        personaje.setId(1L);
        personaje.setNombre("Ezio");

        when(repoPersonajeMock.buscarPersonaje(1L)).thenReturn(personaje);

        String nombre = servicioPersonaje.getNombre(personaje);

        assertThat(nombre, equalTo("Ezio"));
    }

    @Test
    public void queDevuelvaElRolDelPersonaje() {
        Personaje personaje = new Personaje();
        personaje.setId(1L);
        personaje.setRol(new Guerrero());

        when(repoPersonajeMock.buscarPersonaje(1L)).thenReturn(personaje);

        assertThat(servicioPersonaje.getRol(personaje), equalTo(personaje.getRol()));
    }

    @Test
    public void queDevuelvaLaFuerzaDelPersonaje() {
        Personaje personaje = new Personaje();
        personaje.setId(1L);
        Estadisticas stats = new Estadisticas();
        stats.setFuerza(42);
        personaje.setEstadisticas(stats);

        when(repoPersonajeMock.buscarPersonaje(1L)).thenReturn(personaje);

        assertThat(servicioPersonaje.getFuerza(personaje), equalTo(42));
    }

    @Test
    public void queDevuelvaLaInteligenciaDelPersonaje() {
        Personaje personaje = new Personaje();
        personaje.setId(1L);
        Estadisticas stats = new Estadisticas();
        stats.setInteligencia(77);
        personaje.setEstadisticas(stats);

        when(repoPersonajeMock.buscarPersonaje(1L)).thenReturn(personaje);

        assertThat(servicioPersonaje.getInteligencia(personaje), equalTo(77));
    }

    @Test
    public void queDevuelvaLaArmaduraDelPersonaje() {
        Personaje personaje = new Personaje();
        personaje.setId(1L);
        Estadisticas stats = new Estadisticas();
        stats.setArmadura(31);
        personaje.setEstadisticas(stats);

        when(repoPersonajeMock.buscarPersonaje(1L)).thenReturn(personaje);

        assertThat(servicioPersonaje.getArmadura(personaje), equalTo(31));
    }

    @Test
    public void queDevuelvaLaAgilidadDelPersonaje() {
        Personaje personaje = new Personaje();
        personaje.setId(1L);
        Estadisticas stats = new Estadisticas();
        stats.setAgilidad(60);
        personaje.setEstadisticas(stats);

        when(repoPersonajeMock.buscarPersonaje(1L)).thenReturn(personaje);

        assertThat(servicioPersonaje.getAgilidad(personaje), equalTo(60));
    }

    @Test
    public void queDevuelvaElGeneroDelPersonaje() {
        Personaje personaje = new Personaje();
        personaje.setId(1L);
        personaje.setGenero("Femenino");

        when(repoPersonajeMock.buscarPersonaje(1L)).thenReturn(personaje);

        assertThat(servicioPersonaje.getGenero(personaje), equalTo("Femenino"));
    }

    @Test
    public void queDevuelvaUnRivalAlBuscarloPorId() {
        Personaje rival = new Personaje();
        rival.setNombre("Rival");
        when(repoPersonajeMock.buscarRival(1L)).thenReturn(rival);

        Personaje obtenido = servicioPersonaje.buscarRival(1L);

        assertThat(obtenido.getNombre(), equalTo("Rival"));
    }


}
