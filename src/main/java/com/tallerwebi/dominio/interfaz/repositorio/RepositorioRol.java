package com.tallerwebi.dominio.interfaz.repositorio;

import com.tallerwebi.dominio.entidad.Rol;

import java.util.List;

public interface RepositorioRol {
    Rol obtenerRolPorId(Long idRol);
    void guardarRol(Rol rol);
    List<Rol> obtenerTodos();
    void eliminar(Rol rol);

}
