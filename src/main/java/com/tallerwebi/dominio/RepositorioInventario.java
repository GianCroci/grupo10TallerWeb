package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioInventario {
    List<Equipamiento> obtenerInventario();

    void modificarEquipamiento(Equipamiento equipamiento);
}
