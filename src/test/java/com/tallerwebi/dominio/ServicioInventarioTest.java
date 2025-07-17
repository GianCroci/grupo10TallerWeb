package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidad.Equipamiento;
import com.tallerwebi.dominio.entidad.Personaje;
import com.tallerwebi.dominio.entidad.Arma;
import com.tallerwebi.dominio.entidad.Rol;
import com.tallerwebi.dominio.entidad.Guerrero;
import com.tallerwebi.dominio.excepcion.InventarioVacioException;
import com.tallerwebi.dominio.interfaz.repositorio.RepositorioInventario;
import com.tallerwebi.dominio.interfaz.servicio.ServicioInventario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class ServicioInventarioTest {

    private ServicioInventario servicioInventario;
    private RepositorioInventario repositorioInventarioMock;
    private Personaje personajeMock;

    @BeforeEach
    public void init() {
        repositorioInventarioMock = mock(RepositorioInventario.class);
        servicioInventario = new ServicioInventarioImpl(repositorioInventarioMock);
        personajeMock = mock(Personaje.class);
    }

}