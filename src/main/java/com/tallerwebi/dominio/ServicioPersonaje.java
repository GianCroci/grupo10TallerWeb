package com.tallerwebi.dominio;

public interface ServicioPersonaje {

    public void setNombre(String nombre);

    public String getNombre();

    void setRol(String rol);

    String getRol();

    Integer getFuerza();

    Integer getInteligencia();

    Integer getArmadura();

    Integer getAgilidad();

    void guardarPersonaje(Personaje personaje);
}
