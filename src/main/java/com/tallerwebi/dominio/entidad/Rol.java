package com.tallerwebi.dominio.entidad;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Rol {

    @Id
    private Long id;
    private String tipo;

    public Rol(Long id, String tipo) {
        this.id = id;
        this.tipo = tipo;
    }

    public Rol() {

    }

    public abstract void aplicarMejora(Arma arma);
    public abstract void aplicarMejora(Escudo escudo);
    public abstract void aplicarMejora(Casco casco);
    public abstract void aplicarMejora(Pechera pechera);
    public abstract void aplicarMejora(Pantalones pantalones);
    public abstract void aplicarMejora(Botas botas);

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getTipo() { return tipo; }

    public void setTipo(String nombre) { this.tipo = nombre; }

    public abstract void aplicarStatsBase(Personaje personaje);
}
