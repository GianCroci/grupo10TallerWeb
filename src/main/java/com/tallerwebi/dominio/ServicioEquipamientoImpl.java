package com.tallerwebi.dominio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ServicioEquipamientoImpl implements ServicioEquipamiento{

    private RepositorioInventario repositorioInventario;
    private List<Equipamiento> equipos;

    @Autowired
    public ServicioEquipamientoImpl(RepositorioInventario repositorioInventario) {
        this.repositorioInventario = repositorioInventario;
        this.equipos = repositorioInventario.obtenerInventario(null);
        //Agregar el repo del usuario para poder buscar el equipamiento correspondiente por el id
    }

    @Override
    public List<Equipamiento> mostrarEquipamiento() {
        return this.equipos;
    }

    public Optional<Equipamiento> mostrarPrimerEquipado(){
        return this.equipos.stream().filter(Equipamiento::getEquipado).findFirst();
    }

    @Override
    public Optional<Equipamiento> buscarEquipamientoPorId(Integer id) {
        return this.equipos.stream()
                .filter(equipo -> equipo.getId().equals(id))
                .findFirst();
    }

    @Override
    public Boolean equipar(Integer id) {
        Optional<Equipamiento> equipoOptional = buscarEquipamientoPorId(id);
        if (equipoOptional.isPresent()) {
            Equipamiento equipoAEquipar = equipoOptional.get();
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