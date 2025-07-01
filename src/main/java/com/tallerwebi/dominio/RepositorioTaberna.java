package com.tallerwebi.dominio;

import javax.persistence.Entity;


public interface RepositorioTaberna {

    int getCantidadCervezasInvitadas(Personaje personaje, PersonajeTaberna personajeTaberna);

    void invitarCerveza(Personaje personaje, PersonajeTaberna personajeTaberna);

    boolean puedeInvitar(Personaje personaje, PersonajeTaberna personajeTaberna);
}
