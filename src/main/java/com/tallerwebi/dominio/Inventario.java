package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.Equipamiento;

import java.util.ArrayList;
import java.util.List;


public class Inventario {

    public List <Equipamiento> armas;
    public List <Equipamiento> vestimentas;
    public Equipamiento armaEquipada;
    public Equipamiento vestimentaEquipada;


    public Inventario() {
        this.armas = new ArrayList<Equipamiento>(5);
        this.vestimentas = new ArrayList<Equipamiento>(5);
        this.armaEquipada = new Equipamiento("Espada","ARMA");
        this.armas.add(armaEquipada);
        this.vestimentaEquipada = new Equipamiento("Armadura", "VESTIMENTA");
        this.vestimentas.add(vestimentaEquipada);
    }

    public List<String> getNombreArmas() {
        List<String> nombres = new ArrayList<>();
        for (Equipamiento arma : armas) {
            nombres.add(arma.getNombre());
        }
        return nombres;
    }

    public List<String> getNombreVestimentas() {
        List<String> nombres = new ArrayList<>();
        for (Equipamiento vestimenta : vestimentas) {
            nombres.add(vestimenta.getNombre());
        }
        return nombres;
    }

    public List<String> getTodoELInventario() {
        List<String> nombres = new ArrayList<>();

        for (Equipamiento arma : armas) {
            nombres.add(arma.getNombre());
        }

        for (Equipamiento vestimenta : vestimentas) {
            nombres.add(vestimenta.getNombre());
        }

        return nombres;
    }


    public List <Equipamiento> getArmas() {
        return this.armas;
    }

    public Boolean agregarEquipo(Equipamiento equipoNuevo) {
        if (equipoNuevo.getTipo().equals("ARMA")) {
            return this.armas.add(equipoNuevo);
        }
        if (equipoNuevo.getTipo().equals("VESTIMENTA")) {
            return this.vestimentas.add(equipoNuevo);
        }else{
            return Boolean.FALSE;
        }
    }

    public List<Equipamiento> getVestimentas() {
        return this.vestimentas;
    }

    public Equipamiento buscarArmaPorNombre(String nombre) {
        for (int i = 0; i < this.armas.size(); i++) {
            if (armas.get(i).getNombre().equals(nombre)) {
                return this.armas.get(i);
            }
        }
        return null;
    }

    public Object buscarVestimentaPorNombre(String nombre) {
        for (int i = 0; i < this.vestimentas.size(); i++) {
            if (vestimentas.get(i).getNombre().equals(nombre)) {
                return this.vestimentas.get(i);
            }
        }
        return null;
    }
}