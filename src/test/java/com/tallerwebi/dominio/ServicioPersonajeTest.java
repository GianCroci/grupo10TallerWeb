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

   /* @Test
    public void queSePuedaCrearUnPersonajeConNombre(){

        servicioPersonaje.setNombre("Lenorwix");
        when(repoPersonajeMock.buscarPersonaje(1L)).thenReturn(personajeMockeado);
        when(personajeMockeado.getNombre()).thenReturn("Lenorwix");

        String nombreEsperado = "Lenorwix";
        String nombreObtenido = servicioPersonaje.getNombre();

        assertThat(nombreEsperado, equalTo(nombreObtenido));
    }

   /* @Test
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

    */
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

}
