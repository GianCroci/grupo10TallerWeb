package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.Equipamiento;

import java.util.List;

public interface ServicioInventario {

    Inventario buscarInventario(Integer id);

    String agregarEquipo(Integer idInventario, Equipamiento equipoNuevo);

    List<String> obtenerNombresArmas(Integer idInventario);

    List<String> obtenerNombresVestimentas(Integer idInventario);

    List<String> obtenerContenidoCompleto(Integer idInventario);

    String equiparArma(Integer idInventario, String nombreArma);

    String equiparVestimenta(Integer idInventario, String nombreVestimenta);

}