package com.tallerwebi.dominio.entidad;


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
    private List<Equipamiento> productos = new ArrayList<>();

    public List<Equipamiento> getProductos() {
        return productos;
    }
    public void setProductos(List<Equipamiento> productos) {
        this.productos = productos;
    }
}

