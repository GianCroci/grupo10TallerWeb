package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.EstadoSolicitud;
import com.tallerwebi.dominio.Personaje;
import com.tallerwebi.dominio.RepositorioSolicitudAmistad;
import com.tallerwebi.dominio.SolicitudAmistad;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository("repositorioSolicitudAmistad")
public class RepositorioSolicitudAmistadImpl implements RepositorioSolicitudAmistad {

    private SessionFactory sessionFactory;

    @Autowired
    public RepositorioSolicitudAmistadImpl(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void guardarSolicitud(SolicitudAmistad solicitud) {
        sessionFactory.getCurrentSession().save(solicitud);
    }

    @Override
    public SolicitudAmistad buscarPorId(Long idSolicitud) {
        Session session = sessionFactory.getCurrentSession();
        return (SolicitudAmistad) session.createCriteria(SolicitudAmistad.class)
                .add(Restrictions.eq("id", idSolicitud))
                .uniqueResult();
    }

    @Override
    public void modificarSolicitud(SolicitudAmistad solicitud) {
        sessionFactory.getCurrentSession().update(solicitud);
    }

    @Override
    public List<SolicitudAmistad> obtenerSolicitudesDeAmistadRecibidasPendientes(Long idPersonaje) {
        Session session = sessionFactory.getCurrentSession();
        return (List<SolicitudAmistad>) session.createCriteria(SolicitudAmistad.class)
                .add(Restrictions.eq("destinatario.id", idPersonaje))
                .add(Restrictions.eq("estado", EstadoSolicitud.PENDIENTE))
                .list();
    }

    @Override
    public List<SolicitudAmistad> obtenerSolicitudesDeAmistadEnviadasPendientes(Long idPersonaje) {
        Session session = sessionFactory.getCurrentSession();
        return (List<SolicitudAmistad>) session.createCriteria(SolicitudAmistad.class)
                .add(Restrictions.eq("remitente.id", idPersonaje))
                .add(Restrictions.eq("estado", EstadoSolicitud.PENDIENTE))
                .list();
    }
}
