package com.tallerwebi.dominio;

import javax.persistence.Entity;


public interface RepositorioTaberna {

    int getCantidadCervezasInvitadas(Long idPersonaje, PersonajeTaberna personajeTaberna);

    void invitarCerveza(Long idPersonaje, PersonajeTaberna personajeTaberna);

    boolean puedeInvitar(Long idPersonaje, PersonajeTaberna personajeTaberna);

    Personaje buscarPorId(Long idPersonaje);
}
