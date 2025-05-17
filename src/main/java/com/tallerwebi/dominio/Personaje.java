package com.tallerwebi.dominio;

import org.springframework.stereotype.Component;

@Component
public class Personaje {

    private String nombre;
    private String genero;
    private String rol;
    private Integer fuerza;
    private Integer inteligencia;
    private Integer armadura;
    private Integer agilidad;
    public String imagen;

    public void setFuerza(Integer fuerza) {
        this.fuerza = fuerza;
    }

    public void setInteligencia(Integer inteligencia) {
        this.inteligencia = inteligencia;
    }

    public void setArmadura(Integer armadura) {
        this.armadura = armadura;
    }

    public void setAgilidad(Integer agilidad) {
        this.agilidad = agilidad;
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

    public void setRol(String rol) {
        switch (rol) {
            case "Luchador":
                this.rol = "Luchador";
                this.fuerza = 100;
                this.inteligencia = 40;
                this.armadura = 80;
                this.agilidad = 60;
                break;
            case "Mago":
                this.rol = "Mago";
                this.fuerza = 30;
                this.inteligencia = 100;
                this.armadura = 20;
                this.agilidad = 40;
                break;
            case "Bandido":
                this.rol = "Bandido";
                this.fuerza = 50;
                this.inteligencia = 70;
                this.armadura = 30;
                this.agilidad = 100;
                break;
        }
    }

    public String getRol() {
        return rol;
    }

    public Integer getFuerza() {
        return fuerza;
    }

    public Integer getInteligencia() {
        return inteligencia;
    }

    public Integer getArmadura() {
        return armadura;
    }

    public Integer getAgilidad() {
        return agilidad;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getGenero() {
        return genero;
    }
}
