package com.tallerwebi.dominio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ServicioEquipamientoImpl implements ServicioEquipamiento{

    @Autowired(required = false)
    private List<Equipamiento> equipos;

    public ServicioEquipamientoImpl(){
        this.equipos = new ArrayList<>();
        this.equipos.add(new Equipamiento("Espada", 100.0, 100.0, 100.0, 20, 30, 40, 20, Boolean.TRUE));
        this.equipos.add(new Equipamiento("Espada 2", 100.0, 100.0, 100.0, 20, 30, 40, 20, Boolean.TRUE));
    }


    public List<Equipamiento> mostrarEquipamiento() {
        return this.equipos;
    }

}