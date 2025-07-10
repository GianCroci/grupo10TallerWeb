package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.entidad.Enemigo;
import com.tallerwebi.dominio.entidad.Personaje;

public class BatallaDTO {

    private Long idEnemigo;
    private String nombreEnemigo;
    private Integer vidaTotalEnemigo;
    private Integer vidaActualEnemigo;
    private Integer nivelEnemigo;
    private String imagenEnemigo;
    private String ultimaAccionEnemigo;
    private String imagenFondo;
    private Long idPersonaje;
    private String nombrePersonaje;
    private Integer vidaTotalPersonaje;
    private Integer vidaActualPersonaje;
    private Integer nivelPersonaje;
    private String imagenPersonaje;
    private String ultimaAccionPersonaje;
    private String turno;
    private String estadoFinalPelea;


    public BatallaDTO(Personaje personaje, Enemigo enemigo) {
        this.idPersonaje = personaje.getId();
        this.nombrePersonaje = personaje.getNombre();
        this.vidaTotalPersonaje = personaje.getVida();
        this.vidaActualPersonaje = personaje.getVida();
        this.nivelPersonaje = personaje.getNivel();
        this.imagenPersonaje = personaje.getImagen();
        this.idEnemigo = enemigo.getId();
        this.nombreEnemigo = enemigo.getNombre();
        this.vidaTotalEnemigo = enemigo.getVida();
        this.vidaActualEnemigo = enemigo.getVida();
        this.nivelEnemigo = enemigo.getNivel();
        this.imagenEnemigo = enemigo.getImagenEnemigo();
        this.imagenFondo = enemigo.getImagenFondo();
        this.turno = "";
        this.estadoFinalPelea = "";
        this.ultimaAccionPersonaje = "";
        this.ultimaAccionEnemigo = "";
    }

    public BatallaDTO() {
    }

    public Long getIdEnemigo() { return idEnemigo; }

    public void setIdEnemigo(Long idEnemigo) { this.idEnemigo = idEnemigo; }

    public String getNombreEnemigo() { return nombreEnemigo; }

    public void setNombreEnemigo(String nombreEnemigo) { this.nombreEnemigo = nombreEnemigo; }

    public Integer getVidaTotalEnemigo() { return vidaTotalEnemigo; }

    public void setVidaTotalEnemigo(Integer vidaEnemigo) { this.vidaTotalEnemigo = vidaEnemigo; }

    public Integer getNivelEnemigo() { return nivelEnemigo; }

    public void setNivelEnemigo(Integer nivelEnemigo) { this.nivelEnemigo = nivelEnemigo; }

    public String getImagenEnemigo() { return imagenEnemigo; }

    public void setImagenEnemigo(String imagenEnemigo) { this.imagenEnemigo = imagenEnemigo; }

    public String getImagenFondo() { return imagenFondo; }

    public void setImagenFondo(String imagenFondo) { this.imagenFondo = imagenFondo; }

    public Long getIdPersonaje() { return idPersonaje; }

    public void setIdPersonaje(Long idPersonaje) { this.idPersonaje = idPersonaje; }

    public String getNombrePersonaje() { return nombrePersonaje; }

    public void setNombrePersonaje(String nombrePersonaje) { this.nombrePersonaje = nombrePersonaje; }

    public Integer getVidaTotalPersonaje() { return vidaTotalPersonaje; }

    public void setVidaTotalPersonaje(Integer vidaPersonaje) { this.vidaTotalPersonaje = vidaPersonaje; }

    public Integer getNivelPersonaje() { return nivelPersonaje; }

    public void setNivelPersonaje(Integer nivelPersonaje) { this.nivelPersonaje = nivelPersonaje; }

    public String getImagenPersonaje() { return imagenPersonaje; }

    public void setImagenPersonaje(String imagenPersonaje) { this.imagenPersonaje = imagenPersonaje; }

    public String getTurno() { return turno; }

    public void setTurno(String Turno) { this.turno = Turno; }

    public Integer getVidaActualEnemigo() { return vidaActualEnemigo; }

    public void setVidaActualEnemigo(Integer vidaActualEnemigo) { this.vidaActualEnemigo = vidaActualEnemigo; }

    public Integer getVidaActualPersonaje() { return vidaActualPersonaje; }

    public void setVidaActualPersonaje(Integer vidaActualPersonaje) { this.vidaActualPersonaje = vidaActualPersonaje; }

    public String getUltimaAccionEnemigo() { return ultimaAccionEnemigo; }

    public void setUltimaAccionEnemigo(String ultimaAccionEnemigo) { this.ultimaAccionEnemigo = ultimaAccionEnemigo; }

    public String getUltimaAccionPersonaje() { return ultimaAccionPersonaje; }

    public void setUltimaAccionPersonaje(String ultimaAccionPersonaje) { this.ultimaAccionPersonaje = ultimaAccionPersonaje; }

    public String getEstadoFinalPelea() { return estadoFinalPelea; }

    public void setEstadoFinalPelea(String estadoFinalPelea) { this.estadoFinalPelea = estadoFinalPelea; }
}