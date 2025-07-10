package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidad.Personaje;
import com.tallerwebi.dominio.excepcion.RivalNoEncontrado;
import com.tallerwebi.dominio.interfaz.servicio.ServicioBatalla;
import com.tallerwebi.dominio.interfaz.servicio.ServicioPersonaje;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ServicioBatallaTest {

    private ServicioBatalla servicioBatalla;
    private ServicioPersonaje servicioPersonajeMock;

    @BeforeEach
    public void init() {
        servicioPersonajeMock = mock(ServicioPersonaje.class);
        servicioBatalla = new ServicioBatallaImpl(servicioPersonajeMock);
    }

    @Test
    public void queSePuedaBuscarUnRivalExistente() throws RivalNoEncontrado {
        Personaje rival = new Personaje();
        when(servicioPersonajeMock.buscarRival(1L)).thenReturn(rival);

        Personaje obtenido = servicioBatalla.buscarRival(1L);

        assertEquals(rival, obtenido);
    }

    @Test
    public void queLanceExcepcionSiNoSeEncuentraUnRival() {
        when(servicioPersonajeMock.buscarRival(1L)).thenReturn(null);

        assertThrows(RivalNoEncontrado.class, () -> {
            servicioBatalla.buscarRival(1L);
        });
    }
}
