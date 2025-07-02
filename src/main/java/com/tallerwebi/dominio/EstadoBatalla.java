package com.tallerwebi.dominio;

public class EstadoBatalla {

    private String jugador1;
    private String jugador2;
    public String turnoActual;

    public EstadoBatalla(String jugador1, String jugador2) {
        this.jugador1 = jugador1;
        this.jugador2 = jugador2;
        this.turnoActual = jugador1;
    }

    public String getOponente(String jugador) {
        return jugador.equals(jugador1) ? jugador2 : jugador1;
    }

    public void cambiarTurno() {
        this.turnoActual = getOponente(turnoActual);
    }

    public boolean esTurnoDe(String jugador) {
        return jugador.equals(turnoActual);
    }

    // Getters y setters
}
