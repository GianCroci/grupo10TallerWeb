package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.entidad.Pago;
import com.tallerwebi.dominio.interfaz.repositorio.RepositorioPago;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Transactional
@Repository("repositorioPago")
public class RepositorioPagoImpl implements RepositorioPago {

    private final SessionFactory sessionFactory;

    @Autowired
    public RepositorioPagoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void guardarPago(Pago pagoCreado) {
        sessionFactory.getCurrentSession().save(pagoCreado);
    }
}
