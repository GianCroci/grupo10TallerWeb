package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Equipamiento;

public class MejoraDto {

    private Equipamiento equipamiento;
    private Integer oroUsuario;


    public MejoraDto() {
    }

    public MejoraDto(Equipamiento equipamiento, Integer oroUsuario) {
        this.equipamiento = equipamiento;
        this.oroUsuario = oroUsuario;
    }

    public Integer getOroUsuario() {
        return oroUsuario;
    }

    public void setOroUsuario(Integer oroUsuario) {
        this.oroUsuario = oroUsuario;
    }

    public Equipamiento getEquipamiento() {
        return equipamiento;
    }

    public void setEquipamiento(Equipamiento equipamiento) {
        this.equipamiento = equipamiento;
    }
}
