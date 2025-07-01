package com.tallerwebi.dominio;

public class MensajeRecibido {
    private String mensaje;
    private String destinatario;
    private String remitente;

    public MensajeRecibido(String mensaje, String destinatario, String remitente) {
        this.mensaje = mensaje;
        this.destinatario = destinatario;
        this.remitente = remitente;
    }

    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }

    public String getDestinatario() { return destinatario; }
    public void setDestinatario(String destinatario) { this.destinatario = destinatario; }

    public String getRemitente() { return remitente; }
    public void setRemitente(String remitente) { this.remitente = remitente; }
}
