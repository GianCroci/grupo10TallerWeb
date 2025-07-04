package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.entidad.Mensaje;
import com.tallerwebi.dominio.interfaz.repositorio.RepositorioMensaje;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RepositorioMensajeImpl implements RepositorioMensaje {

    @Autowired
    private SessionFactory sessionFactory;


    public RepositorioMensajeImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void guardar(Mensaje mensaje) {
        sessionFactory.getCurrentSession().save(mensaje);
    }

    @Override
    public List<Mensaje> obtenerHistorial(String usuario1, String usuario2) {
        String hql = "FROM Mensaje m WHERE " +
                "(m.remitente = :u1 AND m.destinatario = :u2) OR " +
                "(m.remitente = :u2 AND m.destinatario = :u1) " +
                "ORDER BY m.fecha ASC";
        return sessionFactory.getCurrentSession()
                .createQuery(hql, Mensaje.class)
                .setParameter("u1", usuario1)
                .setParameter("u2", usuario2)
                .getResultList();
    }
}
