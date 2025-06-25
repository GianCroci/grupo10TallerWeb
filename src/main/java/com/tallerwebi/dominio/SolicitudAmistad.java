package com.tallerwebi.dominio;

import javax.persistence.*;

@Entity
public class SolicitudAmistad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "remitente_id")
    private Personaje remitente; // quien envía la solicitud

    @ManyToOne
    @JoinColumn(name = "destinatario_id")
    private Personaje destinatario; // quien recibe la solicitud

    @Enumerated(EnumType.STRING)
    private EstadoSolicitud estado;

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public Personaje getRemitente() { return remitente; }

    public void setRemitente(Personaje remitente) { this.remitente = remitente; }

    public Personaje getDestinatario() { return destinatario; }

    public void setDestinatario(Personaje destinatario) { this.destinatario = destinatario; }

    public EstadoSolicitud getEstado() { return estado; }

    public void setEstado(EstadoSolicitud estado) { this.estado = estado; }
}