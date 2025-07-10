package com.tallerwebi.dominio;

public class EstadoBatalla {
    private String mensaje;
    private int hpJugador;
    private int hpRival;
    private String turno;
    private String miNombre;
    private String nombreJugador;  // Nuevo campo
    private String nombreRival;    // Nuevo campo
    private String nombreJugadorA; // Nombre del jugador A
    private String nombreJugadorB; // Nombre del jugador B

    public EstadoBatalla(String mensaje, int hpJugador, int hpRival, String turno) {
        this.mensaje = mensaje;
        this.hpJugador = hpJugador;
        this.hpRival = hpRival;
        this.turno = turno;
    }

    public EstadoBatalla() {
    }

    // Método para configurar HP desde la perspectiva del jugador
    public void configurarPerspectiva(String nombreJugadorActual, String nombreRivalActual,
                                      int hpDelJugador, int hpDelRival) {
        this.nombreJugador = nombreJugadorActual;
        this.nombreRival = nombreRivalActual;
        this.hpJugador = hpDelJugador;
        this.hpRival = hpDelRival;
    }

    // Getters y setters existentes
    public String getMiNombre() {
        return miNombre;
    }

    public void setMiNombre(String miNombre) {
        this.miNombre = miNombre;
    }

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

    public String getNombreJugador() {
        return nombreJugador;
    }

    public void setNombreJugador(String nombreJugador) {
        this.nombreJugador = nombreJugador;
    }

    public String getNombreRival() {
        return nombreRival;
    }

    public void setNombreRival(String nombreRival) {
        this.nombreRival = nombreRival;
    }

    public String getNombreJugadorA() {
        return nombreJugadorA;
    }

    public void setNombreJugadorA(String nombreJugadorA) {
        this.nombreJugadorA = nombreJugadorA;
    }

    public String getNombreJugadorB() {
        return nombreJugadorB;
    }

    public void setNombreJugadorB(String nombreJugadorB) {
        this.nombreJugadorB = nombreJugadorB;
    }
}