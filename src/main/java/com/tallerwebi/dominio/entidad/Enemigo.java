package com.tallerwebi.dominio.entidad;

import com.tallerwebi.dominio.Estadisticas;

import javax.persistence.*;

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

    public String realizarAccion() {
        return "";
    }
}
