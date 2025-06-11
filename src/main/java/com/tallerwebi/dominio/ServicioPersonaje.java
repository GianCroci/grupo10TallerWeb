package com.tallerwebi.dominio;

public interface ServicioPersonaje {

    public String getNombre(Personaje personaje);

    String getRol(Personaje personaje);

    Integer getFuerza(Personaje personaje);

    Integer getInteligencia(Personaje personaje);

    Integer getArmadura(Personaje personaje);

    Integer getAgilidad(Personaje personaje);

    void guardarPersonaje(Personaje personaje);

    String getGenero(Personaje personaje);

    Personaje buscarPersonaje(Long id);

    void modificar(Personaje personaje);

    Personaje buscarRival(Long idPersonaje);
}
