package com.tallerwebi.dominio;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.any;

public class ServicioMercadoTest {

    private RepositorioMercado repositorioMercado;
    private RepositorioInventario repositorioInventario;
    private RepositorioPersonaje repositorioPersonaje;
    private ServicioMercadoImpl servicioMercado;

    @BeforeEach
    public void init() {
        repositorioMercado = mock(RepositorioMercado.class);
        repositorioInventario = mock(RepositorioInventario.class);
        repositorioPersonaje = mock(RepositorioPersonaje.class);
        servicioMercado = new ServicioMercadoImpl(repositorioMercado, repositorioInventario, repositorioPersonaje);
    }

    @Test
    public void queSeObtengaElMercadoDesdeElRepositorio() {
        Mercado mercadoMock = mock(Mercado.class);
        when(repositorioMercado.obtenerMercadoConProductos()).thenReturn(mercadoMock);

        Mercado resultado = servicioMercado.mostrarMercado();

        assertThat(resultado, is(mercadoMock));
        verify(repositorioMercado).obtenerMercadoConProductos();
    }

    @Test
    public void queDevuelvaErrorSiNoSeleccionaNada() {
        String mensaje = servicioMercado.procesarCompra(null, 1L);
        assertThat(mensaje, is("No seleccionaste ningún objeto"));

        mensaje = servicioMercado.procesarCompra(new ArrayList<>(), 1L);
        assertThat(mensaje, is("No seleccionaste ningún objeto"));
    }

    @Test
    public void queDevuelvaErrorSiNoTieneOroSuficiente() {

        Equipamiento item = mock(Equipamiento.class);
        when(item.getNombre()).thenReturn("Espada");
        when(item.getCostoCompra()).thenReturn(100);

        List<Equipamiento> productos = List.of(item);
        Mercado mercado = mock(Mercado.class);
        when(mercado.getProductos()).thenReturn(productos);
        when(repositorioMercado.obtenerMercadoConProductos()).thenReturn(mercado);

        Personaje personaje = mock(Personaje.class);
        when(personaje.getOro()).thenReturn(50);
        when(repositorioPersonaje.buscarPersonaje(1L)).thenReturn(personaje);

        String mensaje = servicioMercado.procesarCompra(List.of("Espada"), 1L);

        assertThat(mensaje, is("No tienes suficiente oro para comprar los objetos seleccionados."));
    }

    @Test
    public void queObtengaOroDelPersonajeDesdeElRepositorio() {
        Long idPersonaje = 1L;
        when(repositorioPersonaje.buscarOroPersonaje(idPersonaje)).thenReturn(150);

        Integer oro = servicioMercado.obtenerOroDelPersonaje(idPersonaje);

        assertThat(oro, is(150));
        verify(repositorioPersonaje).buscarOroPersonaje(idPersonaje);
    }


    @Test
    public void queRealiceCompraConExitoSiTieneOroSuficiente() {
        Pechera equipamientoMock = mock(Pechera.class);
        when(equipamientoMock.getNombre()).thenReturn("Espada");
        when(equipamientoMock.getCostoCompra()).thenReturn(50);
        when(equipamientoMock.getStats()).thenReturn(new Estadisticas());
        when(equipamientoMock.getRol()).thenReturn(null);
        when(equipamientoMock.getCostoMejora()).thenReturn(10);
        when(equipamientoMock.getCostoVenta()).thenReturn(20);
        when(equipamientoMock.getNivel()).thenReturn(0);
        when(equipamientoMock.getImagen()).thenReturn("espada.png");

        List<Equipamiento> productos = List.of(equipamientoMock);
        Mercado mercadoMock = mock(Mercado.class);
        when(mercadoMock.getProductos()).thenReturn(productos);
        when(repositorioMercado.obtenerMercadoConProductos()).thenReturn(mercadoMock);

        Personaje personajeMock = mock(Personaje.class);
        when(personajeMock.getOro()).thenReturn(100);
        doNothing().when(personajeMock).setOro(anyInt());
        when(repositorioPersonaje.buscarPersonaje(1L)).thenReturn(personajeMock);

        String mensaje = servicioMercado.procesarCompra(List.of("Espada"), 1L);

        assertThat(mensaje, containsString("¡Compra realizada con éxito!"));
        verify(personajeMock).setOro(50);
        verify(repositorioPersonaje).modificar(personajeMock);
        verify(repositorioInventario).agregarEquipamiento(any(Equipamiento.class));
    }


}
