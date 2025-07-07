package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidad.*;
import com.tallerwebi.dominio.interfaz.AccionCombate;
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

import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

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
    private Random randomMock;
    private Map<String, AccionCombate> posiblesAccionesMock;

    @BeforeEach
    public void init(){
        posiblesAccionesMock = new HashMap<>();
        randomMock = mock(Random.class);
        posiblesAccionesMock.put("ataqueFisico", new AtaqueFisico(randomMock));
        servicioPersonajeMock = mock(ServicioPersonaje.class);
        repositorioEnemigoMock = mock(RepositorioEnemigo.class);
        repositorioPersonajeMock = mock(RepositorioPersonaje.class);
        servicioBatalla = new ServicioBatallaImpl(servicioPersonajeMock, repositorioEnemigoMock, repositorioPersonajeMock, randomMock, posiblesAccionesMock);
        estadisticasTrolMock = new Estadisticas();
        estadisticasTrolMock.setInteligencia(0);
        estadisticasTrolMock.setArmadura(150);
        estadisticasTrolMock.setAgilidad(100);
        estadisticasTrolMock.setFuerza(250);
        idPersonajeMock = 1L;
        idEnemigoMock = 1L;
        slimeMock = new Slime();
        slimeMock.setId(1l);
        slimeMock.setNombre("Slime");
        slimeMock.setDescripcion("baboso");
        estadisticasSlimeMock = new Estadisticas();
        estadisticasSlimeMock.setInteligencia(5);
        estadisticasSlimeMock.setArmadura(20);
        estadisticasSlimeMock.setAgilidad(5);
        estadisticasSlimeMock.setFuerza(20);
        slimeMock.setEstadisticas(estadisticasSlimeMock);
        slimeMock.setImagenEnemigo("img/slime.png");
        slimeMock.setImagenFondo("img/calabozo.png");
        slimeMock.setNivel(5);
        slimeMock.setVida(75);

        personajeMock = new Personaje();
        personajeMock.setId(1l);
        personajeMock.setNombre("Arthas");
        personajeMock.setGenero("Masculino");
        estadisticasPersonajeMock = new Estadisticas();
        personajeMock.setEstadisticas(estadisticasPersonajeMock);
        guerreroMock = new Guerrero();
        personajeMock.setRol(guerreroMock);
        personajeMock.aplicarEstadisticasBase();
        personajeMock.calcularNivel();
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

        Integer fuerzaEsperada = 20;
        Integer fuerzaObtenida = enemigoDTO.getEstadisticas().getFuerza();

        Integer agilidadEsperada = 5;
        Integer agilidadObtenida = enemigoDTO.getEstadisticas().getAgilidad();

        Integer inteligenciaEsperada = 5;
        Integer inteligenciaObtenida = enemigoDTO.getEstadisticas().getInteligencia();

        Integer armaduraEsperada = 20;
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
    public void queEnElMetodoComenzarBatallaSiLaAgilidadDelPersonajeYElEnemigoSonIgualesTengan50PorcientoDeProbabilidadDeEmpezarYSiElNumeroRandomSeEncuentraEntre0Y49ComienceElpersonaje(){
        personajeMock.getEstadisticas().setAgilidad(10);
        slimeMock.getEstadisticas().setAgilidad(10);
        when(repositorioEnemigoMock.obtenerEnemigoPorId(idEnemigoMock)).thenReturn(slimeMock);
        when(repositorioPersonajeMock.buscarPersonaje(idPersonajeMock)).thenReturn(personajeMock);
        when(randomMock.nextInt(99)).thenReturn(49);

        BatallaDTO batalladto = servicioBatalla.comenzarBatalla(idPersonajeMock, idEnemigoMock);

        String primerTurnoEsperado = "Arthas comienza la batalla";
        String primerTurnoObtenido = batalladto.getTurno();

        assertThat(primerTurnoObtenido, equalToIgnoringCase(primerTurnoEsperado));
    }

    @Test
    public void queEnElMetodoComenzarBatallaSiLaAgilidadDelPersonajeYElEnemigoSonIgualesTengan50PorcientoDeProbabilidadDeEmpezarYSiElNumeroRandomSeEncuentraEntre50Y99ComienceElEnemigo(){
        personajeMock.getEstadisticas().setAgilidad(10);
        slimeMock.getEstadisticas().setAgilidad(10);
        when(repositorioEnemigoMock.obtenerEnemigoPorId(idEnemigoMock)).thenReturn(slimeMock);
        when(repositorioPersonajeMock.buscarPersonaje(idPersonajeMock)).thenReturn(personajeMock);
        when(randomMock.nextInt(99)).thenReturn(50);

        BatallaDTO batalladto = servicioBatalla.comenzarBatalla(idPersonajeMock, idEnemigoMock);

        String primerTurnoEsperado = "Slime comienza la batalla<br>Slime ha realizado un ataque fuerte y ha hecho 13 puntos de daño.";
        String primerTurnoObtenido = batalladto.getTurno();

        assertThat(primerTurnoObtenido, equalToIgnoringCase(primerTurnoEsperado));
    }

    @Test
    public void queEnElMetodoComenzarBatallaSiLaAgilidadDelPersonajeEsSuperiorALaDelEnemigoAumente1DePRobabilidadSobre50PorcientoPorCada1PuntoDeAgilidadSuperiorYComienzaElPersonaje(){
        personajeMock.getEstadisticas().setAgilidad(30);
        slimeMock.getEstadisticas().setAgilidad(10);
        when(repositorioEnemigoMock.obtenerEnemigoPorId(idEnemigoMock)).thenReturn(slimeMock);
        when(repositorioPersonajeMock.buscarPersonaje(idPersonajeMock)).thenReturn(personajeMock);
        when(randomMock.nextInt(99)).thenReturn(69);

        BatallaDTO batalladto = servicioBatalla.comenzarBatalla(idPersonajeMock, idEnemigoMock);

        String primerTurnoEsperado = "Arthas comienza la batalla";
        String primerTurnoObtenido = batalladto.getTurno();

        assertThat(primerTurnoObtenido, equalToIgnoringCase(primerTurnoEsperado));
    }

    @Test
    public void queEnElMetodoComenzarBatallaSiLaAgilidadDelPersonajeEsSuperiorALaDelEnemigoAumente1DePRobabilidadSobre50PorcientoPorCada1PuntoDeAgilidadSuperiorYComienzaElEnemigo(){
        personajeMock.getEstadisticas().setAgilidad(30);
        slimeMock.getEstadisticas().setAgilidad(10);
        when(repositorioEnemigoMock.obtenerEnemigoPorId(idEnemigoMock)).thenReturn(slimeMock);
        when(repositorioPersonajeMock.buscarPersonaje(idPersonajeMock)).thenReturn(personajeMock);
        when(randomMock.nextInt(99)).thenReturn(70);

        BatallaDTO batalladto = servicioBatalla.comenzarBatalla(idPersonajeMock, idEnemigoMock);

        String primerTurnoEsperado = "Slime comienza la batalla<br>Slime ha realizado un ataque fuerte y ha hecho 13 puntos de daño.";
        String primerTurnoObtenido = batalladto.getTurno();

        assertThat(primerTurnoObtenido, equalToIgnoringCase(primerTurnoEsperado));
    }

    @Test
    public void queEnElMetodoComenzarBatallaSiLaAgilidadDelPersonajeEsInferiorALaDelEnemigoDisminuye1DePRobabilidadBajo50PorcientoPorCada1PuntoDeAgilidadSuperiorDelEnemigoYComienzaElPersonaje(){
        personajeMock.getEstadisticas().setAgilidad(10);
        slimeMock.getEstadisticas().setAgilidad(30);
        when(repositorioEnemigoMock.obtenerEnemigoPorId(idEnemigoMock)).thenReturn(slimeMock);
        when(repositorioPersonajeMock.buscarPersonaje(idPersonajeMock)).thenReturn(personajeMock);
        when(randomMock.nextInt(99)).thenReturn(29);

        BatallaDTO batalladto = servicioBatalla.comenzarBatalla(idPersonajeMock, idEnemigoMock);

        String primerTurnoEsperado = "Arthas comienza la batalla";
        String primerTurnoObtenido = batalladto.getTurno();

        assertThat(primerTurnoObtenido, equalToIgnoringCase(primerTurnoEsperado));
    }

    @Test
    public void queEnElMetodoComenzarBatallaSiLaAgilidadDelPersonajeEsInferiorALaDelEnemigoDisminuye1DePRobabilidadBajo50PorcientoPorCada1PuntoDeAgilidadSuperiorDelEnemigoYComienzaElEnemigo(){
        personajeMock.getEstadisticas().setAgilidad(10);
        slimeMock.getEstadisticas().setAgilidad(30);
        when(repositorioEnemigoMock.obtenerEnemigoPorId(idEnemigoMock)).thenReturn(slimeMock);
        when(repositorioPersonajeMock.buscarPersonaje(idPersonajeMock)).thenReturn(personajeMock);
        when(randomMock.nextInt(99)).thenReturn(30);

        BatallaDTO batalladto = servicioBatalla.comenzarBatalla(idPersonajeMock, idEnemigoMock);

        String primerTurnoEsperado = "Slime comienza la batalla<br>Slime ha realizado un ataque fuerte y ha hecho 13 puntos de daño.";
        String primerTurnoObtenido = batalladto.getTurno();

        assertThat(primerTurnoObtenido, equalToIgnoringCase(primerTurnoEsperado));
    }

    @Test
    public void queElMetodoRealizarAccionActualiceLaBatallaDTOConLosPuntosDeVidaActualesEnemigosReducidosSiSeRealizaUnAtaqueFisico(){
        when(repositorioEnemigoMock.obtenerEnemigoPorId(idEnemigoMock)).thenReturn(slimeMock);
        when(repositorioPersonajeMock.buscarPersonaje(idPersonajeMock)).thenReturn(personajeMock);
        when(randomMock.nextInt(99)).thenReturn(30);
        String accion = "ataqueFisico";
        BatallaDTO batallaDTO = new BatallaDTO();
        batallaDTO.setIdEnemigo(1L);
        batallaDTO.setIdPersonaje(1L);
        batallaDTO.setVidaActualEnemigo(75);
        batallaDTO.setVidaActualPersonaje(75);
        batallaDTO.setVidaTotalEnemigo(75);
        batallaDTO.setVidaTotalPersonaje(75);
        batallaDTO.setUltimaAccionPersonaje("");
        batallaDTO.setUltimaAccionEnemigo("");

        servicioBatalla.realizarAccion(accion, batallaDTO);

        Integer vidaActualEnemigoEsperada = 55;
        Integer vidaActualEnemigoObtenida = batallaDTO.getVidaActualEnemigo();

        assertThat(vidaActualEnemigoObtenida, equalTo(vidaActualEnemigoEsperada));
    }

    @Test
    public void queElMetodoRealizarAccionActualiceLaBatallaDTOSiElEnemigoMurio(){
        when(repositorioEnemigoMock.obtenerEnemigoPorId(idEnemigoMock)).thenReturn(slimeMock);
        when(repositorioPersonajeMock.buscarPersonaje(idPersonajeMock)).thenReturn(personajeMock);
        when(randomMock.nextInt(99)).thenReturn(30);
        String accion = "ataqueFisico";
        BatallaDTO batallaDTO = new BatallaDTO();
        batallaDTO.setIdEnemigo(1L);
        batallaDTO.setIdPersonaje(1L);
        batallaDTO.setVidaActualEnemigo(5);
        batallaDTO.setVidaActualPersonaje(75);
        batallaDTO.setVidaTotalEnemigo(75);
        batallaDTO.setVidaTotalPersonaje(75);
        batallaDTO.setUltimaAccionPersonaje("");
        batallaDTO.setUltimaAccionEnemigo("");

        servicioBatalla.realizarAccion(accion, batallaDTO);

        String estadoFinalPeleaEsperado = "Arthas se levanta victorioso contra su enemigo Slime";
        String estadoFinalPeleaObtenido = batallaDTO.getEstadoFinalPelea();

        assertThat(estadoFinalPeleaObtenido, equalToIgnoringCase(estadoFinalPeleaEsperado));
    }

    @Test
    public void queElMetodoRealizarAccionAumenteElOroDelPersonajePorElEquivalenteAlNivelDelEnemigoYLoModifiqueEnLaBaseDeDatos(){
        when(repositorioEnemigoMock.obtenerEnemigoPorId(idEnemigoMock)).thenReturn(slimeMock);
        when(repositorioPersonajeMock.buscarPersonaje(idPersonajeMock)).thenReturn(personajeMock);
        when(randomMock.nextInt(99)).thenReturn(30);
        String accion = "ataqueFisico";
        BatallaDTO batallaDTO = new BatallaDTO();
        batallaDTO.setIdEnemigo(1L);
        batallaDTO.setIdPersonaje(1L);
        batallaDTO.setVidaActualEnemigo(5);
        batallaDTO.setVidaActualPersonaje(75);
        batallaDTO.setVidaTotalEnemigo(75);
        batallaDTO.setVidaTotalPersonaje(75);
        batallaDTO.setUltimaAccionPersonaje("");
        batallaDTO.setUltimaAccionEnemigo("");

        servicioBatalla.realizarAccion(accion, batallaDTO);

        String estadoFinalPeleaEsperado = "Arthas se levanta victorioso contra su enemigo Slime";
        String estadoFinalPeleaObtenido = batallaDTO.getEstadoFinalPelea();

        assertThat(estadoFinalPeleaObtenido, equalToIgnoringCase(estadoFinalPeleaEsperado));
        verify(repositorioPersonajeMock, times(1)).modificar(personajeMock);
    }

}
