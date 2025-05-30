package com.tallerwebi.dominio;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public abstract class Rol {

    @Id
    private Long id;
    private String nombre;

    public abstract void aplicarMejora(Arma arma);
    public abstract void aplicarMejora(Escudo escudo);
    public abstract void aplicarMejora(Casco casco);
    public abstract void aplicarMejora(Pechera pechera);
    public abstract void aplicarMejora(Pantalones pantalones);
    public abstract void aplicarMejora(Botas botas);

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }

    public void setNombre(String nombre) { this.nombre = nombre; }
}
