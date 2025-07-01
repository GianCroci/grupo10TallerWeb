package com.tallerwebi.dominio;

public class MensajeEnviado {
    private String mensaje;
    private String remitente;

    public MensajeEnviado(String mensaje, String remitente) {
        this.mensaje = mensaje;
        this.remitente = remitente;
    }

    public String getMensaje() { return mensaje; }
    public String getRemitente() { return remitente; }
}
