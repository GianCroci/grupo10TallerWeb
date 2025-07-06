package com.tallerwebi.dominio;

import java.time.LocalTime;
import java.util.Map;

public interface ServicioTaberna {

    void invitarCerveza(Long idPersonaje, PersonajeTaberna personajeTaberna);

    boolean puedeInvitar(Long idPersonaje, PersonajeTaberna personajeTaberna);

    int getCantidadCervezasInvitadas(Long idPersonaje, PersonajeTaberna personajeTaberna);

    Map<PersonajeTaberna, Integer> obtenerCervezasInvitadasPorPersonaje(Long idPersonaje);

    String mostrarTaberna();

    PersonajeTaberna getPersonajePorHora(LocalTime hora);

    String obtenerVistaSegunPersonaje(PersonajeTaberna personajeTaberna);

    PersonajeTaberna obtenerPersonajeDisponible();



    boolean validarBeneficioMercader(Long idPersonaje);
    boolean validarBeneficioHerrero(Long idPersonaje);
    boolean validarBeneficioGuardia(Long idPersonaje);
    void obtenerBeneficioMercader(Long idPersonaje);
    boolean obtenerBeneficioHerrero(Long idPersonaje);

    boolean obtenerBeneficioGuardia(Long idPersonaje);

    void mejorarEstadisticas(Long idPersonaje);


}
