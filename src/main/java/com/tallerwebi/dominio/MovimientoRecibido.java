package com.tallerwebi.dominio;

public class MovimientoRecibido {

    private String jugador;
    private String accion;

    public MovimientoRecibido(String jugador, String accion) {
        this.jugador = jugador;
        this.accion = accion;

    }

    public String getJugador() {
        return jugador;
    }

    public void setJugador(String jugador) {
        this.jugador = jugador;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }


}
