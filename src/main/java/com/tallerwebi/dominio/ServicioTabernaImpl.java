package com.tallerwebi.dominio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

@Service("servicioTaberna")
public class ServicioTabernaImpl implements ServicioTaberna {

    public Taberna taberna;

    private RepositorioTaberna repositorioTaberna;

    private ServicioMercado servicioMercado;


    public ServicioTabernaImpl(RepositorioTaberna repositorioTaberna, ServicioMercado servicioMercado) {

        this.taberna = new Taberna();
        this.repositorioTaberna = repositorioTaberna;
        this.servicioMercado = servicioMercado;


    }

    @Override
    public String invitarTrago(PersonajeTaberna personaje) {
        taberna.invitarTrago(personaje);
        //obtenerBeneficio();
        return "Invitaste un trago al " + personaje.name() + ". Total de cervezas invitadas: " + taberna.getCantidadCervezasInvitadas(personaje);
    }


    //devuelve el personaje correspondiente a la hora actual
    @Override
    public String mostrarTaberna() {
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

    @Override
    public boolean obtenerBeneficioMercader() {
        if (repositorioTaberna.getCantidadCervezasInvitadas(PersonajeTaberna.MERCADER) >= 5) {
            return true;
        } else {
            return false;
        }

    }

    @Override
    public boolean obtenerBeneficioHerrero() {
        if (repositorioTaberna.getCantidadCervezasInvitadas(PersonajeTaberna.HERRERO) >= 5) {
            return true;
        } else {
            return false;
        }
    }
    @Override
    public boolean obtenerBeneficioGuardia() {
        if (repositorioTaberna.getCantidadCervezasInvitadas(PersonajeTaberna.GUARDIA) >= 5) {
            return true;
        } else {
            return false;
        }
    }

        /*
    //condicion de que si el personaje es el guardia y tiene 5 tragos, le da un arma especial
    public void obtenerArmaEspecial() {

      servicioEquipamiento.darArmaEspecial();
    }

    */

}





