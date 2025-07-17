package com.tallerwebi.punta_a_punta.vistas;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;

public class VistaLogin extends VistaWeb {

    public VistaLogin(Page page) {
        super(page);
        page.navigate("http://localhost:8080/spring/login");
    }

    public String obtenerTextoDeLaBarraDeNavegacion(){
        return this.obtenerTextoDelElemento("nav a.navbar-brand");
    }

    public String obtenerMensajeDeError(){
        return this.obtenerTextoDelElemento("p.alert.alert-danger");
    }

    public void escribirEMAIL(String email){
        this.escribirEnElElemento("#email", email);
    }

    public void escribirClave(String clave){
        this.escribirEnElElemento("#password", clave);
    }

    public void darClickEnIniciarSesion(){
        page.waitForSelector("button[type='submit']", new Page.WaitForSelectorOptions().setState(WaitForSelectorState.VISIBLE));
        this.darClickEnElElemento("#btn-login");
    }

    public void darClickEnRegistrarse() {
        page.waitForSelector("a#registrarse", new Page.WaitForSelectorOptions().setState(WaitForSelectorState.VISIBLE));
        this.darClickEnElElemento("#registrarse");
    }
}
