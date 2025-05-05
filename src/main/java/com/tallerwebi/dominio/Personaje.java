package com.tallerwebi.dominio;

public class Personaje {

    private String nombre;
    private String rol;
    private Integer fuerza;
    private Integer inteligencia;
    private Integer armadura;
    private Integer agilidad;

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
}
