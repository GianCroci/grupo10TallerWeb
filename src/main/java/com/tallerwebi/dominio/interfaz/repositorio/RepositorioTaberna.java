package com.tallerwebi.dominio.interfaz.repositorio;

import com.tallerwebi.dominio.PersonajeTaberna;
import com.tallerwebi.dominio.entidad.Personaje;


public interface RepositorioTaberna {

    int getCantidadCervezasInvitadas(Long idPersonaje, PersonajeTaberna personajeTaberna);

    void invitarCerveza(Long idPersonaje, PersonajeTaberna personajeTaberna);

    Personaje buscarPorId(Long idPersonaje);

    int cantidadInvitacionesHoy(Long idPersonaje);
}
