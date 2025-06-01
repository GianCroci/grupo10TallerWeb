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

        //la idea seria agregar mas adeltante esta logica de que si
        //el usuario tiene mas de 5 tragos, se le permite mejorar el equipamiento

        /*
        if (sePuedeMejorar()==true) {
            // logica normal de la mejora
        }
           throw new IllegalArgumentException("No puedes mejorar el equipamiento, debes invitar al menos 5 tragos al herrero.");

        */
       /*

            if (oroUsuario > equipamiento.getCostoMejora()) {
                equipamiento.setFuerza(equipamiento.getFuerza() + 1);
                equipamiento.setInteligencia(equipamiento.getInteligencia() + 1);
                equipamiento.setArmadura(equipamiento.getArmadura() + 1);
                equipamiento.setAgilidad(equipamiento.getAgilidad() + 1);
                equipamiento.setCostoMejora(equipamiento.getCostoMejora() + 50.0);
                repositorioInventario.modificarEquipamiento(equipamiento);

                return true;
            }
       */
        return false;
    }

    @Override
    public List<Equipamiento> obtenerInventario() {

        List<Equipamiento> inventario = repositorioInventario.obtenerInventario();
        return inventario;
    }

    /*
    @Override
    public Boolean sePuedeMejorar() {

        //si el herrero ha recibido 5 tragos o más, se puede mejorar el equipamiento
        if (servicioTaberna.getCervezasInvitadas(PersonajeTaberna.HERRERO) >= 5) {
            return true;
        } else {
            throw new IllegalArgumentException("No puedes mejorar el equipamiento, debes invitar al menos 5 tragos al herrero.");
        }

    }
    */



}
