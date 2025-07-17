package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidad.*;
import com.tallerwebi.dominio.excepcion.InventarioVacioException;
import com.tallerwebi.dominio.interfaz.repositorio.RepositorioInventario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ServicioInventarioTest {

    private ServicioInventarioImpl servicioInventario;
    private RepositorioInventario repositorioInventarioMock;

    @BeforeEach
    public void init() {
        repositorioInventarioMock = mock(RepositorioInventario.class);
        servicioInventario = new ServicioInventarioImpl(repositorioInventarioMock);
    }

    @Test
    public void obtenerListaDelInventarioSiNoEstaVacio() throws InventarioVacioException {
        List<Equipamiento> inventarioMock = List.of(new Arma());
        when(repositorioInventarioMock.obtenerInventario(1L)).thenReturn(inventarioMock);

        List<Equipamiento> resultado = servicioInventario.obtenerInventario(1L);

        assertThat(resultado, equalTo(inventarioMock));
    }

    @Test
    public void queLanceUnaExcepcionCuandoElInventarioEsteVacio() {
        when(repositorioInventarioMock.obtenerInventario(1L)).thenReturn(List.of());

        assertThrows(InventarioVacioException.class, () -> servicioInventario.obtenerInventario(1L));
    }

    @Test
    public void obtenerEquipoDePersonajePorId() {
        Equipamiento equipoMock = new Arma();
        when(repositorioInventarioMock.obtenerEquipoDePersonajePorId(1L, 2L)).thenReturn(equipoMock);

        Equipamiento resultado = servicioInventario.obtenerEquipamientoPorId(1L, 2L);

        assertThat(resultado, equalTo(equipoMock));
    }

    @Test
    public void queRetorneFalseSiSeIntentaEquiparUnEquipoInexistente() throws InventarioVacioException {
        when(repositorioInventarioMock.obtenerEquipoDePersonajePorId(1L, 2L)).thenReturn(null);

        Boolean resultado = servicioInventario.equipar(1L, 2L);

        assertFalse(resultado);
    }

    @Test
    public void equiparEquipoYQueSeModifiquenLasEstadisticas() throws InventarioVacioException {
        Personaje personaje = new Personaje();
        Estadisticas statsPersonaje = new Estadisticas();
        statsPersonaje.setFuerza(10);
        statsPersonaje.setInteligencia(10);
        statsPersonaje.setAgilidad(10);
        statsPersonaje.setArmadura(10);
        personaje.setEstadisticas(statsPersonaje);

        Equipamiento equipo = new Arma();
        equipo.setEquipado(true);
        equipo.setPersonaje(personaje);

        Estadisticas statsEquipo = new Estadisticas();
        statsEquipo.setFuerza(5);
        statsEquipo.setInteligencia(5);
        statsEquipo.setAgilidad(5);
        statsEquipo.setArmadura(5);
        equipo.setStats(statsEquipo);

        when(repositorioInventarioMock.obtenerEquipoDePersonajePorId(1L, 2L)).thenReturn(equipo);

        Boolean resultado = servicioInventario.equipar(1L, 2L);

        assertTrue(resultado);
        assertFalse(equipo.getEquipado());
        assertThat(personaje.getEstadisticas().getFuerza(), equalTo(5));
        assertThat(personaje.getEstadisticas().getInteligencia(), equalTo(5));
        assertThat(personaje.getEstadisticas().getAgilidad(), equalTo(5));
        assertThat(personaje.getEstadisticas().getArmadura(), equalTo(5));
    }

    @Test
    public void desequiparEquipoActualYEquiparUnoNuevo() throws InventarioVacioException {
        Personaje personaje = new Personaje();
        Estadisticas statsPersonaje = new Estadisticas();
        statsPersonaje.setFuerza(20);
        statsPersonaje.setInteligencia(20);
        statsPersonaje.setAgilidad(20);
        statsPersonaje.setArmadura(20);
        personaje.setEstadisticas(statsPersonaje);

        Equipamiento equipoNuevo = new Arma();
        equipoNuevo.setEquipado(false);
        equipoNuevo.setPersonaje(personaje);
        Estadisticas statsNuevo = new Estadisticas();
        statsNuevo.setFuerza(5);
        statsNuevo.setInteligencia(5);
        statsNuevo.setAgilidad(5);
        statsNuevo.setArmadura(5);
        equipoNuevo.setStats(statsNuevo);

        Equipamiento equipoViejo = new Arma();
        equipoViejo.setEquipado(true);
        equipoViejo.setPersonaje(personaje);
        Estadisticas statsViejo = new Estadisticas();
        statsViejo.setFuerza(3);
        statsViejo.setInteligencia(3);
        statsViejo.setAgilidad(3);
        statsViejo.setArmadura(3);
        equipoViejo.setStats(statsViejo);

        when(repositorioInventarioMock.obtenerEquipoDePersonajePorId(1L, 2L)).thenReturn(equipoNuevo);
        when(repositorioInventarioMock.obtenerInventario(1L)).thenReturn(List.of(equipoViejo, equipoNuevo));

        Boolean resultado = servicioInventario.equipar(1L, 2L);

        assertTrue(resultado);
        assertTrue(equipoNuevo.getEquipado());
        assertFalse(equipoViejo.getEquipado());

        assertThat(personaje.getEstadisticas().getFuerza(), equalTo(22));
        assertThat(personaje.getEstadisticas().getInteligencia(), equalTo(22));
        assertThat(personaje.getEstadisticas().getAgilidad(), equalTo(22));
        assertThat(personaje.getEstadisticas().getArmadura(), equalTo(22));
    }

    @Test
    public void sumarEstadisticas() {
        Estadisticas statsBase = new Estadisticas();
        statsBase.setFuerza(1);
        statsBase.setInteligencia(1);
        statsBase.setAgilidad(1);
        statsBase.setArmadura(1);

        Estadisticas statsSumar = new Estadisticas();
        statsSumar.setFuerza(2);
        statsSumar.setInteligencia(3);
        statsSumar.setAgilidad(4);
        statsSumar.setArmadura(5);

        servicioInventario.sumarEstadisticas(statsBase, statsSumar);

        assertThat(statsBase.getFuerza(), equalTo(3));
        assertThat(statsBase.getInteligencia(), equalTo(4));
        assertThat(statsBase.getAgilidad(), equalTo(5));
        assertThat(statsBase.getArmadura(), equalTo(6));
    }

    @Test
    public void restarEstadisticas() {
        Estadisticas statsBase = new Estadisticas();
        statsBase.setFuerza(5);
        statsBase.setInteligencia(5);
        statsBase.setAgilidad(5);
        statsBase.setArmadura(5);

        Estadisticas statsRestar = new Estadisticas();
        statsRestar.setFuerza(2);
        statsRestar.setInteligencia(1);
        statsRestar.setAgilidad(3);
        statsRestar.setArmadura(4);

        servicioInventario.restarEstadisticas(statsBase, statsRestar);

        assertThat(statsBase.getFuerza(), equalTo(3));
        assertThat(statsBase.getInteligencia(), equalTo(4));
        assertThat(statsBase.getAgilidad(), equalTo(2));
        assertThat(statsBase.getArmadura(), equalTo(1));
    }

    @Test
    public void obtenerEquipadoPorTipoEspecifico() throws InventarioVacioException {
        Personaje personaje = new Personaje();

        Equipamiento equipo1 = new Arma();
        equipo1.setEquipado(true);
        equipo1.setPersonaje(personaje);

        Equipamiento equipo2 = new Casco();
        equipo2.setEquipado(false);
        equipo2.setPersonaje(personaje);

        when(repositorioInventarioMock.obtenerInventario(1L)).thenReturn(List.of(equipo1, equipo2));

        Equipamiento resultado = servicioInventario.obtenerEquipamientoEquipadoPorTipo(1L, Arma.class);

        assertNotNull(resultado);
        assertTrue(resultado.getEquipado());
        assertThat(resultado, equalTo(equipo1));
    }

    @Test
    public void obtenerInventarioFiltradoPorTipoEspecifico() throws InventarioVacioException {
        Equipamiento arma = new Arma();
        Equipamiento casco = new Casco();

        when(repositorioInventarioMock.obtenerInventario(1L)).thenReturn(List.of(arma, casco));

        List<Equipamiento> resultadoTodos = servicioInventario.obtenerInventarioFiltradoPorTipo(1L, "Todos");
        List<Equipamiento> resultadoFiltrado = servicioInventario.obtenerInventarioFiltradoPorTipo(1L, "Arma");

        assertThat(resultadoTodos, hasSize(2));
        assertThat(resultadoFiltrado, contains(arma));
    }

    @Test
    public void obtenerEquipoSeleccionado() {
        Equipamiento equipo1 = new Arma();
        equipo1.setEquipado(false);
        Equipamiento equipo2 = new Casco();
        equipo2.setEquipado(true);

        List<Equipamiento> inventario = List.of(equipo1, equipo2);

        Equipamiento seleccionado = servicioInventario.obtenerEquipoSeleccionado(inventario, 1L);
        assertThat(seleccionado, equalTo(equipo2));

        equipo2.setEquipado(false);
        seleccionado = servicioInventario.obtenerEquipoSeleccionado(inventario, 1L);
        assertThat(seleccionado, equalTo(equipo1));
    }

    @Test
    public void validarEquipoSeleccionadoEnFiltro() {
        Equipamiento e1 = new Arma();
        e1.setEquipado(true);
        e1.setId(1L);
        Equipamiento e2 = new Casco();
        e2.setEquipado(false);
        e2.setId(2L);
        Equipamiento e3 = new Casco();
        e3.setEquipado(false);
        e3.setId(3L);

        List<Equipamiento> inventarioFiltrado = List.of(e1, e2);
        List<Equipamiento> inventarioCompleto = List.of(e1, e2, e3);

        Equipamiento seleccionado = servicioInventario.validarEquipoSeleccionadoEnFiltro(inventarioFiltrado, e2, inventarioCompleto);
        assertThat(seleccionado, equalTo(e2));

        Equipamiento seleccionadoNoFiltrado = servicioInventario.validarEquipoSeleccionadoEnFiltro(inventarioFiltrado, e3, inventarioCompleto);
        assertThat(seleccionadoNoFiltrado, equalTo(e1));

        assertNull(servicioInventario.validarEquipoSeleccionadoEnFiltro(inventarioFiltrado, null, inventarioCompleto));

        assertNull(servicioInventario.validarEquipoSeleccionadoEnFiltro(List.of(), e3, List.of()));
    }
}
