package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidad.*;
import com.tallerwebi.dominio.interfaz.AccionCombate;
import com.tallerwebi.presentacion.BatallaDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AccionCombateTest {

    private Enemigo enemigoMock;
    private Estadisticas estadisticasSlimeMock;
    private Personaje personajeMock;
    private Estadisticas estadisticasPersonajeMock;
    private Rol guerreroMock;
    private AccionCombate accionCombateMock;
    private BatallaDTO batallaDTOMock;
    private Random randomMock;

    @BeforeEach
    public void init(){
        randomMock = mock(Random.class);
        enemigoMock = new Slime();
        enemigoMock.setId(1l);
        enemigoMock.setNombre("Slime");
        enemigoMock.setDescripcion("baboso");
        estadisticasSlimeMock = new Estadisticas();
        estadisticasSlimeMock.setInteligencia(5);
        estadisticasSlimeMock.setArmadura(20);
        estadisticasSlimeMock.setAgilidad(5);
        estadisticasSlimeMock.setFuerza(20);
        enemigoMock.setEstadisticas(estadisticasSlimeMock);
        enemigoMock.setImagenEnemigo("img/slime.png");
        enemigoMock.setImagenFondo("img/calabozo.png");
        enemigoMock.setNivel(5);
        enemigoMock.setVida(75);

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

        batallaDTOMock = new BatallaDTO();
        batallaDTOMock.setIdEnemigo(enemigoMock.getId());
        batallaDTOMock.setIdPersonaje(personajeMock.getId());
        batallaDTOMock.setVidaTotalEnemigo(personajeMock.getVida());
        batallaDTOMock.setVidaTotalPersonaje(personajeMock.getVida());
        batallaDTOMock.setVidaActualPersonaje(personajeMock.getVida());
        batallaDTOMock.setVidaActualEnemigo(enemigoMock.getVida());
        batallaDTOMock.setUltimaAccionPersonaje("");
        batallaDTOMock.setUltimaAccionEnemigo("");
    }

    @Test
    public void queCuandoElJugadorRealiceLaAccionAtaqueFisicoLaVidaActualDelEnemigoDismunuyaDependiendoDeLaFuerzaDelPersonajeYLaArmaduraDelEnemigo(){

        accionCombateMock = new AtaqueFisico(randomMock);

        when(randomMock.nextInt(99)).thenReturn(13);

        accionCombateMock.realizarAccionJugador(personajeMock, enemigoMock, batallaDTOMock);

        Integer vidaActualEnemigoEsperada = 55;
        Integer vidaActualEnemigoObtenida = batallaDTOMock.getVidaActualEnemigo();

        assertThat(vidaActualEnemigoObtenida, equalTo(vidaActualEnemigoEsperada));
    }

    @Test
    public void queCuandoElJugadorRealiceLaAccionAtaqueFisicoLaVidaActualDelEnemigoNoDisminuyaSiElEnemigoLogroEsquivar(){
        accionCombateMock = new AtaqueFisico(randomMock);

        enemigoMock.getEstadisticas().setAgilidad(50);
        when(randomMock.nextInt(99)).thenReturn(13);

        accionCombateMock.realizarAccionJugador(personajeMock, enemigoMock, batallaDTOMock);

        Integer vidaActualEnemigoEsperada = 75;
        Integer vidaActualEnemigoObtenida = batallaDTOMock.getVidaActualEnemigo();

        assertThat(vidaActualEnemigoObtenida, equalTo(vidaActualEnemigoEsperada));
    }

    @Test
    public void queCuandoElJugadorRealiceLaAccionAtaqueMagicoLaVidaActualDelEnemigoDismunuyaDependiendoDeLaInteligenciaDelPersonajeYLaArmaduraDelEnemigo(){
        accionCombateMock = new AtaqueMagico(randomMock);
        personajeMock.getEstadisticas().setInteligencia(30);
        when(randomMock.nextInt(99)).thenReturn(13);

        accionCombateMock.realizarAccionJugador(personajeMock, enemigoMock, batallaDTOMock);

        Integer vidaActualEnemigoEsperada = 55;
        Integer vidaActualEnemigoObtenida = batallaDTOMock.getVidaActualEnemigo();

        assertThat(vidaActualEnemigoObtenida, equalTo(vidaActualEnemigoEsperada));
    }

    @Test
    public void queCuandoElJugadorRealiceLaAccionAtaqueMagicoLaVidaActualDelEnemigoNoDisminuyaSiElEnemigoLogroEsquivar(){
        accionCombateMock = new AtaqueMagico(randomMock);

        enemigoMock.getEstadisticas().setAgilidad(50);
        when(randomMock.nextInt(99)).thenReturn(13);

        accionCombateMock.realizarAccionJugador(personajeMock, enemigoMock, batallaDTOMock);

        Integer vidaActualEnemigoEsperada = 75;
        Integer vidaActualEnemigoObtenida = batallaDTOMock.getVidaActualEnemigo();

        assertThat(vidaActualEnemigoObtenida, equalTo(vidaActualEnemigoEsperada));
    }

    @Test
    public void queCuandoElJugadorRealiceLaAccionDefensaSeActualiceElAtributoUltimoTurnoDelObjetoBatallaDTO(){
        accionCombateMock = new Defensa();

        accionCombateMock.realizarAccionJugador(personajeMock, enemigoMock, batallaDTOMock);

        String ultimoTurnoPersonajeEsperado = "defensa";
        String ultimoTurnoPersonajeObtenido = batallaDTOMock.getUltimaAccionPersonaje();

        assertThat(ultimoTurnoPersonajeObtenido, equalToIgnoringCase(ultimoTurnoPersonajeEsperado));
    }

    @Test
    public void queCuandoElJugadorRealiceLaAccionEsquivarSeActualiceElAtributoUltimoTurnoDelObjetoBatallaDTO(){
        accionCombateMock = new Esquivar();

        accionCombateMock.realizarAccionJugador(personajeMock, enemigoMock, batallaDTOMock);

        String ultimoTurnoPersonajeEsperado = "esquivar";
        String ultimoTurnoPersonajeObtenido = batallaDTOMock.getUltimaAccionPersonaje();

        assertThat(ultimoTurnoPersonajeObtenido, equalToIgnoringCase(ultimoTurnoPersonajeEsperado));
    }
}
