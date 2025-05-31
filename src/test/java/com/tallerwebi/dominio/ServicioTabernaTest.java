package com.tallerwebi.dominio;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ServicioTabernaTest {

    private ServicioTaberna servicioTabernaMock;

    private ServicioHerreria servicioHerreria;

    private RepositorioInventario repositorioInventarioMock;

    private ServicioEquipamiento servicioEquipamientoMock;

    private RepositorioPersonaje repositorioPersonaje;

    private Taberna tabernaMock;


    @BeforeEach
    public void init() {
        servicioTabernaMock = mock(ServicioTaberna.class);
        repositorioInventarioMock = mock(RepositorioInventario.class);
        servicioHerreria = new ServicioHerreriaImpl(repositorioInventarioMock, repositorioPersonaje, servicioTabernaMock);
        servicioEquipamientoMock = mock(ServicioEquipamiento.class);
        tabernaMock = mock(Taberna.class);

    }


    /*
    @Test
    public void queAlDar5TragosOMasAlMercaderTeHagaUnDescuentoDel20PorcientoAlComprar() {
        // Aquí deberías implementar la lógica de prueba para verificar el descuento del 20%
        // al comprar 5 tragos o más al herrero.
        // Por ejemplo, podrías simular una compra y verificar que el precio final sea correcto.
        when(servicioTaberna.getCervezasInvitadas(PersonajeTaberna.MERCADER)).thenReturn(5);

    }
    */


    @Test
    public void queAlDar5TragosOMasAlHerreroTeDejeMejorarLasArmas(){

        // Simulamos que el herrero ha recibido 5 tragos
        when(servicioTabernaMock.getCervezasInvitadas(PersonajeTaberna.HERRERO)).thenReturn(5);


        boolean sePuedeMejorar = servicioHerreria.sePuedeMejorar();

        // Assert
        assertTrue(sePuedeMejorar);
        verify(servicioTabernaMock).getCervezasInvitadas(PersonajeTaberna.HERRERO);
    }

    @Test
    public void queAlDarMenosDe5TragosAlHerreroNoDejeMejorarLasArmas(){

        // Simulamos que el herrero ha recibido 4 tragos
        when(servicioTabernaMock.getCervezasInvitadas(PersonajeTaberna.HERRERO)).thenReturn(4);


        assertThrows(IllegalArgumentException.class, () -> {
           servicioHerreria.sePuedeMejorar();
        });
        verify(servicioTabernaMock).getCervezasInvitadas(PersonajeTaberna.HERRERO);
    }

    /*
    @Test
    public void queAlDarAlMenos5TragosAlGuardiaTeDeUnArmaEspecial() {

            // Simulamos que el guardia recibió 5 tragos
            when(tabernaMock.getCantidadCervezasInvitadas(PersonajeTaberna.GUARDIA)).thenReturn(5);

             ServicioTabernaImpl servicioTaberna = new ServicioTabernaImpl();

            servicioTaberna.obtenerBeneficio();

            verify(servicioEquipamientoMock).darArmaEspecial();
            verify(tabernaMock).getCantidadCervezasInvitadas(PersonajeTaberna.GUARDIA);
        }


     */
    }

