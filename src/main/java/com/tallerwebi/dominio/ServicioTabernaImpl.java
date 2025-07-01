package com.tallerwebi.dominio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

@Service("servicioTaberna")
public class ServicioTabernaImpl implements ServicioTaberna {

    private RepositorioTaberna repositorioTaberna;


    public ServicioTabernaImpl(RepositorioTaberna repositorioTaberna) {

        this.repositorioTaberna = repositorioTaberna;

    }


    /*--------------------------------------------------------------------------------*/
    //METODOS QUE PEGAN CON EL REPO


    @Override
    public boolean puedeInvitar(Personaje personaje, PersonajeTaberna personajeTaberna) {
        if (!repositorioTaberna.puedeInvitar(personaje, personajeTaberna)) {
            throw new IllegalArgumentException("Ya se invitó a este personaje hoy.");
        }
        return repositorioTaberna.puedeInvitar(personaje, personajeTaberna);
    }
    @Override
    public void invitarCerveza(Personaje personaje, PersonajeTaberna personajeTaberna) {
        repositorioTaberna.invitarCerveza(personaje, personajeTaberna);
    }
    public int getCantidadCervezasInvitadas(Personaje personaje, PersonajeTaberna personajeTaberna) {
        return repositorioTaberna.getCantidadCervezasInvitadas(personaje, personajeTaberna);
    }
    public Map<PersonajeTaberna, Integer> obtenerCervezasInvitadasPorPersonaje(Personaje personaje) {
        Map<PersonajeTaberna, Integer> cervezas = new HashMap<>();

        for (PersonajeTaberna tipo : PersonajeTaberna.values()) {
            int cantidad = getCantidadCervezasInvitadas(personaje, tipo);
            cervezas.put(tipo, cantidad);
        }

        return cervezas;
    }




    /*--------------------------------------------------------------------------------*/
    // METODOS PROPIOS DEL SERVICIO

    //Primero se obtiene la hora actual

    @Override
    public PersonajeTaberna obtenerPersonajeDisponible() {
        LocalTime horaActual = LocalTime.now();
        return getPersonajePorHora(horaActual);
    }

    //Segundo se determina el personaje que corresponde a esa hora

    @Override
    public PersonajeTaberna getPersonajePorHora(LocalTime hora) {
        if (hora.isAfter(LocalTime.of(6, 0)) && hora.isBefore(LocalTime.of(12, 0))) {
            return PersonajeTaberna.MERCADER;
        } else if (hora.isAfter(LocalTime.of(12, 0)) && hora.isBefore(LocalTime.of(18, 0))) {
            return PersonajeTaberna.HERRERO;
        } else {
            return PersonajeTaberna.GUARDIA;
        }
    }

    //Tercero se devuele el string de la imagen segun el personaje

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

    //Cuarto se renderiza la vista taberna con la vista correspondiente

    @Override
    public String mostrarTaberna() {
        PersonajeTaberna vistaSegunPersonaje = getPersonajePorHora(LocalTime.now());
        return obtenerVistaSegunPersonaje(vistaSegunPersonaje);
    }

    /*--------------------------------------------------------------------------------*/
    //Validacion de condicion
    @Override
    public boolean validarBeneficioMercader(Personaje personaje) {

        if (personaje == null) {

            throw new IllegalArgumentException("El personaje no puede ser nulo");
        }

        if(getCantidadCervezasInvitadas(personaje, PersonajeTaberna.MERCADER) >= 5) {
            return true;
        } else {
            return false;
        }
    }
    @Override
    public boolean validarBeneficioHerrero(Personaje personaje) {

        if (personaje == null) {
            throw new IllegalArgumentException("El personaje no puede ser nulo");
        }

        if (repositorioTaberna.getCantidadCervezasInvitadas(personaje, PersonajeTaberna.HERRERO) >= 5) {
            return true;
        } else {
            return false;
        }
    }
    @Override
    public boolean validarBeneficioGuardia(Personaje personaje) {

        if (personaje == null) {
            throw new IllegalArgumentException("El personaje no puede ser nulo");
        }
        if (repositorioTaberna.getCantidadCervezasInvitadas(personaje, PersonajeTaberna.GUARDIA) >= 5) {
            return true;
        } else {
            return false;
        }
    }

    //Aplicacion de beneficio
    @Override
    public void obtenerBeneficioMercader(Personaje personaje) {
        if (validarBeneficioMercader(personaje)) {
            int oroOtorgado= 100;
            personaje.setOro(personaje.getOro() + oroOtorgado);
        } else {
            throw new IllegalStateException("No se pudo obtener el beneficio del mercader porque no se alcanzaron las cervezas invitadas");
        }
    }
    @Override
    public boolean obtenerBeneficioHerrero(Personaje personaje) {
        if (validarBeneficioHerrero(personaje)) {
            mejorarEstadisticas(personaje);
            return true;
        } else {
            throw new IllegalStateException("No se pudo obtener el beneficio del herrero porque no se alcanzaron las cervezas invitadas");
        }
    }
    @Override
    public boolean obtenerBeneficioGuardia(Personaje personaje) {
        if (validarBeneficioGuardia(personaje)) {
            obtenerArmaEspecialDelGuardia(personaje);
            return true;
        } else {
            throw new IllegalStateException("No se pudo obtener el beneficio del guardia porque no se alcanzaron las cervezas invitadas");
        }
    }

    private void obtenerArmaEspecialDelGuardia(Personaje personaje) {
        Equipamiento armaEspecial = new Arma();
        armaEspecial.setNombre("Espada del Guardián");
        //armaEspecial.setStats(50, 20, 0, 0);
        armaEspecial.setCostoCompra(0);
        armaEspecial.setCostoVenta(100);
        armaEspecial.setCostoMejora(20);
        armaEspecial.setNivel(1);
        armaEspecial.setEquipado(true);

        personaje.getEquipamientos().add(armaEspecial);
    }

    public void mejorarEstadisticas(Personaje personaje) {

         int fuerzaActual= personaje.getEstadisticas().getFuerza();
         int inteligenciaActual = personaje.getEstadisticas().getInteligencia();
         int beneficioInteligencia= inteligenciaActual += 10;
         int beneficioFuerza = fuerzaActual += 10;

            personaje.getEstadisticas().setFuerza(beneficioFuerza);
            personaje.getEstadisticas().setInteligencia(beneficioInteligencia);

    }




}





