package com.tallerwebi.dominio;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ServicioMercadoTest {

    private RepositorioMercado repositorioMercado;
    private ServicioMercado servicioMercado;
    private Mercado mercadoMock;

    @BeforeEach
    public void init() {
        repositorioMercado = mock(RepositorioMercado.class);
        servicioMercado = new ServicioMercadoImpl(repositorioMercado);
        mercadoMock = mock(Mercado.class);
    }

    @Test
    public void queSeObtengaElMercadoConProductosDesdeElRepositorio() {

        when(repositorioMercado.obtenerMercadoConProductos()).thenReturn(mercadoMock);

        Mercado resultado = servicioMercado.mostrarMercado();

        assertThat(resultado, is(mercadoMock));
        verify(repositorioMercado).obtenerMercadoConProductos();
    }

    @Test
    public void queRetorneErrorSiNoSeSeleccionaNada() {
        String mensaje = servicioMercado.procesarCompra(null);

        assertThat(mensaje, is("No seleccionaste ningún objeto"));
    }

    @Test
    public void queRetorneMensajeExitosoSiHayItemsSeleccionados() {
        List<String> items = List.of("Tunica", "Cinturon");
        String mensaje = servicioMercado.procesarCompra(items);

        assertThat(mensaje, is("¡Compra realizada con éxito! Has comprado: Tunica, Cinturon"));
    }
}
