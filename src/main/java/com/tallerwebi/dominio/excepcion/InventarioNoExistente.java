package com.tallerwebi.dominio.excepcion;

public class InventarioNoExistente extends RuntimeException {
    public InventarioNoExistente(String mensaje) {
        super(mensaje);
    }
}