package com.tallerwebi.dominio;

import com.tallerwebi.presentacion.MejoraDto;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service("servicioHerreria")
@Transactional
public class ServicioHerreriaImpl implements ServicioHerreria {

    @Override
    public Boolean mejorarEquipamiento(Equipamiento Equipamiento, Integer oro) {


        return false;
    }
}
