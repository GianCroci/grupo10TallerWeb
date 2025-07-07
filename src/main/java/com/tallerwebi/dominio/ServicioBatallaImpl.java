package com.tallerwebi.dominio;

import com.fasterxml.classmate.Annotations;
import com.tallerwebi.dominio.entidad.Enemigo;
import com.tallerwebi.dominio.entidad.Personaje;
import com.tallerwebi.dominio.excepcion.RivalNoEncontrado;
import com.tallerwebi.dominio.interfaz.AccionCombate;
import com.tallerwebi.dominio.interfaz.repositorio.RepositorioEnemigo;
import com.tallerwebi.dominio.interfaz.repositorio.RepositorioPersonaje;
import com.tallerwebi.dominio.interfaz.servicio.ServicioBatalla;
import com.tallerwebi.dominio.interfaz.servicio.ServicioPersonaje;
import com.tallerwebi.presentacion.AmigoDTO;
import com.tallerwebi.presentacion.BatallaDTO;
import com.tallerwebi.presentacion.EnemigoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service("servicioBatalla")
@Transactional
public class ServicioBatallaImpl implements ServicioBatalla {

    private RepositorioPersonaje repositorioPersonaje;
    private ServicioPersonaje servicioPersonaje;
    private RepositorioEnemigo repositorioEnemigo;
    private Random random;
    private String resultado;
    private Map<String, AccionCombate> actionMapGenerator;

    @Autowired
    public ServicioBatallaImpl(ServicioPersonaje servicioPersonaje, RepositorioEnemigo repositorioEnemigo, RepositorioPersonaje repositorioPersonaje, Random random, Map<String, AccionCombate> actionMapGenerator) {
        this.repositorioPersonaje = repositorioPersonaje;
        this.servicioPersonaje = servicioPersonaje;
        this.repositorioEnemigo = repositorioEnemigo;
        this.random = random;
        this.actionMapGenerator = actionMapGenerator;
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

        Integer probabilidadPrimerTurno = 50;
        Integer numeroAleatorio = this.random.nextInt(99);

        if(personajeObtenido.getEstadisticas().getAgilidad().equals(enemigoObtenido.getEstadisticas().getAgilidad()) ) {
            if (numeroAleatorio < probabilidadPrimerTurno){
                batallaDTO.setTurno(batallaDTO.getNombrePersonaje() + " comienza la batalla");
            }else {
                batallaDTO.setTurno(batallaDTO.getNombreEnemigo() + " comienza la batalla");
                enemigoObtenido.realizarAccion(personajeObtenido, batallaDTO);
            }
        }
        if (personajeObtenido.getEstadisticas().getAgilidad() > enemigoObtenido.getEstadisticas().getAgilidad()){
            probabilidadPrimerTurno += personajeObtenido.getEstadisticas().getAgilidad() - enemigoObtenido.getEstadisticas().getAgilidad();
            if (numeroAleatorio < probabilidadPrimerTurno){
                batallaDTO.setTurno(batallaDTO.getNombrePersonaje() + " comienza la batalla");
            }else {
                batallaDTO.setTurno(batallaDTO.getNombreEnemigo() + " comienza la batalla");
                enemigoObtenido.realizarAccion(personajeObtenido, batallaDTO);
            }
        }
        if (personajeObtenido.getEstadisticas().getAgilidad() < enemigoObtenido.getEstadisticas().getAgilidad()){
            probabilidadPrimerTurno -= enemigoObtenido.getEstadisticas().getAgilidad() - personajeObtenido.getEstadisticas().getAgilidad();
            if (numeroAleatorio < probabilidadPrimerTurno){
                batallaDTO.setTurno(batallaDTO.getNombrePersonaje() + " comienza la batalla");
            }else {
                batallaDTO.setTurno(batallaDTO.getNombreEnemigo() + " comienza la batalla");
                enemigoObtenido.realizarAccion(personajeObtenido, batallaDTO);
            }

        }

        if (batallaDTO.getVidaActualPersonaje() <= 0){
            batallaDTO.setVidaActualPersonaje(0);
            batallaDTO.setEstadoFinalPelea(personajeObtenido.getNombre() + " ha sido derrotado por su enemigo " + enemigoObtenido.getNombre());
        }

        return batallaDTO;
    }

    @Override
    public void realizarAccion(String accion, BatallaDTO batallaDTOActual) {
        Personaje personajeObtenido = repositorioPersonaje.buscarPersonaje(batallaDTOActual.getIdPersonaje());
        Enemigo enemigoObtenido = repositorioEnemigo.obtenerEnemigoPorId(batallaDTOActual.getIdEnemigo());

        AccionCombate accionARealizar = this.actionMapGenerator.get(accion);

        accionARealizar.realizarAccionJugador(personajeObtenido, enemigoObtenido, batallaDTOActual);

        if (batallaDTOActual.getVidaActualEnemigo() <= 0){
            batallaDTOActual.setVidaActualEnemigo(0);
            batallaDTOActual.setEstadoFinalPelea(personajeObtenido.getNombre() + " se levanta victorioso contra su enemigo " + enemigoObtenido.getNombre());
            personajeObtenido.setOro(personajeObtenido.getOro() + enemigoObtenido.getNivel());
            repositorioPersonaje.modificar(personajeObtenido);
        }else {
            enemigoObtenido.realizarAccion(personajeObtenido, batallaDTOActual);
        }
        if (batallaDTOActual.getVidaActualPersonaje() <= 0){
            batallaDTOActual.setVidaActualPersonaje(0);
            batallaDTOActual.setEstadoFinalPelea(personajeObtenido.getNombre() + " ha sido derrotado por su enemigo " + enemigoObtenido.getNombre());
        }
    }
}
