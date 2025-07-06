package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidad.Arma;
import com.tallerwebi.dominio.entidad.Equipamiento;
import com.tallerwebi.dominio.entidad.Personaje;
import com.tallerwebi.dominio.interfaz.repositorio.RepositorioTaberna;
import com.tallerwebi.dominio.interfaz.servicio.ServicioTaberna;
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
    public boolean puedeInvitar(Long idPersonaje, PersonajeTaberna personajeTaberna) {
        if (!repositorioTaberna.puedeInvitar(idPersonaje, personajeTaberna)) {
            throw new IllegalArgumentException("Ya se invitó a este personaje hoy.");
        }
        return repositorioTaberna.puedeInvitar(idPersonaje, personajeTaberna);
    }
    @Override
    public void invitarCerveza(Long idPersonaje, PersonajeTaberna personajeTaberna) {
        if (!puedeInvitar(idPersonaje, personajeTaberna)) {
            throw new IllegalArgumentException("No se puede invitar a este personaje en este momento.");
        }
        repositorioTaberna.invitarCerveza(idPersonaje, personajeTaberna);
    }
    public int getCantidadCervezasInvitadas(Long idPersonaje, PersonajeTaberna personajeTaberna) {
        return repositorioTaberna.getCantidadCervezasInvitadas(idPersonaje, personajeTaberna);
    }
    public Map<PersonajeTaberna, Integer> obtenerCervezasInvitadasPorPersonaje(Long idPersonaje) {
        Map<PersonajeTaberna, Integer> cervezas = new HashMap<>();

        for (PersonajeTaberna tipo : PersonajeTaberna.values()) {
            int cantidad = getCantidadCervezasInvitadas(idPersonaje, tipo);
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
    public boolean validarBeneficioMercader(Long idPersonaje) {
        Personaje personaje = repositorioTaberna.buscarPorId(idPersonaje);
        return repositorioTaberna.getCantidadCervezasInvitadas(personaje.getId(), PersonajeTaberna.MERCADER) >= 5;

    }

    @Override
    public boolean validarBeneficioHerrero(Long idPersonaje) {
        Personaje personaje = repositorioTaberna.buscarPorId(idPersonaje);
        return repositorioTaberna.getCantidadCervezasInvitadas(personaje.getId(), PersonajeTaberna.HERRERO) >= 5;

    }

    @Override
    public boolean validarBeneficioGuardia(Long idPersonaje) {
        Personaje personaje = repositorioTaberna.buscarPorId(idPersonaje);
        return repositorioTaberna.getCantidadCervezasInvitadas(personaje.getId(), PersonajeTaberna.GUARDIA) >= 5;

    }

    //Aplicacion de beneficio
    @Override
    public void obtenerBeneficioMercader(Long idPersonaje) {

        if (validarBeneficioMercader(idPersonaje)) {
            Personaje personaje = repositorioTaberna.buscarPorId(idPersonaje);
            int oroOtorgado= 100;
            personaje.setOro(personaje.getOro() + oroOtorgado);
        } else {
            throw new IllegalStateException("No se pudo obtener el beneficio del mercader porque no se alcanzaron las cervezas invitadas");
        }
    }

    @Override
    public boolean obtenerBeneficioHerrero(Long idPersonaje) {
        if (validarBeneficioHerrero(idPersonaje)) {
            mejorarEstadisticas(idPersonaje);
            return true;
        } else {
            throw new IllegalStateException("No se pudo obtener el beneficio del herrero porque no se alcanzaron las cervezas invitadas");
        }
    }

    @Override
    public boolean obtenerBeneficioGuardia(Long idPersonaje) {
        if (validarBeneficioGuardia(idPersonaje)) {
            obtenerArmaEspecialDelGuardia(idPersonaje);
            return true;
        } else {
            throw new IllegalStateException("No se pudo obtener el beneficio del guardia porque no se alcanzaron las cervezas invitadas");
        }
    }

    private void obtenerArmaEspecialDelGuardia(Long idPersonaje) {
        Personaje personaje = repositorioTaberna.buscarPorId(idPersonaje);

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

    public void mejorarEstadisticas(Long idPersonaje) {
        Personaje personaje = repositorioTaberna.buscarPorId(idPersonaje);

        int fuerzaActual= personaje.getEstadisticas().getFuerza();
        int inteligenciaActual = personaje.getEstadisticas().getInteligencia();
        int beneficioInteligencia= inteligenciaActual += 10;
        int beneficioFuerza = fuerzaActual += 10;

        personaje.getEstadisticas().setFuerza(beneficioFuerza);
        personaje.getEstadisticas().setInteligencia(beneficioInteligencia);

    }
}





