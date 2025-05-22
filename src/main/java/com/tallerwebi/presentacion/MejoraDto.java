package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Equipamiento;

public class MejoraDto {

    private Equipamiento equipamiento;
    private Double oroUsuario;


    public MejoraDto() {
    }

    public MejoraDto(Equipamiento equipamiento, Double oroUsuario) {
        this.equipamiento = equipamiento;
        this.oroUsuario = oroUsuario;
    }

    public Double getOroUsuario() {
        return oroUsuario;
    }

    public void setOroUsuario(Double oroUsuario) {
        this.oroUsuario = oroUsuario;
    }

    public Equipamiento getEquipamiento() {
        return equipamiento;
    }

    public void setEquipamiento(Equipamiento equipamiento) {
        this.equipamiento = equipamiento;
    }
}
