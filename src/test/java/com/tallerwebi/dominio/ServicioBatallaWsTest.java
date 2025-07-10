package com.tallerwebi.dominio;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

import com.tallerwebi.dominio.entidad.Batalla;
import com.tallerwebi.dominio.entidad.Personaje;
import com.tallerwebi.dominio.interfaz.servicio.ServicioPersonaje;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ServicioBatallaWsTest {

    private Map<String, Batalla> batallasMock;
    private ServicioPersonaje servicioPersonajeMock;
    private ServicioBatallaWsImpl servicioBatalla;
    private Personaje personajeA;
    private Personaje personajeB;
    private Batalla batallaMockeada;

    @BeforeEach
    public void init() {
        batallasMock = mock(Map.class);
        servicioPersonajeMock = mock(ServicioPersonaje.class);
        servicioBatalla = new ServicioBatallaWsImpl(servicioPersonajeMock, batallasMock);

        personajeA = mock(Personaje.class);
        personajeB = mock(Personaje.class);
        batallaMockeada = mock(Batalla.class);

        when(personajeA.getId()).thenReturn(1L);
        when(personajeB.getId()).thenReturn(2L);
        when(batallaMockeada.getJugadorA()).thenReturn(personajeA);
        when(batallaMockeada.getJugadorB()).thenReturn(personajeB);
    }

    @Test
    public void queSePuedaBuscarSalaPendienteCuandoPersonajeEsJugadorA() {
        // preparacion
        Long idPersonaje = 1L;
        String claveEsperada = "1_2";
        when(batallasMock.keySet()).thenReturn(Set.of(claveEsperada));
        when(batallasMock.get(claveEsperada)).thenReturn(batallaMockeada);

        // ejecucion
        Optional<String> resultado = servicioBatalla.buscarSalaPendientePara(idPersonaje);

        // verificacion
        verify(batallasMock, times(1)).keySet();
        verify(batallasMock, times(1)).get(claveEsperada);
        assertTrue(resultado.isPresent());
        assertThat(resultado.get(), equalTo(claveEsperada));
    }

    @Test
    public void queSePuedaBuscarSalaPendienteCuandoPersonajeEsJugadorB() {
        // preparacion
        Long idPersonaje = 2L;
        String claveEsperada = "1_2";
        when(batallasMock.keySet()).thenReturn(Set.of(claveEsperada));
        when(batallasMock.get(claveEsperada)).thenReturn(batallaMockeada);

        // ejecucion
        Optional<String> resultado = servicioBatalla.buscarSalaPendientePara(idPersonaje);

        // verificacion
        verify(batallasMock, times(1)).keySet();
        verify(batallasMock, times(1)).get(claveEsperada);
        assertTrue(resultado.isPresent());
        assertThat(resultado.get(), equalTo(claveEsperada));
    }

    @Test
    public void queRetorneVacioCuandoPersonajeNoEstaEnNingunaBatalla() {
        // preparacion
        Long idPersonaje = 3L;
        String clave = "1_2";
        when(batallasMock.keySet()).thenReturn(Set.of(clave));
        when(batallasMock.get(clave)).thenReturn(batallaMockeada);

        // ejecucion
        Optional<String> resultado = servicioBatalla.buscarSalaPendientePara(idPersonaje);

        // verificacion
        verify(batallasMock, times(1)).keySet();
        verify(batallasMock, times(1)).get(clave);
        assertFalse(resultado.isPresent());
    }


    @Test
    public void queCreeSalaCuandoNoExiste() {
        // preparacion
        Long idA = 1L;
        Long idB = 2L;
        String clave1 = "1_2";
        String clave2 = "2_1";
        when(batallasMock.containsKey(clave1)).thenReturn(false);
        when(batallasMock.containsKey(clave2)).thenReturn(false);
        when(servicioPersonajeMock.buscarPersonaje(idA)).thenReturn(personajeA);
        when(servicioPersonajeMock.buscarPersonaje(idB)).thenReturn(personajeB);

        // ejecucion
        String resultado = servicioBatalla.obtenerOSalaExistente(idA, idB);

        // verificacion
        verify(batallasMock, times(1)).containsKey(clave1);
        verify(batallasMock, times(1)).containsKey(clave2);
        verify(servicioPersonajeMock, times(1)).buscarPersonaje(idA);
        verify(servicioPersonajeMock, times(1)).buscarPersonaje(idB);
        verify(personajeA, times(1)).setEsTuTurno(true);
        verify(personajeB, times(1)).setEsTuTurno(false);
        assertThat(resultado, equalTo(clave1));
    }

    @Test
    public void queSePuedaObtenerBatallaPorSalaId() {
        // preparacion
        String salaId = "1_2";
        when(batallasMock.get(salaId)).thenReturn(batallaMockeada);

        // ejecucion
        Batalla resultado = servicioBatalla.obtenerBatalla(salaId);

        // verificacion
        verify(batallasMock, times(1)).get(salaId);
        assertThat(resultado, equalTo(batallaMockeada));
    }

    @Test
    public void queRetorneNullCuandoSalaNoExiste() {
        // preparacion
        String salaId = "inexistente";
        when(batallasMock.get(salaId)).thenReturn(null);

        // ejecucion
        Batalla resultado = servicioBatalla.obtenerBatalla(salaId);

        // verificacion
        verify(batallasMock, times(1)).get(salaId);
        assertNull(resultado);
    }
}
