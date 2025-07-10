package com.tallerwebi.presentacion;

public class Pedido {

    private Long idProducto;
    private Integer cantidad;
    private Long idPersonajeComprador;

    public Long getIdProducto() { return idProducto; }

    public void setIdProducto(Long idProducto) { this.idProducto = idProducto; }

    public Integer getCantidad() { return cantidad; }

    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }

    public Long getIdPersonajeComprador() { return idPersonajeComprador; }

    public void setIdPersonajeComprador(Long idPersonajeComprador) {this.idPersonajeComprador = idPersonajeComprador; }
}
