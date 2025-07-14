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

/*-----------------------------------VALIDACION DE BENEFICIO----------------------------------------------*/

    @Test
    public void queAlDar5TragosOMasAlMercaderTeValideElBeneficio(){
        Personaje personaje = new Personaje();
        personaje.setId(1L);
        when(repositorioTabernaMock.buscarPorId(1L)).thenReturn(personaje);
        when(repositorioTabernaMock.getCantidadCervezasInvitadas(personaje.getId(), PersonajeTaberna.MERCADER)).thenReturn(5);

        servicioTaberna.validarBeneficioMercader(personaje.getId());

        assertTrue(servicioTaberna.validarBeneficioMercader(personaje.getId()));
    }

    @Test
    public void queAlDar5TragosOMenosAlMercaderNOValideElBeneficio(){
        Personaje personaje = new Personaje();
        personaje.setId(1L);
        when(repositorioTabernaMock.buscarPorId(1L)).thenReturn(personaje);
        when(repositorioTabernaMock.getCantidadCervezasInvitadas(personaje.getId(), PersonajeTaberna.MERCADER)).thenReturn(4);

        servicioTaberna.validarBeneficioMercader(personaje.getId());

        assertFalse(servicioTaberna.validarBeneficioMercader(personaje.getId()));
    }



    @Test
    public void queAlDar5TragosOMasAlHerreroTeValideElBeneficio(){
        Personaje personaje = new Personaje();
        personaje.setId(1L);
        when(repositorioTabernaMock.buscarPorId(1L)).thenReturn(personaje);
        when(repositorioTabernaMock.getCantidadCervezasInvitadas(personaje.getId(), PersonajeTaberna.HERRERO)).thenReturn(5);

        servicioTaberna.validarBeneficioHerrero(personaje.getId());

        assertTrue(servicioTaberna.validarBeneficioHerrero(personaje.getId()));
    }

    @Test
    public void queAlDar5TragosOMenosAlHerreroNOValideElBeneficio(){
        Personaje personaje = new Personaje();
        personaje.setId(1L);
        when(repositorioTabernaMock.buscarPorId(1L)).thenReturn(personaje);
        when(repositorioTabernaMock.getCantidadCervezasInvitadas(personaje.getId(), PersonajeTaberna.HERRERO)).thenReturn(4);

        servicioTaberna.validarBeneficioHerrero(personaje.getId());

        assertFalse(servicioTaberna.validarBeneficioHerrero(personaje.getId()));
    }



    @Test
    public void queAlDar5TragosOMasAlGuardiaTeValideElBeneficio(){
        Personaje personaje = new Personaje();
        personaje.setId(1L);
        when(repositorioTabernaMock.buscarPorId(1L)).thenReturn(personaje);
        when(repositorioTabernaMock.getCantidadCervezasInvitadas(personaje.getId(), PersonajeTaberna.GUARDIA)).thenReturn(5);

        servicioTaberna.validarBeneficioGuardia(personaje.getId());

        assertTrue(servicioTaberna.validarBeneficioGuardia(personaje.getId()));
    }

    @Test
    public void queAlDar5TragosOMenosAlGuardiaNOValideElBeneficio(){
        Personaje personaje = new Personaje();
        personaje.setId(1L);
        when(repositorioTabernaMock.buscarPorId(1L)).thenReturn(personaje);
        when(repositorioTabernaMock.getCantidadCervezasInvitadas(personaje.getId(), PersonajeTaberna.GUARDIA)).thenReturn(4);

        servicioTaberna.validarBeneficioGuardia(personaje.getId());

        assertFalse(servicioTaberna.validarBeneficioGuardia(personaje.getId()));
    }



    /*-----------------------------------EJECUCION DE BENEFICIO----------------------------------------------*/


    @Test
    public void queAlValidarElBeneficioDelMercaderObtengas100DeOro(){
        Personaje personaje= new Personaje();
        personaje.setId(1L);
        personaje.setOro(0);
        when(repositorioTabernaMock.buscarPorId(1L)).thenReturn(personaje);
        when(repositorioTabernaMock.getCantidadCervezasInvitadas(personaje.getId(), PersonajeTaberna.MERCADER)).thenReturn(5);
        when(servicioTabernaMock.validarBeneficioMercader(personaje.getId())).thenReturn(true);

        int oroEsperado= 100;

        servicioTaberna.obtenerBeneficioMercader(personaje.getId());

        int oroFinal = personaje.getOro();

        assertEquals(oroEsperado,oroFinal);

    }


    @Test
    public void queNoValideQueSeInvito5TragosAlMercaderYTireException(){
        Personaje personaje= new Personaje();
        personaje.setId(1L);
        when(repositorioTabernaMock.buscarPorId(1L)).thenReturn(personaje);
        when(repositorioTabernaMock.getCantidadCervezasInvitadas(personaje.getId(), PersonajeTaberna.MERCADER)).thenReturn(4);

        assertThrows(IllegalStateException.class, () -> {
            servicioTaberna.obtenerBeneficioMercader(personaje.getId());
        });
    }


    @Test
    public void queAlDar5TragosOMasAlHerreroTeMejoreLasEstadisticasDeInteligenciaYDeFuerza(){
        Personaje personaje= new Personaje();
        personaje.setId(1L);
        personaje.setEstadisticas(new Estadisticas());
        personaje.getEstadisticas().setFuerza(0);
        personaje.getEstadisticas().setInteligencia(0);
        int fuerzaEsperada = 10;
        int inteligenciaEsperada = 10;

        when(repositorioTabernaMock.buscarPorId(1L)).thenReturn(personaje);
        when(repositorioTabernaMock.getCantidadCervezasInvitadas(personaje.getId(), PersonajeTaberna.HERRERO)).thenReturn(5);
        when(servicioTabernaMock.validarBeneficioHerrero(personaje.getId())).thenReturn(true);

        servicioTaberna.obtenerBeneficioHerrero(personaje.getId());

        assertEquals(fuerzaEsperada, personaje.getEstadisticas().getFuerza());
        assertEquals(inteligenciaEsperada, personaje.getEstadisticas().getInteligencia());

    }

    @Test
    public void queNoValideQueSeInvito5TragosAlHerreroYTireException(){
        Personaje personaje= new Personaje();
        personaje.setId(1L);
        when(repositorioTabernaMock.buscarPorId(1L)).thenReturn(personaje);
        when(repositorioTabernaMock.getCantidadCervezasInvitadas(personaje.getId(), PersonajeTaberna.HERRERO)).thenReturn(4);

        assertThrows(IllegalStateException.class, () -> {
            servicioTaberna.obtenerBeneficioHerrero(personaje.getId());
        });
    }

    @Test
    public void queAlDar5TragosOMasAlGuardiaTeRegaleUnArma(){
        Personaje personaje= new Personaje();
        personaje.setId(1L);
        personaje.setEquipamientos(new ArrayList<>());

        when(repositorioTabernaMock.buscarPorId(1L)).thenReturn(personaje);
        when(repositorioTabernaMock.getCantidadCervezasInvitadas(personaje.getId(), PersonajeTaberna.GUARDIA)).thenReturn(5);
        when(servicioTabernaMock.validarBeneficioGuardia(personaje.getId())).thenReturn(true);

        int cantidadDeObjetosEsperada = 1;

        servicioTaberna.obtenerBeneficioGuardia(personaje.getId());

        int cantidadDeObjetosFinal = personaje.getEquipamientos().size();

        assertEquals(cantidadDeObjetosEsperada,cantidadDeObjetosFinal);

    }

    @Test
    public void queNoValideQueSeInvito5TragosAlGuardiaYTireException(){
        Personaje personaje= new Personaje();
        personaje.setId(1L);
        when(repositorioTabernaMock.buscarPorId(1L)).thenReturn(personaje);
        when(repositorioTabernaMock.getCantidadCervezasInvitadas(personaje.getId(), PersonajeTaberna.GUARDIA)).thenReturn(4);

        assertThrows(IllegalStateException.class, () -> {
            servicioTaberna.obtenerBeneficioGuardia(personaje.getId());
        });
    }

    @Test
    public void queCalculeCorrectamenteLaCantidadDeCervezasDisponibles() {
        Long idPersonaje = 1L;

        when(repositorioTabernaMock.cantidadInvitacionesHoy(idPersonaje)).thenReturn(1);

        int cervezasDisponibles = servicioTaberna.obtenerCervezasDisponibles(idPersonaje);

        assertEquals(1, cervezasDisponibles);
    }

    @Test
    public void queNoPermitaCervezasDisponiblesSiYaSeInvitoElMaximo() {
        Long idPersonaje = 1L;

        when(repositorioTabernaMock.cantidadInvitacionesHoy(idPersonaje)).thenReturn(3);

        int cervezasDisponibles = servicioTaberna.obtenerCervezasDisponibles(idPersonaje);

        assertEquals(0, cervezasDisponibles);
    }

}

