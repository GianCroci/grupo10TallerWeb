package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidad.Arma;
import com.tallerwebi.dominio.entidad.Equipamiento;
import com.tallerwebi.dominio.entidad.Personaje;
import com.tallerwebi.dominio.interfaz.repositorio.RepositorioTaberna;
import com.tallerwebi.dominio.interfaz.servicio.ServicioTaberna;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

@Service("servicioTaberna")
@Transactional
public class ServicioTabernaImpl implements ServicioTaberna {

    private RepositorioTaberna repositorioTaberna;


    public ServicioTabernaImpl(RepositorioTaberna repositorioTaberna) {

        this.repositorioTaberna = repositorioTaberna;

    }


    /*--------------------------------------------------------------------------------*/
    //METODOS QUE PEGAN CON EL REPO

    @Override
    public void invitarCerveza(Long idPersonaje, PersonajeTaberna personajeTaberna) {
        if (repositorioTaberna.cantidadInvitacionesHoy(idPersonaje) < 2) {
            repositorioTaberna.invitarCerveza(idPersonaje, personajeTaberna);
        }
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

    public int obtenerCervezasDisponibles(Long idPersonaje) {
        int maximoDiario = 2;
        int yaInvitadas = repositorioTaberna.cantidadInvitacionesHoy(idPersonaje);

        return Math.max(0, maximoDiario - yaInvitadas);
    }


    /*--------------------------------------------------------------------------------*/
    // METODOS PROPIOS DEL SERVICIO

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
    public String mostrarTaberna() {
        return "taberna.png";
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
        Estadisticas estadisticasArmaEspecial = new Estadisticas();
        estadisticasArmaEspecial.setFuerza(20);
        estadisticasArmaEspecial.setInteligencia(20);
        estadisticasArmaEspecial.setArmadura(10);
        estadisticasArmaEspecial.setAgilidad(5);

        armaEspecial.setNombre("Espada del Guardián");
        armaEspecial.setStats(estadisticasArmaEspecial);
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





