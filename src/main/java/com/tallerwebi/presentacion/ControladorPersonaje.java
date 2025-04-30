package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioPersonaje;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class ControladorPersonaje {

    @Autowired
    private ServicioPersonaje servicioPersonaje;

    public ControladorPersonaje(ServicioPersonaje servicioPersonaje) {
        this.servicioPersonaje = servicioPersonaje;
    }

    public void setNombre(String nombre) {
        servicioPersonaje.setNombre(nombre);
    }

    public String getNombre() {
       return servicioPersonaje.getNombre();
    }
}
