package com.tallerwebi.dominio;

public class EstadoBatalla {
    private String mensaje;
    private int hpJugador;
    private int hpRival;
    private String turno; // nombre del personaje que tiene el turno, o "FIN"

    public EstadoBatalla(String mensaje, int hpJugador, int hpRival, String turno) {
        this.mensaje = mensaje;
        this.hpJugador = hpJugador;
        this.hpRival = hpRival;
        this.turno = turno;
    }

    // Getters y setters
}

