package com.tallerwebi.dominio.entidad;

import com.tallerwebi.dominio.EstadoSolicitud;

import javax.persistence.*;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        SolicitudAmistad that = (SolicitudAmistad) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}