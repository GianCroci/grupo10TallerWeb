package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.InventarioVacioException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class ServicioInventarioTest {

    private ServicioInventario servicioInventario;
    private RepositorioInventario repositorioInventarioMock;
    private Personaje personajeMock;

    @BeforeEach
    public void init() {
        repositorioInventarioMock = mock(RepositorioInventario.class);
        servicioInventario = new ServicioInventarioImpl(repositorioInventarioMock);
        personajeMock = mock(Personaje.class);
    }

    @Test
    public void queDevuelvaElInventarioSiExiste() throws InventarioVacioException {
        List<Equipamiento> inventarioMock = List.of(new Arma());
        when(repositorioInventarioMock.obtenerInventario(1L)).thenReturn(inventarioMock);

        List<Equipamiento> resultado = servicioInventario.obtenerInventario(1L);

        assertThat(resultado, equalTo(inventarioMock));
    }

    @Test
    public void queDevuelvaInventarioVacioExceptionSiElInventarioEstaVacio() {
        when(repositorioInventarioMock.obtenerInventario(1L)).thenReturn(List.of());

        assertThrows(InventarioVacioException.class, () -> servicioInventario.obtenerInventario(1L));
    }

    @Test
    public void queDevuelvaInventarioVacioExceptionSiElInventarioEsNull() {
        when(repositorioInventarioMock.obtenerInventario(1L)).thenReturn(null);

        assertThrows(InventarioVacioException.class, () -> servicioInventario.obtenerInventario(1L));
    }

    @Test
    public void queDevuelvaElPrimerEquipadoSiExiste() throws InventarioVacioException {
        Equipamiento arma1 = new Arma();
        arma1.setEquipado(false);

        Equipamiento arma2 = new Arma();
        arma2.setEquipado(true);

        List<Equipamiento> inventario = List.of(arma1, arma2);
        when(repositorioInventarioMock.obtenerInventario(personajeMock.getId())).thenReturn(inventario);
        Equipamiento resultado = servicioInventario.obtenerPrimerEquipado(personajeMock.getId());

        assertThat(resultado, equalTo(arma2));
    }

    @Test
    public void queDevuelvaElEquipamientoPorId() {
        Equipamiento equipoMock = new Arma();
        when(repositorioInventarioMock.obtenerEquipoDePersonajePorId(1L, 2L)).thenReturn(equipoMock);

        Equipamiento resultado = servicioInventario.obtenerEquipamientoPorId(1L, 2L);

        assertThat(resultado, equalTo(equipoMock));
    }

    //el nombre parece un trabalenguas D:
    @Test
    public void queEquipeNuevoEquipoYDesequipeElEquipadoAnterior() throws InventarioVacioException {
        Rol rolMock = mock(Guerrero.class);

        Estadisticas estadisticasPersonaje = new Estadisticas();
        estadisticasPersonaje.setFuerza(1);
        estadisticasPersonaje.setInteligencia(1);
        estadisticasPersonaje.setAgilidad(1);
        estadisticasPersonaje.setArmadura(1);

        when(personajeMock.getEstadisticas()).thenReturn(estadisticasPersonaje);
        when(personajeMock.getRol()).thenReturn(rolMock);

        Equipamiento equipoNuevoMock = new Arma();
        equipoNuevoMock.setEquipado(false);
        equipoNuevoMock.setPersonaje(personajeMock);

        Estadisticas statsEquipamiento = new Estadisticas();
        statsEquipamiento.setFuerza(5);
        statsEquipamiento.setInteligencia(5);
        statsEquipamiento.setAgilidad(5);
        statsEquipamiento.setArmadura(5);
        equipoNuevoMock.setStats(statsEquipamiento);

        Equipamiento equipoActualMock = new Arma();
        equipoActualMock.setEquipado(true);
        equipoActualMock.setPersonaje(personajeMock);

        when(repositorioInventarioMock.obtenerEquipoDePersonajePorId(1L, 2L))
                .thenReturn(equipoNuevoMock);

        when(repositorioInventarioMock.obtenerInventario(1L))
                .thenReturn(List.of(equipoActualMock, equipoNuevoMock));

        Boolean resultado = servicioInventario.equipar(1L, 2L);

        assertTrue(resultado);
        assertTrue(equipoNuevoMock.getEquipado());
        assertFalse(equipoActualMock.getEquipado());

        verify(rolMock, times(1)).aplicarStatsBase(personajeMock);
    }

    @Test
    public void desequiparEquipadoActual() throws InventarioVacioException {
        Personaje personajeMock = new Personaje();
        personajeMock.setRol(mock(Rol.class));

        Equipamiento equipoMock = new Arma();
        equipoMock.setEquipado(true);
        equipoMock.setPersonaje(personajeMock);

        when(repositorioInventarioMock.obtenerEquipoDePersonajePorId(1L, 2L)).thenReturn(equipoMock);

        Boolean resultado = servicioInventario.equipar(1L, 2L);

        assertTrue(resultado);
        assertFalse(equipoMock.getEquipado());
        verify(personajeMock.getRol()).aplicarStatsBase(personajeMock);
    }

    @Test
    public void queEquipeNuevoCuandoNoHayEquipadoActual() throws InventarioVacioException {
        Rol rolMock = mock(Guerrero.class);

        Personaje personajeMock = mock(Personaje.class);

        Estadisticas estadisticasPersonaje = new Estadisticas();
        estadisticasPersonaje.setFuerza(1);
        estadisticasPersonaje.setInteligencia(1);
        estadisticasPersonaje.setAgilidad(1);
        estadisticasPersonaje.setArmadura(1);

        when(personajeMock.getEstadisticas()).thenReturn(estadisticasPersonaje);
        when(personajeMock.getRol()).thenReturn(rolMock);

        Equipamiento equipoNuevoMock = new Arma();
        equipoNuevoMock.setEquipado(false);
        equipoNuevoMock.setPersonaje(personajeMock);

        Estadisticas statsEquipamiento = new Estadisticas();
        statsEquipamiento.setFuerza(5);
        statsEquipamiento.setInteligencia(5);
        statsEquipamiento.setAgilidad(5);
        statsEquipamiento.setArmadura(5);
        equipoNuevoMock.setStats(statsEquipamiento);

        when(repositorioInventarioMock.obtenerEquipoDePersonajePorId(1L, 2L))
                .thenReturn(equipoNuevoMock);

        when(repositorioInventarioMock.obtenerInventario(1L))
                .thenReturn(List.of(equipoNuevoMock));

        Boolean resultado = servicioInventario.equipar(1L, 2L);

        assertTrue(resultado);
        assertTrue(equipoNuevoMock.getEquipado());

        verify(rolMock, never()).aplicarStatsBase(personajeMock);
    }


    @Test
    public void queDevuelvaFalseSiElEquipamientoNoExisteAlEquipar() throws InventarioVacioException {
        when(repositorioInventarioMock.obtenerEquipoDePersonajePorId(1L, 2L)).thenReturn(null);

        Boolean resultado = servicioInventario.equipar(1L, 2L);

        assertFalse(resultado);
    }

    @Test
    public void queSeSumenCorrectamenteLasEstadisticas() {
        Estadisticas estadisticasPersonaje = new Estadisticas();
        estadisticasPersonaje.setFuerza(1);
        estadisticasPersonaje.setInteligencia(1);
        estadisticasPersonaje.setAgilidad(1);
        estadisticasPersonaje.setArmadura(1);

        Personaje personaje = new Personaje();
        personaje.setEstadisticas(estadisticasPersonaje);

        Estadisticas statsEquipamiento = new Estadisticas();
        statsEquipamiento.setFuerza(2);
        statsEquipamiento.setInteligencia(3);
        statsEquipamiento.setAgilidad(4);
        statsEquipamiento.setArmadura(5);

        servicioInventario.sumarEstadisticas(personaje, statsEquipamiento);

        assertThat(personaje.getEstadisticas().getFuerza(), equalTo(3));
        assertThat(personaje.getEstadisticas().getInteligencia(), equalTo(4));
        assertThat(personaje.getEstadisticas().getAgilidad(), equalTo(5));
        assertThat(personaje.getEstadisticas().getArmadura(), equalTo(6));
    }



}
