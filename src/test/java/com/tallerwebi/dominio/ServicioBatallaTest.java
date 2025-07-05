package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidad.*;
import com.tallerwebi.dominio.interfaz.repositorio.RepositorioEnemigo;
import com.tallerwebi.dominio.interfaz.repositorio.RepositorioPersonaje;
import com.tallerwebi.dominio.interfaz.servicio.ServicioBatalla;
import com.tallerwebi.dominio.interfaz.servicio.ServicioPersonaje;
import com.tallerwebi.dominio.interfaz.servicio.ServicioUsuario;
import com.tallerwebi.presentacion.BatallaDTO;
import com.tallerwebi.presentacion.ControladorBatalla;
import com.tallerwebi.presentacion.EnemigoDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ServicioBatallaTest {

    private RepositorioEnemigo repositorioEnemigoMock;
    private RepositorioPersonaje repositorioPersonajeMock;
    private ServicioPersonaje servicioPersonajeMock;
    private ServicioBatalla servicioBatalla;
    private Estadisticas estadisticasSlimeMock;
    private Estadisticas estadisticasTrolMock;
    private Estadisticas estadisticasPersonajeMock;
    private Long idPersonajeMock;
    private Long idEnemigoMock;
    private Enemigo slimeMock;
    private Personaje personajeMock;
    private Rol guerreroMock;

    @BeforeEach
    public void init(){
        servicioPersonajeMock = mock(ServicioPersonaje.class);
        repositorioEnemigoMock = mock(RepositorioEnemigo.class);
        repositorioPersonajeMock = mock(RepositorioPersonaje.class);
        servicioBatalla = new ServicioBatallaImpl(servicioPersonajeMock, repositorioEnemigoMock, repositorioPersonajeMock);
        estadisticasTrolMock = new Estadisticas();
        estadisticasTrolMock.setInteligencia(20);
        estadisticasTrolMock.setArmadura(20);
        estadisticasTrolMock.setAgilidad(20);
        estadisticasTrolMock.setFuerza(20);
        idPersonajeMock = 1L;
        idEnemigoMock = 1L;
        slimeMock = new Slime();
        slimeMock.setId(1l);
        slimeMock.setNombre("Slime");
        slimeMock.setDescripcion("baboso");
        estadisticasSlimeMock = new Estadisticas();
        estadisticasSlimeMock.setInteligencia(50);
        estadisticasSlimeMock.setArmadura(50);
        estadisticasSlimeMock.setAgilidad(60);
        estadisticasSlimeMock.setFuerza(50);
        slimeMock.setEstadisticas(estadisticasSlimeMock);
        slimeMock.setImagenEnemigo("img/slime.png");
        slimeMock.setImagenFondo("img/calabozo.png");
        slimeMock.setNivel(5);
        slimeMock.setVida(75);


        personajeMock = new Personaje();
        personajeMock.setId(1l);
        personajeMock.setNombre("Arthas");
        personajeMock.setGenero("Masculino");
        guerreroMock = new Guerrero();
        personajeMock.setRol(guerreroMock);
        estadisticasPersonajeMock = new Estadisticas();
        estadisticasPersonajeMock.setFuerza(30);
        estadisticasPersonajeMock.setInteligencia(5);
        estadisticasPersonajeMock.setArmadura(15);
        estadisticasPersonajeMock.setAgilidad(5);
        personajeMock.setEstadisticas(estadisticasPersonajeMock);
        personajeMock.setNivel(5);
        personajeMock.setVida(75);
        personajeMock.setImagen("img/guerrero.png");
        personajeMock.setOro(500);
    }

    @Test
    public void queElMetodoSetearAtributosTablonDelObjetoEnemigoDTOSeAsignenCorrectamenteAlPasarleUnEnemigo(){

        EnemigoDTO enemigoDTO = new EnemigoDTO();

        enemigoDTO.setearAtributosTablon(slimeMock);

        Long idEsperado = 1L;
        Long idObtenido = enemigoDTO.getId();

        String nombreEsperado = "slime";
        String nombreObtenido = enemigoDTO.getNombre();

        String descripcionEsperada = "baboso";
        String descripcionObtenida = enemigoDTO.getDescripcion();

        String imagenEnemigoEsperada = "img/slime.png";
        String imagenEnemigoObtenida = enemigoDTO.getImagenEnemigo();

        Integer fuerzaEsperada = 50;
        Integer fuerzaObtenida = enemigoDTO.getEstadisticas().getFuerza();

        Integer agilidadEsperada = 60;
        Integer agilidadObtenida = enemigoDTO.getEstadisticas().getAgilidad();

        Integer inteligenciaEsperada = 50;
        Integer inteligenciaObtenida = enemigoDTO.getEstadisticas().getInteligencia();

        Integer armaduraEsperada = 50;
        Integer armaduraObtenida = enemigoDTO.getEstadisticas().getArmadura();

        Integer nivelEsperado = 5;
        Integer nivelObtenido = enemigoDTO.getNivel();

        assertThat(idObtenido, equalTo(idEsperado));
        assertThat(nombreObtenido, equalToIgnoringCase(nombreEsperado));
        assertThat(descripcionObtenida, equalToIgnoringCase(descripcionEsperada));
        assertThat(imagenEnemigoObtenida, equalToIgnoringCase(imagenEnemigoEsperada));
        assertThat(fuerzaObtenida, equalTo(fuerzaEsperada));
        assertThat(agilidadObtenida, equalTo(agilidadEsperada));
        assertThat(inteligenciaObtenida, equalTo(inteligenciaEsperada));
        assertThat(armaduraObtenida, equalTo(armaduraEsperada));
        assertThat(nivelObtenido, equalTo(nivelEsperado));
    }

    @Test
    public void queElMetodoBusqueUnaListaDeEnemigosYRetorneUnaListaDeEnemigosDTO(){
        List<Enemigo> enemigos = new ArrayList<>();
        enemigos.add(slimeMock);
        when(repositorioEnemigoMock.obtenerEnemigos()).thenReturn(enemigos);
        List<EnemigoDTO> enemigosDTO = servicioBatalla.buscarEnemigosParaTablon();

        assertThat(enemigosDTO.get(0), instanceOf(EnemigoDTO.class));
    }

    @Test
    public void queElMetodoBusqueUnaListaDeEnemigosYRetorneUnaListaDeEnemigosDTOOrdenadaPorNivelAscendente(){
        List<Enemigo> enemigos = new ArrayList<>();
        enemigos.add(slimeMock);

        Enemigo trol = new Trol();
        trol.setId(2l);
        trol.setNombre("trol");
        trol.setDescripcion("trolaso");
        trol.setEstadisticas(estadisticasTrolMock);
        trol.setImagenEnemigo("trol.png");
        trol.setImagenFondo("caverna.png");
        trol.setNivel(3);
        trol.setVida(100);
        enemigos.add(trol);

        when(repositorioEnemigoMock.obtenerEnemigos()).thenReturn(enemigos);
        List<EnemigoDTO> enemigosDTO = servicioBatalla.buscarEnemigosParaTablon();

        Long primerIdEsperado = 2L;
        Long primerIdObtenido = enemigosDTO.get(0).getId();

        Long segundoIdEsperado = 1L;
        Long segundoIdObtenido = enemigosDTO.get(1).getId();


        assertThat(primerIdObtenido, equalTo(primerIdEsperado));
        assertThat(segundoIdObtenido, equalTo(segundoIdEsperado));
    }

    @Test
    public void queElMetodoComenzarBatallaDevuelvaUnObjetoBatallaDTO(){
        when(repositorioEnemigoMock.obtenerEnemigoPorId(idEnemigoMock)).thenReturn(slimeMock);
        when(repositorioPersonajeMock.buscarPersonaje(idPersonajeMock)).thenReturn(personajeMock);
        BatallaDTO batalladto = servicioBatalla.comenzarBatalla(idPersonajeMock, idEnemigoMock);

        assertThat(batalladto, notNullValue());
    }

    @Test
    public void queElMetodoComenzarBatallaBusqueElPersonajeYElEnemigoUtilizandoLosIdsYGuardeLosDatosNecesariosEnElObjetoBatallaDTO(){
        when(repositorioEnemigoMock.obtenerEnemigoPorId(idEnemigoMock)).thenReturn(slimeMock);
        when(repositorioPersonajeMock.buscarPersonaje(idPersonajeMock)).thenReturn(personajeMock);

        BatallaDTO batalladto = servicioBatalla.comenzarBatalla(idPersonajeMock, idEnemigoMock);

        Long idPersonajeEsperado = 1L;
        Long idPersonajeObtenido = batalladto.getIdPersonaje();

        String nombrePersonajeEsperado = "Arthas";
        String nombrePersonajeObtenido = batalladto.getNombrePersonaje();

        String imagenPersonajeEsperado = "img/guerrero.png";
        String imagenPersonajeObtenido = batalladto.getImagenPersonaje();

        Integer nivelPersonajeEsperado = 5;
        Integer nivelPersonajeObtenido = batalladto.getNivelPersonaje();

        Integer vidaTotalPersonajeEsperado = 75;
        Integer vidaTotalPersonajeObtenido = batalladto.getVidaTotalPersonaje();

        Long idEnemigoEsperado = 1L;
        Long idEnemigoObtenido = batalladto.getIdEnemigo();

        String nombreEnemigoEsperado = "Slime";
        String nombreEnemigoObtenido = batalladto.getNombreEnemigo();

        String imagenEnemigoEsperado = "img/slime.png";
        String imagenEnemigoObtenido = batalladto.getImagenEnemigo();

        Integer nivelEnemigoEsperado = 5;
        Integer nivelEnemigoObtenido = batalladto.getNivelEnemigo();

        Integer vidaTotalEnemigoEsperado = 75;
        Integer vidaTotalEnemigoObtenido = batalladto.getVidaTotalEnemigo();

        String imagenFondoEsperado = "img/calabozo.png";
        String imagenFondoObtenido = batalladto.getImagenFondo();

        assertThat(idPersonajeObtenido, equalTo(idPersonajeEsperado));
        assertThat(nombrePersonajeObtenido, equalToIgnoringCase(nombrePersonajeEsperado));
        assertThat(imagenPersonajeObtenido, equalToIgnoringCase(imagenPersonajeEsperado));
        assertThat(nivelPersonajeObtenido, equalTo(nivelPersonajeEsperado));
        assertThat(vidaTotalPersonajeObtenido, equalTo(vidaTotalPersonajeEsperado));

        assertThat(idEnemigoObtenido, equalTo(idEnemigoEsperado));
        assertThat(nombreEnemigoObtenido, equalToIgnoringCase(nombreEnemigoEsperado));
        assertThat(imagenEnemigoObtenido, equalToIgnoringCase(imagenEnemigoEsperado));
        assertThat(nivelEnemigoObtenido, equalTo(nivelEnemigoEsperado));
        assertThat(vidaTotalEnemigoObtenido, equalTo(vidaTotalEnemigoEsperado));
        assertThat(imagenFondoObtenido, equalToIgnoringCase(imagenFondoEsperado));
    }

    @Test
    public void queElMetodoComenzarBatallaUtiliceLaEstadisticaDeAgilidadDelPersonajeYElEnemigoParaDeterminarQuienComienzaElTurno(){
        when(repositorioEnemigoMock.obtenerEnemigoPorId(idEnemigoMock)).thenReturn(slimeMock);
        when(repositorioPersonajeMock.buscarPersonaje(idPersonajeMock)).thenReturn(personajeMock);

        BatallaDTO batalladto = servicioBatalla.comenzarBatalla(idPersonajeMock, idEnemigoMock);

        String primerTurnoEsperado = "Slime comienza la batalla y realiza la accion ";
        String primerTurnoObtenido = batalladto.getPrimerTurno();

        assertThat(primerTurnoObtenido, equalToIgnoringCase(primerTurnoEsperado));
    }
}
