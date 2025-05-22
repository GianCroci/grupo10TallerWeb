package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Equipamiento;
import com.tallerwebi.dominio.RepositorioInventario;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository("repositorioInventario")
public class RepositorioInventarioImpl implements RepositorioInventario {

        @Override
        public List<Equipamiento> obtenerInventario() {
            List<Equipamiento> inventario = new ArrayList<>();
            Equipamiento equipamiento = new Equipamiento("espada", 100.0, 100.0, 100.0, 15, 10, 10, 10, false);
            Equipamiento equipamiento2 = new Equipamiento("daga", 100.0, 100.0, 100.0, 10, 10, 10, 10, false);
            Equipamiento equipamiento3 = new Equipamiento("baston", 100.0, 100.0, 100.0, 1, 10, 10, 10, false);
            inventario.add(equipamiento);
            inventario.add(equipamiento2);
            inventario.add(equipamiento3);
            return inventario;
        }

    @Override
    public void modificarEquipamiento(Equipamiento equipamiento) {

    }
}
