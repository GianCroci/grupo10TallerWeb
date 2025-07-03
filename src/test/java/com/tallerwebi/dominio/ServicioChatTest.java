package com.tallerwebi.dominio;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class ServicioChatTest {

    private ServicioChat servicioChat;
    private Mensaje mensajeMock;
    private RepositorioMensaje repoMensajeMock;

    @BeforeEach
    public void init() {
        mensajeMock = mock(Mensaje.class);
        repoMensajeMock = mock(RepositorioMensaje.class);
        servicioChat = new ServicioChatImpl(repoMensajeMock);
    }

    @Test
    public void queSePuedaGuardarElMensaje(){
        //preparacion

        //verificacion
        servicioChat.guardarMensaje(mensajeMock);

        //ejecucion
        verify(repoMensajeMock, times(1)).guardar(mensajeMock);
    }

    @Test
    public void queSePuedaObtenerElHistorialDeMensajes() {
        // Preparación
        String usuario1 = "usuario1";
        String usuario2 = "usuario2";

        // Verificación
        servicioChat.obtenerHistorial(usuario1, usuario2);

        // Ejecución
        verify(repoMensajeMock, times(1)).obtenerHistorial(usuario1, usuario2);
    }

}

