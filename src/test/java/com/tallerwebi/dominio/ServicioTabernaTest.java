package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidad.Personaje;
import com.tallerwebi.dominio.interfaz.repositorio.RepositorioTaberna;
import com.tallerwebi.dominio.interfaz.servicio.ServicioTaberna;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ServicioTabernaTest {

    private ServicioTaberna servicioTaberna;

    private ServicioTaberna servicioTabernaMock;

    private RepositorioTaberna repositorioTabernaMock;

    private Personaje personajeMock;

    @BeforeEach
    public void init() {
        repositorioTabernaMock = mock(RepositorioTaberna.class);
        servicioTabernaMock = mock(ServicioTaberna.class);
        servicioTaberna = new ServicioTabernaImpl(repositorioTabernaMock);
        personajeMock = mock(Personaje.class);
    }

/*-----------------------------------VALIDACION Y EJECUCION DE BENEFICIO----------------------------------------------*/
    @Test
    public void queAlDar5TragosOMasAlMercaderTeValideElBeneficio(){
        when(repositorioTabernaMock.getCantidadCervezasInvitadas(personajeMock, PersonajeTaberna.MERCADER)).thenReturn(5);

        servicioTaberna.validarBeneficioMercader(personajeMock);

        assertTrue(servicioTaberna.validarBeneficioMercader(personajeMock));
    }

    @Test
    public void queAlDar5TragosOMasAlHerreroTeValideElBeneficio(){
        when(repositorioTabernaMock.getCantidadCervezasInvitadas(personajeMock, PersonajeTaberna.HERRERO)).thenReturn(5);

        servicioTaberna.validarBeneficioHerrero(personajeMock);

        assertTrue(servicioTaberna.validarBeneficioHerrero(personajeMock));
    }

    @Test
    public void queAlDar5TragosOMasAlGuardiaTeValideElBeneficio(){
        when(repositorioTabernaMock.getCantidadCervezasInvitadas(personajeMock, PersonajeTaberna.GUARDIA)).thenReturn(5);

        servicioTaberna.validarBeneficioGuardia(personajeMock);

        assertTrue(servicioTaberna.validarBeneficioGuardia(personajeMock));
    }

    @Test
    public void queAlValidarElBeneficioDelMercaderObtengas100DeOro(){
        Personaje personajePrueba= new Personaje();
        personajePrueba.setOro(0);

        when(repositorioTabernaMock.getCantidadCervezasInvitadas(personajePrueba, PersonajeTaberna.MERCADER)).thenReturn(5);
        when(servicioTabernaMock.validarBeneficioMercader(personajePrueba)).thenReturn(true);

        int oroEsperado= 100;

        servicioTaberna.obtenerBeneficioMercader(personajePrueba);

        int oroFinal = personajePrueba.getOro();

        assertEquals(oroEsperado,oroFinal);

    }

    @Test
    public void queNoValideQueSeInvito5TragosAlMercaderYTireException(){
        when(repositorioTabernaMock.getCantidadCervezasInvitadas(personajeMock, PersonajeTaberna.MERCADER)).thenReturn(4);

        assertThrows(IllegalStateException.class, () -> {
            servicioTaberna.obtenerBeneficioMercader(personajeMock);
        });

        verify(repositorioTabernaMock).getCantidadCervezasInvitadas(personajeMock, PersonajeTaberna.MERCADER);
    }

    @Test
    public void queAlDar5TragosOMasAlHerreroTeMejoreLasEstadisticasDeInteligenciaYDeFuerza(){
        Personaje personajePrueba= new Personaje();
        personajePrueba.setEstadisticas(new Estadisticas());
        personajePrueba.getEstadisticas().setFuerza(0);
        personajePrueba.getEstadisticas().setInteligencia(0);
        int fuerzaEsperada = 10;
        int inteligenciaEsperada = 10;

        when(repositorioTabernaMock.getCantidadCervezasInvitadas(personajePrueba, PersonajeTaberna.HERRERO)).thenReturn(5);
        when(servicioTabernaMock.validarBeneficioHerrero(personajePrueba)).thenReturn(true);

        servicioTaberna.obtenerBeneficioHerrero(personajePrueba);

        assertEquals(fuerzaEsperada, personajePrueba.getEstadisticas().getFuerza());
        assertEquals(inteligenciaEsperada, personajePrueba.getEstadisticas().getInteligencia());

    }

    @Test
    public void queNoValideQueSeInvito5TragosAlHerreroYTireException(){
        when(repositorioTabernaMock.getCantidadCervezasInvitadas(personajeMock, PersonajeTaberna.HERRERO)).thenReturn(4);

        assertThrows(IllegalStateException.class, () -> {
            servicioTaberna.obtenerBeneficioHerrero(personajeMock);
        });

        verify(repositorioTabernaMock).getCantidadCervezasInvitadas(personajeMock, PersonajeTaberna.HERRERO);
    }


    @Test
    public void queAlDar5TragosOMasAlGuardiaTeRegaleUnArma(){
        Personaje personajePrueba= new Personaje();
        personajePrueba.setEquipamientos(new ArrayList<>());

        when(repositorioTabernaMock.getCantidadCervezasInvitadas(personajePrueba, PersonajeTaberna.GUARDIA)).thenReturn(5);
        when(servicioTabernaMock.validarBeneficioGuardia(personajePrueba)).thenReturn(true);

        int cantidadDeObjetosEsperada = 1;

        servicioTaberna.obtenerBeneficioGuardia(personajePrueba);

        int cantidadDeObjetosFinal = personajePrueba.getEquipamientos().size();

        assertEquals(cantidadDeObjetosEsperada,cantidadDeObjetosFinal);

    }

    @Test
    public void queAlDarMenosDe5TragosAlGuardiaTireException(){
        when(repositorioTabernaMock.getCantidadCervezasInvitadas(personajeMock, PersonajeTaberna.GUARDIA)).thenReturn(4);

        assertThrows(IllegalStateException.class, () -> {
            servicioTaberna.obtenerBeneficioGuardia(personajeMock);
        });

        verify(repositorioTabernaMock).getCantidadCervezasInvitadas(personajeMock, PersonajeTaberna.GUARDIA);
    }


}

