package com.tallerwebi.dominio;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.tallerwebi.dominio.Personaje;

import javax.transaction.Transactional;

@Service("servicioMercado")
@Transactional
public class ServicioMercadoImpl implements ServicioMercado {

    private final RepositorioMercado repositorioMercado;
    private final RepositorioInventario repositorioInventario;
    private final RepositorioPersonaje repositorioPersonaje;

    public ServicioMercadoImpl(RepositorioMercado repositorioMercado, RepositorioInventario repositorioInventario, RepositorioPersonaje repositorioPersonaje) {
        this.repositorioMercado = repositorioMercado;
        this.repositorioInventario = repositorioInventario;
        this.repositorioPersonaje = repositorioPersonaje;
    }


    @Override
    public Mercado mostrarMercado() {
        return repositorioMercado.obtenerMercadoConProductos();
    }


    @Override
    public String procesarCompra(List<String> itemsSeleccionados, Long idPersonaje) {
        if (itemsSeleccionados == null || itemsSeleccionados.isEmpty()) {
            return "No seleccionaste ningún objeto";
        }

        Mercado mercado = repositorioMercado.obtenerMercadoConProductos();
        List<Equipamiento> equipamientos = mercado.getProductos();
        Personaje personaje = repositorioPersonaje.buscarPersonaje(idPersonaje);

        int oroDisponible = personaje.getOro();
        int totalCompra = 0;
        List<Equipamiento> comprasRealizadas = new ArrayList<>();

        for (String nombre : itemsSeleccionados) {
            Optional<Equipamiento> item = equipamientos.stream()
                    .filter(e -> e.getNombre().equals(nombre))
                    .findFirst();

            if (item.isPresent()) {
                Equipamiento original = item.get();
                totalCompra += original.getCostoCompra();
                comprasRealizadas.add(original);
            }
        }

        if (totalCompra > oroDisponible) {
            return "No tienes suficiente oro para comprar los objetos seleccionados.";
        }

        personaje.setOro(oroDisponible - totalCompra);
        repositorioPersonaje.modificar(personaje);

        for (Equipamiento original : comprasRealizadas) {
            Equipamiento copia = clonarPorTipo(original);
            copia.setPersonaje(personaje);
            repositorioInventario.agregarEquipamiento(copia);
        }

        return "¡Compra realizada con éxito! Has comprado: " + String.join(", ", itemsSeleccionados);
    }


    private Equipamiento clonarPorTipo(Equipamiento original) {
        Equipamiento copia = null;

        if (original instanceof Arma) copia = new Arma();
        else if (original instanceof Botas) copia = new Botas();
        else if (original instanceof Casco) copia = new Casco();
        else if (original instanceof Escudo) copia = new Escudo();
        else if (original instanceof Pechera) copia = new Pechera();
        else if (original instanceof Pantalones) copia = new Pantalones();


        copia.setNombre(original.getNombre());
        copia.setStats(original.getStats());
        copia.setRol(original.getRol());
        copia.setCostoCompra(original.getCostoCompra());
        copia.setCostoMejora(original.getCostoMejora());
        copia.setCostoVenta(original.getCostoVenta());
        copia.setNivel(original.getNivel());
        copia.setEquipado(false);
        copia.setImagen(original.getImagen());

        return copia;
    }


    @Override
    public Integer obtenerOroDelPersonaje(Long idPersonaje) {
        Integer oroPersonaje = repositorioPersonaje.buscarOroPersonaje(idPersonaje);
        return oroPersonaje;
    }
}

