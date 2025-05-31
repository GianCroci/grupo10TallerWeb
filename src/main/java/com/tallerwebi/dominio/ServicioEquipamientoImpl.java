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
        this.equipos = repositorioInventario.obtenerInventario();
        this.equipos.get(0).setId(1);
        this.equipos.get(1).setId(2);
        this.equipos.get(2).setId(3);
    }

    @Override
    public List<Equipamiento> mostrarEquipamiento() {
        return this.equipos;
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
            return true;
        } else {
            return false;
        }
    }

    public void darArmaEspecial(){
        Equipamiento armaEspecial = new Equipamiento();
        armaEspecial.setId(4);
        armaEspecial.setNombre("Arma Especial");
        armaEspecial.setFuerza(10);
        armaEspecial.setInteligencia(10);
        armaEspecial.setArmadura(10);
        armaEspecial.setAgilidad(10);
        armaEspecial.setCostoMejora(100.0);
        armaEspecial.setEquipado(false);

        equipar(4);
        repositorioInventario.modificarEquipamiento(armaEspecial);
    }


}