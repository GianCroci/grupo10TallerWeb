package com.tallerwebi.dominio;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Personaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String genero;
    @OneToOne
    private Rol rol;
    @Embedded
    private Estadisticas estadisticas;
    private String imagen;
    private Integer oro;
    @OneToMany(mappedBy = "personaje", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Equipamiento> equipamientos = new ArrayList<>();

    public void setId(Long id) { this.id = id; }

    public List<Equipamiento> getEquipamientos() { return equipamientos; }

    public void setEquipamientos(List<Equipamiento> equipamientos) { this.equipamientos = equipamientos; }


    public void aplicarEstadisticasBase() {
        getRol().aplicarStatsBase(this);
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getGenero() {
        return genero;
    }

    public Long getId(){return id;}

    public Integer getOro() { return oro; }

    public void setOro(Integer oro) { this.oro = oro; }

    public Rol getRol() { return rol; }

    public void setRol(Rol rol) { this.rol = rol; }

    public Estadisticas getEstadisticas() { return estadisticas; }

    public void setEstadisticas(Estadisticas estadisticas) { this.estadisticas = estadisticas; }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Personaje personaje = (Personaje) o;
        return Objects.equals(id, personaje.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
