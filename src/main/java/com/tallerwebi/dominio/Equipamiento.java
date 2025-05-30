package com.tallerwebi.dominio;


import com.tallerwebi.dominio.excepcion.NivelDeEquipamientoMaximoException;

import javax.persistence.*;

@Entity
public abstract class Equipamiento{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    @Embedded
    private Estadisticas stats;
    @OneToOne
    private Rol rol;
    private Integer costoCompra;
    private Integer costoMejora;
    private Integer costoVenta;
    private Integer nivel;
    private Boolean equipado;

    public Equipamiento() {

    }

    public Equipamiento(String nombre, Estadisticas stats, Rol rol, Integer costoCompra, Integer costoVenta, Integer costoMejora, Integer nivel, Boolean equipado) {
        this.nombre = nombre;
        this.stats = stats;
        this.rol = rol;
        this.costoCompra = costoCompra;
        this.costoVenta = costoVenta;
        this.costoMejora = costoMejora;
        this.nivel = nivel;
        this.equipado = equipado;
    }

    public abstract void mejorar() throws NivelDeEquipamientoMaximoException;

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }

    public void setNombre(String nombre) { this.nombre = nombre; }

    public Estadisticas getStats() { return stats; }

    public void setStats(Estadisticas stats) { this.stats = stats; }

    public Rol getRol() { return rol; }

    public void setRol(Rol rol) { this.rol = rol; }

    public Integer getCostoCompra() { return costoCompra; }

    public void setCostoCompra(Integer costoCompra) { this.costoCompra = costoCompra; }

    public Integer getCostoMejora() { return costoMejora; }

    public void setCostoMejora(Integer costoMejora) { this.costoMejora = costoMejora; }

    public Integer getCostoVenta() { return costoVenta; }

    public void setCostoVenta(Integer costoVenta) { this.costoVenta = costoVenta; }

    public Integer getNivel() { return nivel; }

    public void setNivel(Integer nivel) { this.nivel = nivel; }

    public Boolean getEquipado() { return equipado; }

    public void setEquipado(Boolean equipado) { this.equipado = equipado; }
}