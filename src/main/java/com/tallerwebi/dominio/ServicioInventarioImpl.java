package com.tallerwebi.dominio;

import com.tallerwebi.dominio.Equipamiento;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.*;

@Service
@Transactional
public class ServicioInventarioImpl implements ServicioInventario {

    private Map<Integer, Inventario> inventarios = new HashMap<>();

    public ServicioInventarioImpl() {
        Inventario inventario = new Inventario();
        inventarios.put(1, inventario);
    }

    @Override
    public Inventario buscarInventario(Integer id) {
        return inventarios.get(id);
    }

    @Override
    public String agregarEquipo(Integer id, Equipamiento equipo) {
        Inventario inv = buscarInventario(id);

        if (inv == null) return "Inventario no encontrado";

        switch (equipo.getTipo()) {
            case "ARMA":
                if (inv.getArmas().size() < 5) {
                    inv.agregarEquipo(equipo);
                    return "Arma agregada al inventario con exito";
                }
                return "Almacenamiento de armas lleno";
            case "VESTIMENTA":
                if (inv.getVestimentas().size() < 5) {
                    inv.agregarEquipo(equipo);
                    return "Vestimenta agregada al inventario con exito";
                }
                return "Almacenamiento de vestimentas lleno";
            default:
                return "Tipo de equipo inválido";
        }
    }

    @Override
    public List<String> obtenerNombresArmas(Integer id) {
        Inventario inv = buscarInventario(id);
        return inv != null ? inv.getNombreArmas() : Collections.emptyList();
    }

    @Override
    public List<String> obtenerNombresVestimentas(Integer id) {
        Inventario inv = buscarInventario(id);
        return inv != null ? inv.getNombreVestimentas() : Collections.emptyList();
    }

    @Override
    public List<String> obtenerContenidoCompleto(Integer id) {
        Inventario inv = buscarInventario(id);
        return inv != null ? inv.getTodoELInventario() : Collections.emptyList();
    }

    @Override
    public String equiparArma(Integer id, String nombreArma) {
        Inventario inv = buscarInventario(id);
        return (inv != null && inv.buscarArmaPorNombre(nombreArma) != null)
                ? "Arma equipada"
                : "No se pudo equipar el arma";
    }

    @Override
    public String equiparVestimenta(Integer id, String nombreVestimenta) {
        Inventario inv = buscarInventario(id);
        return (inv != null && inv.buscarVestimentaPorNombre(nombreVestimenta) != null)
                ? "Vestimenta equipada"
                : "No se pudo equipar la vestimenta";
    }
}
