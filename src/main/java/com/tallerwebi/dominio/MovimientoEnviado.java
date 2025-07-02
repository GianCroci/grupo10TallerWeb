package com.tallerwebi.dominio;

import java.util.List;

public class MovimientoEnviado {
    private String mensaje;
    private List<String> acciones;
    private boolean esTuTurno;



    public MovimientoEnviado(String mensaje, List<String> acciones, boolean esTuTurno) {
        this.mensaje = mensaje;
        this.acciones = acciones;
        this.esTuTurno = esTuTurno;
    }

    public MovimientoEnviado(){

    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public List<String> getAcciones() {
        return acciones;
    }

    public void setAcciones(List<String> acciones) {
        this.acciones = acciones;
    }

    public boolean isEsTuTurno() {
        return esTuTurno;
    }

    public void setEsTuTurno(boolean esTuTurno) {
        this.esTuTurno = esTuTurno;
    }
}
