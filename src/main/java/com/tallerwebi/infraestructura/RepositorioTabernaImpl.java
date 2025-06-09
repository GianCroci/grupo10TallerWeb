package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.PersonajeTaberna;
import com.tallerwebi.dominio.RepositorioTaberna;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Transactional
@Repository("repositorioTaberna")
public class RepositorioTabernaImpl implements RepositorioTaberna {


    private SessionFactory sessionFactory;

    public RepositorioTabernaImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public int getCantidadCervezasInvitadas(PersonajeTaberna personajeTaberna) {
        return 0;
    }
}
