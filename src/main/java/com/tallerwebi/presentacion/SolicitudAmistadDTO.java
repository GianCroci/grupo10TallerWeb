package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.entidad.SolicitudAmistad;

public class SolicitudAmistadDTO {

    private Long idSolicitud;
    private String nombreRemitente;
    private String nombreDestinatario;

    public void setearAtributosSolicitudesRecibidas(SolicitudAmistad solicitudActual) {
        this.idSolicitud = solicitudActual.getId();
        this.nombreRemitente = solicitudActual.getRemitente().getNombre();
    }

    public void setearAtributosSolicitudesEnviadas(SolicitudAmistad solicitudActual) {
        this.nombreDestinatario = solicitudActual.getDestinatario().getNombre();
    }

    public Long getIdSolicitud() { return idSolicitud; }

    public void setIdSolicitud(Long idSolicitud) { this.idSolicitud = idSolicitud; }

    public String getNombreRemitente() { return nombreRemitente; }

    public void setNombreRemitente(String nombreRemitente) { this.nombreRemitente = nombreRemitente; }

    public String getNombreDestinatario() { return nombreDestinatario; }

    public void setNombreDestinatario(String nombreDestinatario) { this.nombreDestinatario = nombreDestinatario; }
}
