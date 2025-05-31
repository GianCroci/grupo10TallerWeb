package com.tallerwebi.dominio;

public interface ServicioTaberna {
    String invitarTrago(PersonajeTaberna personaje);

    String mostrarTaberna();

    int getCervezasInvitadas(PersonajeTaberna personaje);

    void resetearInvitacionesDiarias();

    String obtenerVistaSegunPersonaje(PersonajeTaberna personajeTaberna);

    PersonajeTaberna obtenerPersonajeDisponible();








}
