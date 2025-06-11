package com.tallerwebi.dominio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ServicioInventarioImpl implements ServicioInventario {

    private RepositorioInventario repositorioInventario;
    private List<Equipamiento> equipos;

    @Autowired
    public ServicioInventarioImpl(RepositorioInventario repositorioInventario) {
        //this.repositorioInventario = repositorioInventario;
        //this.equipos = repositorioInventario.obtenerInventario(null);
        //Agregar el repo del usuario para poder buscar el equipamiento correspondiente por el id
        this.equipos = new ArrayList<Equipamiento>();
        Estadisticas stats = new Estadisticas();
        stats.setFuerza(40);
        stats.setInteligencia(10);
        stats.setArmadura(15);
        stats.setAgilidad(10);

        Estadisticas stats2 = new Estadisticas();
        stats2.setFuerza(25);
        stats2.setInteligencia(10);
        stats2.setArmadura(10);
        stats2.setAgilidad(20);

        Estadisticas stats3 = new Estadisticas();
        stats3.setFuerza(30);
        stats3.setInteligencia(25);
        stats3.setArmadura(10);
        stats3.setAgilidad(15);

        Rol rol = new Guerrero();

        Equipamiento espada = new Arma("espada dorada", stats, rol, 100, 50, 20, 1, true);
        Equipamiento daga = new Arma("daga", stats2, rol, 100, 50, 20, 1, false);
        Equipamiento baston = new Arma("baston", stats3, rol, 100, 50, 20, 1, false);
        espada.setId(1L);
        daga.setId(2L);
        baston.setId(3L);

        this.equipos.add(espada);
        this.equipos.add(daga);
        this.equipos.add(baston);
    }

    //Todos los metodos se llevan a cabo encima de un arraylist, falta cambiarlos por los metodos del repo!

    @Override
    public List<Equipamiento> mostrarEquipamiento() {
        return this.equipos;
    }

    @Override
    public Equipamiento mostrarPrimerEquipado() {
        return this.equipos.stream()
                .filter(Equipamiento::getEquipado)
                .findFirst()
                .orElse(null);
    }

    @Override
    public Equipamiento buscarEquipamientoPorId(Long id) {
        return this.equipos.stream()
                .filter(equipo -> equipo.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Boolean equipar(Long id) {
        Equipamiento equipoAEquipar = buscarEquipamientoPorId(id);
        if (equipoAEquipar != null) {
            equipos.stream()
                    .filter(e -> e.getEquipado() && !e.getId().equals(equipoAEquipar.getId()))
                    .forEach(e -> e.setEquipado(false));
            equipoAEquipar.setEquipado(true);
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }

    @Override
    public Boolean agregarEquipo(Equipamiento nuevo) {
        return this.equipos.add(nuevo);
    }



    /*
    public void darArmaEspecial(){
        Equipamiento armaEspecial = new Arma();
        armaEspecial.setId(4l);
        armaEspecial.setNombre("Arma Especial");
        armaEspecial.getStats().setFuerza(10);
        armaEspecial.getStats().setInteligencia(10);
        armaEspecial.getStats().setArmadura(10);
        armaEspecial.getStats().setAgilidad(10);
        armaEspecial.setCostoMejora(100);
        armaEspecial.setEquipado(false);

        equipar(4);
        repositorioInventario.modificarEquipamiento(armaEspecial);
    }

*/
}