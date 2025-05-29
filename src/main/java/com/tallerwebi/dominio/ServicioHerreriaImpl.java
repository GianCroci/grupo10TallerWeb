package com.tallerwebi.dominio;

import com.tallerwebi.infraestructura.RepositorioInventarioImpl;
import com.tallerwebi.presentacion.MejoraDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service("servicioHerreria")
@Transactional
public class ServicioHerreriaImpl implements ServicioHerreria {

    RepositorioInventario repositorioInventario;

    @Autowired
    public ServicioHerreriaImpl(RepositorioInventario repositorioInventario) {
        this.repositorioInventario = repositorioInventario;
    }

    @Override
    public Boolean mejorarEquipamiento(Equipamiento equipamiento, Double oroUsuario) {


        if (oroUsuario >= equipamiento.getCostoMejora()) {
            equipamiento.setFuerza(equipamiento.getFuerza() + 1);
            equipamiento.setInteligencia(equipamiento.getInteligencia() + 1);
            equipamiento.setArmadura(equipamiento.getArmadura() + 1);
            equipamiento.setAgilidad(equipamiento.getAgilidad() + 1);
            equipamiento.setCostoMejora(equipamiento.getCostoMejora() + 50.0);
            repositorioInventario.modificarEquipamiento(equipamiento);
            return true;
        }
        return false;
    }

    @Override
    public List<Equipamiento> obtenerInventario() {

        List<Equipamiento> inventario = repositorioInventario.obtenerInventario();
        return inventario;
    }
}
