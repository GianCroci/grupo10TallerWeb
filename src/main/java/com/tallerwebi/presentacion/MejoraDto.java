package com.tallerwebi.presentacion;

public class MejoraDto {

    private Long idEquipamiento;
    private Integer oroUsuario;


    public MejoraDto() {
    }

    public MejoraDto(Long idEquipamiento, Integer oroUsuario) {
        this.idEquipamiento = idEquipamiento;
        this.oroUsuario = oroUsuario;
    }

    public Integer getOroUsuario() {
        return oroUsuario;
    }

    public void setOroUsuario(Integer oroUsuario) {
        this.oroUsuario = oroUsuario;
    }

    public Long getIdEquipamiento() {
        return idEquipamiento;
    }

    public void setIdEquipamiento(Long equipamiento) {
        this.idEquipamiento = equipamiento;
    }
}
