package com.tallerwebi.dominio;

import java.time.LocalTime;
import java.util.Map;

public interface ServicioTaberna {

    void invitarCerveza(Personaje personaje, PersonajeTaberna personajeTaberna);

    boolean puedeInvitar(Personaje personaje, PersonajeTaberna personajeTaberna);

    int getCantidadCervezasInvitadas(Personaje personaje, PersonajeTaberna personajeTaberna);

    Map<PersonajeTaberna, Integer> obtenerCervezasInvitadasPorPersonaje(Personaje personaje);

    String mostrarTaberna();

    PersonajeTaberna getPersonajePorHora(LocalTime hora);

    String obtenerVistaSegunPersonaje(PersonajeTaberna personajeTaberna);

    PersonajeTaberna obtenerPersonajeDisponible();

    void obtenerBeneficioMercader(Personaje personaje);

    boolean validarBeneficioMercader(Personaje personaje);
    boolean validarBeneficioHerrero(Personaje personaje);
    boolean validarBeneficioGuardia(Personaje personaje);

    boolean obtenerBeneficioHerrero(Personaje personaje);

    boolean obtenerBeneficioGuardia(Personaje personaje);

    void mejorarEstadisticas(Personaje personaje);
}
