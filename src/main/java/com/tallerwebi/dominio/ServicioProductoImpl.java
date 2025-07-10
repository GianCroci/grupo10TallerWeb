package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidad.Producto;
import com.tallerwebi.dominio.interfaz.repositorio.RepositorioProducto;
import com.tallerwebi.dominio.interfaz.servicio.ServicioProducto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service("servicioProducto")
@Transactional
public class ServicioProductoImpl implements ServicioProducto {

    private final RepositorioProducto repositorioProducto;

    @Autowired
    public ServicioProductoImpl(RepositorioProducto repositorioProducto) {
        this.repositorioProducto = repositorioProducto;
    }

    @Override
    public List<Producto> obtenerTodosLosProductos() {
        return repositorioProducto.obtenerTodosLosProductos();
    }
}
