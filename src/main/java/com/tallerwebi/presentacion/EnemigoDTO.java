package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Estadisticas;
import com.tallerwebi.dominio.entidad.Enemigo;

public class EnemigoDTO {

    private Long id;
    private String nombre;
    private String descripcion;
    private String imagenEnemigo;
    private Estadisticas estadisticas;
    private Integer nivel;


    public void setearAtributosTablon(Enemigo enemigo) {
        this.id = enemigo.getId();
        this.nombre = enemigo.getNombre();
        this.descripcion = enemigo.getDescripcion();
        this.imagenEnemigo = enemigo.getImagenEnemigo();
        this.estadisticas = enemigo.getEstadisticas();
        this.nivel = enemigo.getNivel();
    }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }

    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }

    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getImagenEnemigo() { return imagenEnemigo; }

    public void setImagenEnemigo(String imagenEnemigo) { this.imagenEnemigo = imagenEnemigo; }

    public Estadisticas getEstadisticas() { return estadisticas; }

    public void setEstadisticas(Estadisticas estadisticas) { this.estadisticas = estadisticas; }

    public Integer getNivel() { return nivel; }

    public void setNivel(Integer nivel) { this.nivel = nivel; }


}
