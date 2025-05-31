package com.tallerwebi.dominio;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalTime;

@Service("servicioTaberna")
@Transactional
public class ServicioTabernaImpl implements ServicioTaberna{

    public Taberna taberna;
    public ServicioEquipamiento servicioEquipamiento;
    public ServicioHerreria servicioHerreria;
    private RepositorioInventario repositorioInventario;
    private RepositorioPersonaje repositorioPersonaje;


    public ServicioTabernaImpl() {

        this.taberna = new Taberna();
        this.servicioEquipamiento = new ServicioEquipamientoImpl(repositorioInventario);
        this.servicioHerreria = new ServicioHerreriaImpl(repositorioInventario, repositorioPersonaje, this);
    }

    @Override
    public String invitarTrago(PersonajeTaberna personaje) {
        taberna.invitarTrago(personaje);
        obtenerBeneficio();
        return "Invitaste un trago al " + personaje.name() + ". Total de cervezas invitadas: " + taberna.getCantidadCervezasInvitadas(personaje);
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


    //condicion de que si el personaje es el guardia y tiene 5 tragos, le da un arma especial
    public void obtenerArmaEspecial() {

      servicioEquipamiento.darArmaEspecial();
    }

    public void obtenerBeneficio(){
        //aca iria la logica de que si el personaje es el herrero y tiene 5 tragos, le permite mejorar las armas
        ServicioHerreriaImpl ServicioHerreria = (ServicioHerreriaImpl) servicioHerreria;

        if (getCervezasInvitadas(PersonajeTaberna.HERRERO) >= 5) {
            boolean b = ServicioHerreria.sePuedeMejorar()==true;
        }

        if (getCervezasInvitadas(PersonajeTaberna.GUARDIA) >= 5) {
            obtenerArmaEspecial();
        }

        /*
        if (getCervezasInvitadas(PersonajeTaberna.MERCADER) >= 5) {
            servicioEquipamiento.equipar(1); // ejemplo de equipar un item, se puede cambiar el id
        }
        */
    }

}
