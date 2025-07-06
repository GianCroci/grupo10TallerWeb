package com.tallerwebi.dominio;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

@Entity
public class Taberna {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "idPersonaje")
    private Personaje personaje;
    @Enumerated(EnumType.STRING)
    @Column(name = "personaje_taberna", nullable = false)
    private PersonajeTaberna personajeTaberna;

    private int cervezasInvitadas;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Personaje getPersonaje() {
        return personaje;
    }

    public void setPersonaje(Personaje personaje) {
        this.personaje = personaje;
    }

    public PersonajeTaberna getPersonajeTaberna() {
        return personajeTaberna;
    }

    public void setPersonajeTaberna(PersonajeTaberna personajeTaberna) {
        this.personajeTaberna = personajeTaberna;
    }

    public int getCervezasInvitadas() {
        return cervezasInvitadas;
    }

    public void setCervezasInvitadas(int cervezasInvitadas) {
        this.cervezasInvitadas = cervezasInvitadas;
    }

    public LocalDate getUltimaInvitacion() {
        return ultimaInvitacion;
    }

    public void setUltimaInvitacion(LocalDate ultimaInvitacion) {
        this.ultimaInvitacion = ultimaInvitacion;
    }

    private LocalDate ultimaInvitacion;


}