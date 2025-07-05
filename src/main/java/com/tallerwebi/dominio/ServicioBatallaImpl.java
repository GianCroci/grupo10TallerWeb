package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidad.Enemigo;
import com.tallerwebi.dominio.entidad.Personaje;
import com.tallerwebi.dominio.excepcion.RivalNoEncontrado;
import com.tallerwebi.dominio.interfaz.repositorio.RepositorioEnemigo;
import com.tallerwebi.dominio.interfaz.repositorio.RepositorioPersonaje;
import com.tallerwebi.dominio.interfaz.servicio.ServicioBatalla;
import com.tallerwebi.dominio.interfaz.servicio.ServicioPersonaje;
import com.tallerwebi.presentacion.AmigoDTO;
import com.tallerwebi.presentacion.BatallaDTO;
import com.tallerwebi.presentacion.EnemigoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

@Service("servicioBatalla")
@Transactional
public class ServicioBatallaImpl implements ServicioBatalla {

    private RepositorioPersonaje repositorioPersonaje;
    private ServicioPersonaje servicioPersonaje;
    private RepositorioEnemigo repositorioEnemigo;
    private String resultado;

    @Autowired
    public ServicioBatallaImpl(ServicioPersonaje servicioPersonaje, RepositorioEnemigo repositorioEnemigo, RepositorioPersonaje repositorioPersonaje) {
        this.repositorioPersonaje = repositorioPersonaje;
        this.servicioPersonaje = servicioPersonaje;
        this.repositorioEnemigo = repositorioEnemigo;
    }

    @Override
    public Personaje buscarRival(Long idPersonaje) throws RivalNoEncontrado {
        Personaje personaje = servicioPersonaje.buscarRival(idPersonaje);
        if (personaje == null) {
            throw new RivalNoEncontrado("No se encontro un rival para la batalla");
        }
        return personaje;
    }

    @Override
    public void atacarRival(Personaje personaje, Personaje rival) {

        Integer puntosPersonaje = personaje.getEstadisticas().getFuerza() + personaje.getEstadisticas().getArmadura() + personaje.getEstadisticas().getAgilidad() + personaje.getEstadisticas().getInteligencia();
        Integer puntosRival = rival.getEstadisticas().getFuerza() + rival.getEstadisticas().getArmadura() + rival.getEstadisticas().getAgilidad() + rival.getEstadisticas().getInteligencia();

        if (puntosPersonaje > puntosRival) {
            this.resultado = "Victoria";
        }
        else if(puntosPersonaje.equals(puntosRival)) {
            resultado = "Empate";
        }
        else{
            resultado = "Derrota";
        }

    }

    @Override
    public String getResultado() {
        return resultado;
    }

    @Override
    public List<EnemigoDTO> buscarEnemigosParaTablon() {
        List<Enemigo> enemigos = repositorioEnemigo.obtenerEnemigos();

        List<EnemigoDTO> enemigosDTO = new ArrayList<>();
        for (Enemigo enemigoActual : enemigos) {
            EnemigoDTO enemigoDTO = new EnemigoDTO();
            enemigoDTO.setearAtributosTablon(enemigoActual);
            enemigosDTO.add(enemigoDTO);
        }

        enemigosDTO.sort(Comparator.comparing(EnemigoDTO::getNivel));
        return enemigosDTO;
    }

    @Override
    public BatallaDTO comenzarBatalla(Long idPersonaje, Long idEnemigo) {
        Personaje personajeObtenido = repositorioPersonaje.buscarPersonaje(idPersonaje);
        Enemigo enemigoObtenido = repositorioEnemigo.obtenerEnemigoPorId(idEnemigo);
        BatallaDTO batallaDTO = new BatallaDTO(personajeObtenido, enemigoObtenido);

        Integer probabilidadPrimerTurnoBase = 50;
        Integer numeroAleatorio = (int)(Math.random() * 101);

        if(personajeObtenido.getEstadisticas().getAgilidad().equals(enemigoObtenido.getEstadisticas().getAgilidad()) ) {
            if (numeroAleatorio < probabilidadPrimerTurnoBase){
                batallaDTO.setPrimerTurno(batallaDTO.getNombrePersonaje() + " comienza la batalla");
            }else {
                batallaDTO.setPrimerTurno(batallaDTO.getNombreEnemigo() + " comienza la batalla y realiza la accion ");
            }
        }

        if (personajeObtenido.getEstadisticas().getAgilidad() > enemigoObtenido.getEstadisticas().getAgilidad()){
            probabilidadPrimerTurnoBase += personajeObtenido.getEstadisticas().getAgilidad() - enemigoObtenido.getEstadisticas().getAgilidad();
            if (numeroAleatorio < probabilidadPrimerTurnoBase){
                batallaDTO.setPrimerTurno(batallaDTO.getNombrePersonaje() + "comienza la batalla");
            }else {
                batallaDTO.setPrimerTurno(batallaDTO.getNombreEnemigo() + " comienza la batalla y realiza la accion ");
            }
        }else {
            probabilidadPrimerTurnoBase -= enemigoObtenido.getEstadisticas().getAgilidad() - personajeObtenido.getEstadisticas().getAgilidad();
            if (numeroAleatorio < probabilidadPrimerTurnoBase){
                batallaDTO.setPrimerTurno(batallaDTO.getNombrePersonaje() + "comienza la batalla");
            }else {
                batallaDTO.setPrimerTurno(batallaDTO.getNombreEnemigo() + " comienza la batalla y realiza la accion ");
            }

        }

        return batallaDTO;
    }
}
