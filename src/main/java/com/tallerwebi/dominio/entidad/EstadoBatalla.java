package com.tallerwebi.dominio.entidad;

public class EstadoBatalla {
    private String mensaje;
    private int hpJugador;
    private int hpRival;
    private String turno;

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public int getHpJugador() {
        return hpJugador;
    }

    public void setHpJugador(int hpJugador) {
        this.hpJugador = hpJugador;
    }

    public int getHpRival() {
        return hpRival;
    }

    public void setHpRival(int hpRival) {
        this.hpRival = hpRival;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }
}

