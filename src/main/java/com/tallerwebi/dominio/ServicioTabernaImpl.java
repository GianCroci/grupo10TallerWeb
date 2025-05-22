package com.tallerwebi.dominio;

import org.springframework.stereotype.Service;

import java.time.LocalTime;

@Service("servicioTaberna")
public class ServicioTabernaImpl implements ServicioTaberna{

    public Taberna taberna;

    public ServicioTabernaImpl() {
        this.taberna = new Taberna();
    }

    @Override
    public String invitarTrago(PersonajeTaberna personaje) {
        taberna.invitarTrago(personaje);
        return "Invitaste un trago a " + personaje.name() + ". Total de cervezas invitadas: " + taberna.getCantidadCervezasInvitadas(personaje);
    }

    @Override
    public void hacerGuardia(int horasDeGuardia) {
        PersonajeTaberna personajeActual = taberna.getPersonajePorHora(LocalTime.now());
        if (personajeActual == PersonajeTaberna.GUARDIA) {
            System.out.println("Realizaste una guardia de " + horasDeGuardia + " horas y obtuviste oro.");
        } else {
            System.out.println("No hay guardia disponible en este momento.");
        }
    }

    //devuelve el personaje correspondiente a la hora actual
    @Override
    public String  mostrarTaberna() {
        PersonajeTaberna vistaSegunPersonaje = taberna.getPersonajePorHora(LocalTime.now());
        return obtenerVistaSegunPersonaje(vistaSegunPersonaje);
    }

    //devuelve las cervezas invitadas por el personaje
    @Override
    public int getCervezasInvitadas(PersonajeTaberna personaje) {
        return taberna.getCantidadCervezasInvitadas(personaje);
    }

    @Override
    public void resetearInvitacionesDiarias() {
        taberna = new Taberna();
    }

    //devuelve la vista correspondiente a la hora actual
    @Override
    public String obtenerVistaSegunPersonaje(PersonajeTaberna personajeTaberna) {

        switch (personajeTaberna) {
            case MERCADER:
                return "mercader.png";
            case HERRERO:
                return "herrero.png";
            case GUARDIA:
                return "guardia.png";
            default:
                return "No se pudo renderizar la vista correctamente";
        }
    }

    @Override
    public PersonajeTaberna obtenerPersonajeDisponible() {
        LocalTime horaActual = LocalTime.now();
        return taberna.getPersonajePorHora(horaActual);
    }


}
