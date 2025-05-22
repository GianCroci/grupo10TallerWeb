package com.tallerwebi.dominio;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Equipamiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nombre;
    private Double costoCompra;
    private Double costoMejora;
    private Double costoVenta;
    private Integer fuerza;
    private Integer inteligencia;
    private Integer armadura;
    private Integer agilidad;
    private Boolean equipado;

    public Equipamiento(String nombre, Double costoCompra,Double costoMejora,Double costoVenta, Integer fuerza,Integer inteligencia,Integer armadura,Integer agilidad,Boolean equipado) {
        this.nombre = nombre;
        this.costoCompra = costoCompra;
        this.costoMejora = costoMejora;
        this.costoVenta = costoVenta;
        this.fuerza = fuerza;
        this.inteligencia = inteligencia;
        this.armadura = armadura;
        this.agilidad = agilidad;
        this.equipado = equipado;
    }

    public Equipamiento() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getCostoCompra() {
        return costoCompra;
    }

    public void setCostoCompra(Double costoCompra) {
        this.costoCompra = costoCompra;
    }

    public Double getCostoMejora() {
        return costoMejora;
    }

    public void setCostoMejora(Double costoMejora) {
        this.costoMejora = costoMejora;
    }

    public Double getCostoVenta() {
        return costoVenta;
    }

    public void setCostoVenta(Double costoVenta) {
        this.costoVenta = costoVenta;
    }

    public Integer getFuerza() {
        return fuerza;
    }

    public void setFuerza(Integer fuerza) {
        this.fuerza = fuerza;
    }

    public Integer getInteligencia() {
        return inteligencia;
    }

    public void setInteligencia(Integer inteligencia) {
        this.inteligencia = inteligencia;
    }

    public Integer getArmadura() {
        return armadura;
    }

    public void setArmadura(Integer armadura) {
        this.armadura = armadura;
    }

    public Integer getAgilidad() {
        return agilidad;
    }

    public void setAgilidad(Integer agilidad) {
        this.agilidad = agilidad;
    }

    public Boolean getEquipado() {
        return equipado;
    }

    public void setEquipado(Boolean equipado) {
        this.equipado = equipado;
    }
}