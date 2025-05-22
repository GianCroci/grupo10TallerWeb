package com.tallerwebi.dominio;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class Taberna {

    public Map<PersonajeTaberna, Integer> cervezasInvitadas;
    private Map<PersonajeTaberna, LocalDate> ultimaInvitacion;

    public Taberna() {

        cervezasInvitadas = new HashMap<>();
        ultimaInvitacion = new HashMap<>();

        for (PersonajeTaberna personaje : PersonajeTaberna.values()) {
            cervezasInvitadas.put(personaje, 0);
            ultimaInvitacion.put(personaje, LocalDate.MIN);

        }
    }

        public boolean puedeInvitar(PersonajeTaberna personaje) {
            LocalDate hoy = LocalDate.now();
            return !ultimaInvitacion.get(personaje).isEqual(hoy);
        }


    public void invitarTrago(PersonajeTaberna personaje){
        if (puedeInvitar(personaje)) {
            int cantidad = cervezasInvitadas.get(personaje) + 1;
            cervezasInvitadas.put(personaje, cantidad);
            ultimaInvitacion.put(personaje, LocalDate.now());
            System.out.println("Invitaste un trago a " + personaje);
        } else {
            throw new IllegalArgumentException("Ya invitaste a " + personaje.name() + " hoy.");
        }
    }


    public int getCantidadCervezasInvitadas (PersonajeTaberna personaje){
            return cervezasInvitadas.get(personaje);
        }

        public PersonajeTaberna getPersonajePorHora(LocalTime hora){
            if (hora.isBefore(LocalTime.NOON)) {
                return PersonajeTaberna.MERCADER;
            } else if (hora.isBefore(LocalTime.of(19, 0))) {
                return PersonajeTaberna.HERRERO;
            } else {
                return PersonajeTaberna.GUARDIA;
            }
        }

    }

