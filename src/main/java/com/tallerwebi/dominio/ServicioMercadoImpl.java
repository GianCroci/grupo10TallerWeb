package com.tallerwebi.dominio;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service("servicioMercado")
public class ServicioMercadoImpl implements ServicioMercado {

    private final List<Equipamiento> productos = new ArrayList<>();

    public ServicioMercadoImpl() {
        Rol rol = new Guerrero(); // podés usar otra subclase de Rol si tenés

        // --- Tunica ---
        Tunica tunica = new Tunica("Tunica azul", 100);
        tunica.setId(1L);
        tunica.setStats(crearStats(40, 10, 15, 10));
        tunica.setRol(rol);
        tunica.setCostoCompra(150);
        tunica.setCostoMejora(20);
        tunica.setNivel(1);
        tunica.setEquipado(false);
        productos.add(tunica);

        // --- Cinturon ---
        Cinturon cinturon = new Cinturon("Cinturon oro", 80);
        cinturon.setId(2L);
        cinturon.setStats(crearStats(20, 5, 5, 5));
        cinturon.setRol(rol);
        cinturon.setCostoCompra(100);
        cinturon.setCostoMejora(10);
        cinturon.setNivel(1);
        cinturon.setEquipado(false);
        productos.add(cinturon);

        // --- Abrigo ---
        Abrigo abrigo = new Abrigo("Abrigo", 80);
        abrigo.setId(2L);
        abrigo.setStats(crearStats(20, 5, 5, 5));
        abrigo.setRol(rol);
        abrigo.setCostoCompra(100);
        abrigo.setCostoMejora(10);
        abrigo.setNivel(1);
        abrigo.setEquipado(false);
        productos.add(abrigo);

        // --- Capucha ---
        Capucha capucha = new Capucha("Capucha", 80);
        capucha.setId(2L);
        capucha.setStats(crearStats(20, 5, 5, 5));
        capucha.setRol(rol);
        capucha.setCostoCompra(100);
        capucha.setCostoMejora(10);
        capucha.setNivel(1);
        capucha.setEquipado(false);
        productos.add(capucha);
    }


    @Override
    public Mercado mostrarMercado() {
        Mercado mercado = new Mercado();
        mercado.setProductos(productos);
        return mercado;
    }

    @Override
    public String procesarCompra(List<String> itemsSeleccionados) {
        if (itemsSeleccionados == null || itemsSeleccionados.isEmpty()) {
            return "No seleccionaste ningún objeto";
        }
        return "¡Compra realizada con éxito! Has comprado: " + String.join(", ", itemsSeleccionados);
    }



    private Estadisticas crearStats(int fuerza, int inteligencia, int armadura, int agilidad) {
        Estadisticas stats = new Estadisticas();
        stats.setFuerza(fuerza);
        stats.setInteligencia(inteligencia);
        stats.setArmadura(armadura);
        stats.setAgilidad(agilidad);
        return stats;
    }
}
