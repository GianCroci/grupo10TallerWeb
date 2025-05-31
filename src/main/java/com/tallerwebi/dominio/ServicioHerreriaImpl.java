package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.InventarioVacioException;
import com.tallerwebi.dominio.excepcion.NivelDeEquipamientoMaximoException;
import com.tallerwebi.dominio.excepcion.OroInsuficienteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service("servicioHerreria")
@Transactional
public class ServicioHerreriaImpl implements ServicioHerreria {

    private final RepositorioPersonaje repositorioPersonaje;
    private final RepositorioInventario repositorioInventario;

    @Autowired
    public ServicioHerreriaImpl(RepositorioInventario repositorioInventario, RepositorioPersonaje repositorioPersonaje) {
        this.repositorioInventario = repositorioInventario;
        this.repositorioPersonaje = repositorioPersonaje;
    }

    @Override
    public void mejorarEquipamiento(Equipamiento equipamiento, Integer oroUsuario) throws NivelDeEquipamientoMaximoException, OroInsuficienteException {
        if (oroUsuario < equipamiento.getCostoMejora()) {
            throw new OroInsuficienteException("Tu oro no es suficiente para realizar esta accion");
        }
        equipamiento.mejorar();
        repositorioInventario.modificarEquipamiento(equipamiento);
    }

    @Override
    public List<Equipamiento> obtenerInventario(Long idPersonaje) throws InventarioVacioException {
        List<Equipamiento> inventario = repositorioInventario.obtenerInventario(idPersonaje);
        if (inventario.isEmpty()) {
            throw new InventarioVacioException("No se han encontrado equipamientos en su inventario");
        }
        return inventario;
    }

    @Override
    public Integer obtenerOroDelPersonaje(Long idPersonaje) {
        Personaje personajeObtenido = repositorioPersonaje.buscarPersonaje(idPersonaje);
        Integer oroPersonaje = personajeObtenido.getOro();
        return oroPersonaje;
    }
}
