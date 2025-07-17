package com.tallerwebi.punta_a_punta.vistas;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;

public class VistaHerreria extends VistaWeb {
    public VistaHerreria(Page page) {
        super(page);
        page.navigate("http://localhost:8080/spring/herreria");
    }

    public String obtenerMensajeDeError() {
        return this.obtenerTextoDelElemento("#mensajeError");
    }

    public String obtenerMensajeDeMejora() {
        return this.obtenerTextoDelElemento("#mensajeMejora");
    }

    public void hacerClickEnMejorarEquipamiento(String nombreEquipamiento) {
        page.waitForSelector("button[type='submit']", new Page.WaitForSelectorOptions().setState(WaitForSelectorState.VISIBLE));
        this.darClickEnElElemento("#mejorar-" + nombreEquipamiento);
    }
}
