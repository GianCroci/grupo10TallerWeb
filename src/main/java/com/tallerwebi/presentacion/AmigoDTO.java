package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.entidad.Personaje;

public class AmigoDTO {

    private Long id;
    private String nombre;
    private String codigoAmigo;
    private String imagenAmigo;

    public void setearAtributos(Personaje personajeActual) {
        this.id = personajeActual.getId();
        this.nombre = personajeActual.getNombre();
        this.codigoAmigo = personajeActual.getCodigoAmigo();
        String imagenAmigo = personajeActual.getImagen().replace(".png", "Miniatura.png");
        this.imagenAmigo = imagenAmigo;
    }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }

    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getCodigoAmigo() { return codigoAmigo; }

    public void setCodigoAmigo(String codigoAmigo) { this.codigoAmigo = codigoAmigo; }

    public String getImagenAmigo() { return imagenAmigo; }

    public void setImagenAmigo(String imagenAmigo) { this.imagenAmigo = imagenAmigo; }
}
