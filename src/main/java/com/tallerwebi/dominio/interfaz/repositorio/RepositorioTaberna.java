package com.tallerwebi.dominio.interfaz.repositorio;

import com.tallerwebi.dominio.PersonajeTaberna;
import com.tallerwebi.dominio.entidad.Personaje;


public interface RepositorioTaberna {

    int getCantidadCervezasInvitadas(Personaje personaje, PersonajeTaberna personajeTaberna);

    void invitarCerveza(Personaje personaje, PersonajeTaberna personajeTaberna);

    boolean puedeInvitar(Personaje personaje, PersonajeTaberna personajeTaberna);
}
