package com.tallerwebi.dominio;


import com.tallerwebi.dominio.excepcion.NivelDeEquipamientoMaximoException;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Mercado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "mercado_id")
    private List<Producto> productos = new ArrayList<>();

    public List<Producto> getProductos() {
        return productos;
    }
    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }
}

