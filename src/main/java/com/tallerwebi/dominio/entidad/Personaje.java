package com.tallerwebi.dominio.entidad;

import com.tallerwebi.dominio.Estadisticas;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
public class Personaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String genero;
    @OneToOne
    private Rol rol;
    @Embedded
    private Estadisticas estadisticas;
    private Integer nivel;
    private Integer vida;
    private String imagen;
    private Integer oro;
    @OneToMany(mappedBy = "personaje", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Equipamiento> equipamientos = new ArrayList<>();
    private String codigoAmigo;
    @ManyToMany
    @JoinTable(
            name = "amigos",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "amigo_id")
    )
    private List<Personaje> amigos = new ArrayList<>();

    public void setId(Long id) { this.id = id; }

    public List<Equipamiento> getEquipamientos() { return equipamientos; }

    public void setEquipamientos(List<Equipamiento> equipamientos) { this.equipamientos = equipamientos; }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getGenero() {
        return genero;
    }

    public Long getId(){return id;}

    public Integer getOro() { return oro; }

    public void setOro(Integer oro) { this.oro = oro; }

    public Rol getRol() { return rol; }

    public void setRol(Rol rol) { this.rol = rol; }

    public Estadisticas getEstadisticas() { return estadisticas; }

    public void setEstadisticas(Estadisticas estadisticas) { this.estadisticas = estadisticas; }

    public String getCodigoAmigo() { return codigoAmigo; }

    public void setCodigoAmigo(String codigoAmigo) { this.codigoAmigo = codigoAmigo; }

    public List<Personaje> getAmigos() { return amigos; }

    public void setAmigos(List<Personaje> amigos) { this.amigos = amigos; }

    public Integer getNivel() { return nivel; }

    public void setNivel(Integer nivel) { this.nivel = nivel; }

    public Integer getVida() { return vida; }

    public void setVida(Integer vida) { this.vida = vida; }

    public void aplicarEstadisticasBase() {
        getRol().aplicarStatsBase(this);

    }

    public void inicializarCodigoAmigo() {
        this.codigoAmigo = UUID.randomUUID().toString().substring(0, 8);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Personaje personaje = (Personaje) o;
        return Objects.equals(id, personaje.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    public void calcularNivel() {
        Integer sumaEstadisticas = obtenerTotalEstadisticas();
        this.nivel = sumaEstadisticas / 10;
    }

    private Integer obtenerTotalEstadisticas() {
        return this.estadisticas.getAgilidad()
                + this.estadisticas.getInteligencia()
                + this.estadisticas.getArmadura()
                + this.estadisticas.getFuerza();
    }

    public void calcularVida() {
        this.vida = this.nivel * 15;
    }
}
