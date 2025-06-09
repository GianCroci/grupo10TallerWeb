package com.tallerwebi.dominio;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ServicioTabernaTest {

    private PersonajeTaberna personajeTabernaMock;

    private RepositorioTaberna repositorioTabernaMock;

    private ServicioTaberna servicioTaberna;

    private ServicioMercado servicioMercadoMock;

    private ServicioTaberna servicioTabernaMock;

    @BeforeEach
    public void init() {
        personajeTabernaMock= mock(PersonajeTaberna.class);
        repositorioTabernaMock = mock(RepositorioTaberna.class);
        servicioMercadoMock = mock(ServicioMercado.class); // PRIMERO mockeás esto
        servicioTaberna = new ServicioTabernaImpl(repositorioTabernaMock, servicioMercadoMock); // LUEGO lo inyectás
        servicioTabernaMock = mock(ServicioTaberna.class);

    }
    @Test
    public void queAlDar5TragosOMasAlMercaderTeApliqueElDescuento(){
        //preparacion
        when(repositorioTabernaMock.getCantidadCervezasInvitadas(personajeTabernaMock.MERCADER)).thenReturn(5);

        //ejecucion
        Boolean seObtuvo= servicioTaberna.obtenerBeneficioMercader();

        //verificacion
        assertTrue(seObtuvo);
    }

    @Test
    public void queAlDar5TragosOMasAlHerreroTeDejeMejorarLasArmas(){
        //preparacion
        when(repositorioTabernaMock.getCantidadCervezasInvitadas(PersonajeTaberna.HERRERO)).thenReturn(5);

        //ejecucion
        Boolean seObtuvo= servicioTaberna.obtenerBeneficioHerrero();

        //verificacion
        assertTrue(seObtuvo);
    }

    @Test
    public void queAlDar5TragosOMasAlGuardiaTeRegaleUnArma(){
        //preparacion
        when(repositorioTabernaMock.getCantidadCervezasInvitadas(PersonajeTaberna.GUARDIA)).thenReturn(5);

        //ejecucion
        Boolean seObtuvo= servicioTaberna.obtenerBeneficioGuardia();

        //verificacion
        assertTrue(seObtuvo);
    }



    /*
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

