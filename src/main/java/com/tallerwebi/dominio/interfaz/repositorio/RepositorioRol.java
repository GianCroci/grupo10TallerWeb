package com.tallerwebi.dominio.interfaz.repositorio;

import com.tallerwebi.dominio.entidad.Rol;

public interface RepositorioRol {
    Rol obtenerRolPorId(Long idRol);
    void guardarRol(Rol rol);
}
