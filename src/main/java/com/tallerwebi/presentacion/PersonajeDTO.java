package com.tallerwebi.presentacion;

public class PersonajeDTO {
    private String nombre;
    private String genero;
    private String imagen;
    private Long idRol;

    public String getNombre() { return nombre; }

    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getGenero() { return genero; }

    public void setGenero(String genero) { this.genero = genero; }

    public String getImagen() { return imagen; }

    public void setImagen(String imagen) { this.imagen = imagen; }

    public Long getIdRol() { return idRol; }

    public void setIdRol(Long idRol) { this.idRol = idRol; }
}
