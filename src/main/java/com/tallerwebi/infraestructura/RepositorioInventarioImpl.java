package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.*;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository("repositorioInventario")
public class RepositorioInventarioImpl implements RepositorioInventario {

        @Override
        public List<Equipamiento> obtenerInventario(Long idPersonaje) {
            List<Equipamiento> inventario = new ArrayList<>();
            String nombre = "espada";
            Estadisticas stats = new Estadisticas();
            Rol rol = new Guerrero();
            Integer costoCompra = 100;
            Integer costoVenta = 100;
            Integer costoMejora = 100;
            Integer nivel = 1;
            Boolean equipado = false;

            Equipamiento equipamiento = new Arma(nombre, stats, rol, costoCompra, costoVenta, costoMejora, nivel, equipado);
            Equipamiento equipamiento2 = new Arma("baston", stats, rol, costoCompra, costoVenta, costoMejora, nivel, equipado);
            Equipamiento equipamiento3 = new Arma("daga", stats, rol, costoCompra, costoVenta, costoMejora, nivel, equipado);
            inventario.add(equipamiento);
            inventario.add(equipamiento2);
            inventario.add(equipamiento3);
            return inventario;
        }

    @Override
    public void modificarEquipamiento(Equipamiento equipamiento) {

    }
}
