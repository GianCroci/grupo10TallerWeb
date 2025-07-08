package com.tallerwebi.dominio;

public class MensajeBatalla {
   private Long idAtacante;
   private Long idDefensor;
   private int daño;
   private boolean turno;


    public MensajeBatalla(Long idAtacante,  Long idDefensor, int daño,boolean turno) {
        this.idAtacante = idAtacante;
        this.daño = daño;
        this.idDefensor = idDefensor;
        this.turno = turno;
    }

    public boolean isTurno() {
        return turno;
    }
    public void setTurno(boolean turno) {
        this.turno = turno;
    }


    public Long getIdAtacante() {
        return idAtacante;
    }

    public void setIdAtacante(Long idAtacante) {
        this.idAtacante = idAtacante;
    }

    public Long getIdDefensor() {
        return idDefensor;
    }

    public void setIdDefensor(Long idDefensor) {
        this.idDefensor = idDefensor;
    }

    public int getDaño() {
        return daño;
    }

    public void setDaño(int daño) {
        this.daño = daño;
    }
}
