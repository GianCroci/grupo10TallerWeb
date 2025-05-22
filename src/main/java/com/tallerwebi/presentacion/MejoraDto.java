package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Equipamiento;

public class MejoraDto {

    private Equipamiento Equipamiento;
    private Integer oro;


    public MejoraDto() {
    }

    public MejoraDto(Integer idEquipamiento, Integer oro) {
        this.Equipamiento = Equipamiento;
        this.oro = oro;
    }

    public Integer getOro() {
        return oro;
    }
    public void setOro(Integer oro) {
        this.oro = oro;
    }

    public Equipamiento getEquipamiento() { return Equipamiento; }

    public void setIdEquipamiento(Integer idEquipamiento) { this.Equipamiento = Equipamiento; }
}
