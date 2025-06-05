package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.InventarioVacioException;
import com.tallerwebi.dominio.excepcion.NivelDeEquipamientoMaximoException;
import com.tallerwebi.dominio.excepcion.OroInsuficienteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.Comparator;
import java.util.List;

@Service("servicioHerreria")
@Transactional
public class ServicioHerreriaImpl implements ServicioHerreria {

    private final RepositorioPersonaje repositorioPersonaje;
    private final RepositorioInventario repositorioInventario;
    private final ServicioTaberna servicioTaberna;



    @Autowired
    public ServicioHerreriaImpl(RepositorioInventario repositorioInventario, RepositorioPersonaje repositorioPersonaje, ServicioTaberna servicioTaberna) {
        this.repositorioInventario = repositorioInventario;
        this.repositorioPersonaje = repositorioPersonaje;
        this.servicioTaberna = servicioTaberna;
    }

    @Override
    public void mejorarEquipamiento(Long idEquipamiento, Integer oroUsuario, Long idPersonaje) throws NivelDeEquipamientoMaximoException, OroInsuficienteException {
        /*
        if (sePuedeMejorar()==true) {
            // logica normal de la mejora
        }
           throw new IllegalArgumentException("No puedes mejorar el equipamiento, debes invitar al menos 5 tragos al herrero.");
         */
        Equipamiento equipamientoObtenido = repositorioInventario.obtenerEquipamientoPorId(idEquipamiento);

        if (oroUsuario < equipamientoObtenido.getCostoMejora()) {
            throw new OroInsuficienteException("Tu oro no es suficiente para realizar esta accion");
        }
        Personaje personajeObtenido = repositorioPersonaje.buscarPersonaje(idPersonaje);
        Integer oroUsuarioFinal = personajeObtenido.getOro() - equipamientoObtenido.getCostoMejora();
        equipamientoObtenido.mejorar();
        personajeObtenido.setOro(oroUsuarioFinal);
        repositorioInventario.modificarEquipamiento(equipamientoObtenido);
        repositorioPersonaje.modificar(personajeObtenido);
    }

    @Override
    public List<Equipamiento> obtenerInventario(Long idPersonaje) throws InventarioVacioException {
        List<Equipamiento> inventario = repositorioInventario.obtenerInventario(idPersonaje);
        if (inventario.isEmpty()) {
            throw new InventarioVacioException("No se han encontrado equipamientos en su inventario");
        }
        inventario.sort(Comparator.comparing(Equipamiento::getNombre));
        return inventario;
    }

    @Override
    public Integer obtenerOroDelPersonaje(Long idPersonaje) {
        Integer oroPersonaje = repositorioPersonaje.buscarOroPersonaje(idPersonaje);
        return oroPersonaje;
    }

    public Boolean sePuedeMejorar() {

        //si el herrero ha recibido 5 tragos o más, se puede mejorar el equipamiento
        if (servicioTaberna.getCervezasInvitadas(PersonajeTaberna.HERRERO) >= 5) {
            return true;
        } else {
            throw new IllegalArgumentException("No puedes mejorar el equipamiento, debes invitar al menos 5 tragos al herrero.");
        }
    }

}



