package com.tallerwebi.punta_a_punta.vistas;

import com.microsoft.playwright.Page;

public class VistaCreacionPersonaje extends VistaWeb{

    public VistaCreacionPersonaje(Page page) {
        super(page);
        page.navigate("localhost:8080/spring/creacion-personaje");
    }

    public void escribirNombre(String nombre){
        this.escribirEnElElemento("#nombre", nombre);
    }

    public void irACreacionDePersonaje() {
        page.navigate("http://localhost:8080/spring/nuevo-personaje");
    }

    public Page getPage() {
        return this.page;
    }
/*
    public String obtenerMensajeDeError(){
        return this.obtenerTextoDelElemento("p.alert.alert-danger");
    }



    public void escribirClave(String clave){
        this.escribirEnElElemento("#password", clave);
    }

    public void darClickEnIniciarSesion(){
        this.darClickEnElElemento("#btn-login");
    }
    * */
}
