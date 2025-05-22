package com.tallerwebi.dominio;

public interface ServicioTaberna {
    String invitarTrago(PersonajeTaberna personaje);

    void hacerGuardia(int horasDeGuardia);

    String mostrarTaberna();

    int getCervezasInvitadas(PersonajeTaberna personaje);

    void resetearInvitacionesDiarias();

    String obtenerVistaSegunPersonaje(PersonajeTaberna personajeTaberna);

    PersonajeTaberna obtenerPersonajeDisponible();








}
