package com.tallerwebi.dominio;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ServicioInventarioTest {

    private RepositorioInventario repositorioInventarioMock;
    private ServicioInventarioImpl servicio;

    @BeforeEach
    public void init() {
        repositorioInventarioMock = mock(RepositorioInventario.class);
        servicio = new ServicioInventarioImpl(repositorioInventarioMock);
    }

    @Test
    public void verInventarioYQueDevuelvaUnaListaDeEquipos() {
        List<Equipamiento> listaEquipos = servicio.mostrarEquipamiento();
        assertNotNull(listaEquipos);
        assertEquals(3, listaEquipos.size());
    }

    @Test
    public void buscarEquipamientoPorId() {
        Equipamiento equipo = servicio.buscarEquipamientoPorId(1L);
        assertNotNull(equipo);
        assertEquals("espada dorada", equipo.getNombre());
    }

    @Test
    public void equiparCambiaElEstadoDelEquipoSeleccionadoATrue() {
        boolean resultado = servicio.equipar(2L);
        assertTrue(resultado);
        Equipamiento equipoSeleccionado = servicio.buscarEquipamientoPorId(2L);
        assertTrue(equipoSeleccionado.getEquipado());
    }

    @Test
    public void cuandoSeEquipaUnNuevoEquipoSeCambiaElEstadoDelEquipadoAnteriorAFalse() {
        boolean resultado = servicio.equipar(2L);
        assertTrue(resultado);
        Equipamiento equipoNoSeleccionado = servicio.buscarEquipamientoPorId(1L);
        assertFalse(equipoNoSeleccionado.getEquipado());
    }
}
