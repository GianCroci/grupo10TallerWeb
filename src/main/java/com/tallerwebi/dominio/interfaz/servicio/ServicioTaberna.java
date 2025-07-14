package com.tallerwebi.dominio.interfaz.servicio;

import com.tallerwebi.dominio.PersonajeTaberna;
import com.tallerwebi.dominio.entidad.Personaje;

import java.time.LocalTime;
import java.util.Map;

public interface ServicioTaberna {

    void invitarCerveza(Long idPersonaje, PersonajeTaberna personajeTaberna);

    int getCantidadCervezasInvitadas(Long idPersonaje, PersonajeTaberna personajeTaberna);

    Map<PersonajeTaberna, Integer> obtenerCervezasInvitadasPorPersonaje(Long idPersonaje);

    String mostrarTaberna();

    PersonajeTaberna getPersonajePorHora(LocalTime hora);

    String obtenerVistaSegunPersonaje(PersonajeTaberna personajeTaberna);

    PersonajeTaberna obtenerPersonajeDisponible();

    int obtenerCervezasDisponibles(Long idPersonaje);

    boolean validarBeneficioMercader(Long idPersonaje);
    boolean validarBeneficioHerrero(Long idPersonaje);
    boolean validarBeneficioGuardia(Long idPersonaje);
    void obtenerBeneficioMercader(Long idPersonaje);
    boolean obtenerBeneficioHerrero(Long idPersonaje);

    boolean obtenerBeneficioGuardia(Long idPersonaje);

    void mejorarEstadisticas(Long idPersonaje);


}
