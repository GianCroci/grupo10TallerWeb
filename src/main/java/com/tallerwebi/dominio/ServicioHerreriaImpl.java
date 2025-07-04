package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidad.Equipamiento;
import com.tallerwebi.dominio.entidad.Personaje;
import com.tallerwebi.dominio.excepcion.InventarioVacioException;
import com.tallerwebi.dominio.excepcion.NivelDeEquipamientoMaximoException;
import com.tallerwebi.dominio.excepcion.OroInsuficienteException;
import com.tallerwebi.dominio.interfaz.repositorio.RepositorioInventario;
import com.tallerwebi.dominio.interfaz.repositorio.RepositorioPersonaje;
import com.tallerwebi.dominio.interfaz.servicio.ServicioHerreria;
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

    @Autowired
    public ServicioHerreriaImpl(RepositorioInventario repositorioInventario, RepositorioPersonaje repositorioPersonaje) {
        this.repositorioInventario = repositorioInventario;
        this.repositorioPersonaje = repositorioPersonaje;

    }

    @Override
    public void mejorarEquipamiento(Long idEquipamiento, Integer oroUsuario, Long idPersonaje) throws NivelDeEquipamientoMaximoException, OroInsuficienteException {

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
}