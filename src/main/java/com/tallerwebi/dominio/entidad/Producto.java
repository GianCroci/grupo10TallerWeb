package com.tallerwebi.dominio.entidad;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Producto {
    @Id
    private Long id;
    private String nombre;
    private String precio;
    private Integer cantidadProducto;
    private String imagen;

    public void setId(Long id) { this.id = id; }

    public Long getId() { return id; }

    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getNombre() { return nombre; }

    public void setPrecio(String precio) { this.precio = precio; }

    public String getPrecio() { return precio; }

    public Integer getCantidadProducto() { return cantidadProducto; }

    public void setCantidadProducto(Integer cantidadProducto) { this.cantidadProducto = cantidadProducto; }

    public String getImagen() { return imagen; }

    public void setImagen(String imagen) { this.imagen = imagen; }
}
