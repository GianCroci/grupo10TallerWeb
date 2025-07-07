package com.tallerwebi.dominio.entidad;

import com.tallerwebi.dominio.Estadisticas;
import com.tallerwebi.presentacion.BatallaDTO;

import javax.persistence.*;
import java.util.Random;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Enemigo {

    @Id
    private Long id;
    private String nombre;
    private String descripcion;
    private String imagenEnemigo;
    private String imagenFondo;
    @Embedded
    private Estadisticas estadisticas;
    private Integer nivel;
    private Integer vida;

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }

    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }

    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getImagenFondo() { return imagenFondo; }

    public void setImagenFondo(String imagenFondo) { this.imagenFondo = imagenFondo; }

    public String getImagenEnemigo() { return imagenEnemigo; }

    public void setImagenEnemigo(String imagenEnemigo) { this.imagenEnemigo = imagenEnemigo; }

    public Estadisticas getEstadisticas() { return estadisticas; }

    public void setEstadisticas(Estadisticas estadisticas) { this.estadisticas = estadisticas; }

    public Integer getNivel() { return nivel; }

    public void setNivel(Integer nivel) { this.nivel = nivel; }

    public Integer getVida() { return vida; }

    public void setVida(Integer vida) { this.vida = vida; }

    public void realizarAccion(Personaje personaje, BatallaDTO batallaDTO) {
        Integer porcentajeVidaPropio = Math.round((float) batallaDTO.getVidaActualEnemigo() / batallaDTO.getVidaTotalEnemigo() * 100);
        Integer porcentajeVidaJugador = Math.round((float) batallaDTO.getVidaActualPersonaje() / batallaDTO.getVidaTotalPersonaje() * 100);

        Random rand = new Random();
        Integer accionARealizar = rand.nextInt(99);
        if (porcentajeVidaPropio >= 80 || porcentajeVidaJugador <= 20) {
            golpeFuerte(personaje, batallaDTO, rand);
        }else {
            if (accionARealizar < 44) {
                golpeFuerte(personaje, batallaDTO, rand);
            }
            if (accionARealizar >= 45 && accionARealizar < 66) {
                defender(batallaDTO);
            }
            if (accionARealizar >= 66 && accionARealizar < 86) {
                esquivar(batallaDTO);
            }
            if (accionARealizar >= 86 && accionARealizar < 95) {
                golpeDebil(personaje, batallaDTO, rand);
            }
            if (accionARealizar >= 95 && accionARealizar < 100) {
                confusion(batallaDTO);
            }
        }
    }

    private void confusion(BatallaDTO batallaDTO) {
        batallaDTO.setUltimaAccionEnemigo("confusion");
        batallaDTO.setTurno(batallaDTO.getTurno() + "<br>" + this.nombre + " no esta seguro de lo que esta pasando y pierde su turno.");
    }

    private void golpeFuerte(Personaje personaje, BatallaDTO batallaDTO, Random rand) {
        Integer estadisticaDanioMasAlta = Math.max(this.estadisticas.getFuerza(), this.estadisticas.getInteligencia());
        Integer probabilidadDeEsquivarDelPersonaje = (personaje.getEstadisticas().getAgilidad() - this.estadisticas.getAgilidad()) / 2;
        Integer numeroAleatorio = rand.nextInt(99);
        Integer danioFinal = estadisticaDanioMasAlta - (personaje.getEstadisticas().getArmadura() / 2);
        if (danioFinal < 0){
            danioFinal = 0;
        }
        if (batallaDTO.getUltimaAccionPersonaje().equalsIgnoreCase("defensa")){
            danioFinal /= 2;
        }
        if (batallaDTO.getUltimaAccionPersonaje().equalsIgnoreCase("esquivar")){
            probabilidadDeEsquivarDelPersonaje *= 2;
        }
        if (numeroAleatorio > probabilidadDeEsquivarDelPersonaje){
            batallaDTO.setTurno(batallaDTO.getTurno() + "<br>" + this.nombre + " ha realizado un ataque fuerte y ha hecho " + danioFinal + " puntos de daño.");
            batallaDTO.setVidaActualPersonaje(batallaDTO.getVidaActualPersonaje()-danioFinal);
        }else {
            batallaDTO.setTurno(batallaDTO.getTurno() + "<br>" + this.nombre + " ha intentado asestar un golpe fuerte a " + personaje.getNombre() + " pero lo ha esquivado.");
        }
        batallaDTO.setUltimaAccionEnemigo("golpeFuerte");
    }

    private void defender(BatallaDTO batallaDTO) {
        batallaDTO.setUltimaAccionEnemigo("defensa");
        batallaDTO.setTurno(batallaDTO.getTurno() + "<br>" + this.nombre + " se prepara para bloquear el siguiente golpe.");
    }
    private void esquivar(BatallaDTO batallaDTO) {
        batallaDTO.setUltimaAccionEnemigo("esquivar");
        batallaDTO.setTurno(batallaDTO.getTurno() + "<br>" + this.nombre + " se prepara para esquivar el siguiente golpe.");
    }
    private void golpeDebil(Personaje personaje, BatallaDTO batallaDTO, Random rand) {
        Integer estadisticaDanioMasBaja = Math.min(this.estadisticas.getFuerza(), this.estadisticas.getInteligencia());
        Integer probabilidadDeEsquivarDelPersonaje = (personaje.getEstadisticas().getAgilidad() - this.estadisticas.getAgilidad()) / 2;
        Integer numeroAleatorio = rand.nextInt(99);
        Integer danioFinal = estadisticaDanioMasBaja - (personaje.getEstadisticas().getArmadura() / 2);
        if (danioFinal < 0){
            danioFinal = 0;
        }
        if (batallaDTO.getUltimaAccionPersonaje().equalsIgnoreCase("defensa")){
            danioFinal /= 2;
        }
        if (batallaDTO.getUltimaAccionPersonaje().equalsIgnoreCase("esquivar")){
            probabilidadDeEsquivarDelPersonaje *= 2;
        }
        if (numeroAleatorio > probabilidadDeEsquivarDelPersonaje){
            batallaDTO.setTurno(batallaDTO.getTurno() + "<br>" + this.nombre + " ha realizado un golpe debil y ha hecho " + danioFinal + " puntos de daño.");
            batallaDTO.setVidaActualPersonaje(batallaDTO.getVidaActualPersonaje()-danioFinal);
        }else {
            batallaDTO.setTurno(batallaDTO.getTurno() + "<br>" + this.nombre + " ha intentado asestar un golpe debil a " + personaje.getNombre() + " pero lo ha esquivado.");
        }
        batallaDTO.setUltimaAccionEnemigo("golpeDebil");
    }

}
