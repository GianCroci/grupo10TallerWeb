package com.tallerwebi.dominio;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ServicioTabernaTest {

    private PersonajeTaberna personajeTabernaMock;

    private RepositorioTaberna repositorioTabernaMock;

    private ServicioTaberna servicioTaberna;

    private ServicioMercado servicioMercadoMock;

    private ServicioTaberna servicioTabernaMock;

    private RepositorioPersonaje repositorioPersonajeMock;

    private Personaje personajeMock;

    private Equipamiento equipamientoMock;

    @BeforeEach
    public void init() {
        personajeTabernaMock= mock(PersonajeTaberna.class);
        repositorioTabernaMock = mock(RepositorioTaberna.class);
        servicioMercadoMock = mock(ServicioMercado.class);
        servicioTaberna = new ServicioTabernaImpl(repositorioTabernaMock, servicioMercadoMock);
        servicioTabernaMock = mock(ServicioTaberna.class);
        repositorioPersonajeMock = mock(RepositorioPersonaje.class);
        personajeMock = mock(Personaje.class);
        equipamientoMock = mock(Equipamiento.class);

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


    @Test
    public void queAlAplicarElBeneficioHerreroTeMejoreLasEstadisticasDelArma(){
        //preparacion
        when(servicioTabernaMock.obtenerBeneficioHerrero()).thenReturn(true);
        when(repositorioPersonajeMock.buscarPersonaje(1L)).thenReturn(personajeMock);
        when(personajeMock.getEquipamientos()).thenReturn((List<Equipamiento>) equipamientoMock);
        when(equipamientoMock.getArmaEquipada()).thenReturn(armaEquipadaMock);
        when(armadaEquipadaMock.getStats()).thenReturn(statsMock);
        when(statsMock.getFuerza()).thenReturn(0);

        //ejecucion
        Integer fuerzaEsperada = 10;
        Integer inteligenciaEsperada = 10;

        servicioTaberna.mejorarArmaEquipada(armaEquipadaMock);

        //verificacion
        assertEquals(fuerzaEsperada, armaEquipadaMock.getStats().getFuerza());
        assertEquals(inteligenciaEsperada, armaEquipadaMock.getStats().getInteligencia());
        verify(servicioTaberna, times(1)).obtenerBeneficioHerrero();
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

