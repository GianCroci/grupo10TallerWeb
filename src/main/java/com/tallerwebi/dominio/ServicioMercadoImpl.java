package com.tallerwebi.dominio;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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

        for (String nombre : itemsSeleccionados) {
            equipamientos.stream()
                    .filter(e -> e.getNombre().equals(nombre))
                    .findFirst()
                    .ifPresent(original -> {
                        Equipamiento copia = clonarPorTipo(original);
                        Personaje personaje = repositorioPersonaje.buscarPersonaje(idPersonaje);
                        copia.setPersonaje(personaje);
                        repositorioInventario.agregarEquipamiento(copia);

                    });
        }

        return "¡Compra realizada con éxito! Has comprado: " + String.join(", ", itemsSeleccionados);
    }

    private Equipamiento clonarPorTipo(Equipamiento original) {
        Equipamiento copia;
        if (original instanceof Abrigo) copia = new Abrigo();
        else if (original instanceof Capucha) copia = new Capucha();
        else if (original instanceof ZapatoUno) copia = new ZapatoUno();
        else if (original instanceof ZapatoDos) copia = new ZapatoDos();
        else if (original instanceof Tunica) copia = new Tunica();
        else if (original instanceof Cinturon) copia = new Cinturon();
        else copia = new Equipamiento() {
                @Override public void mejorar() {}
            };

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
}

