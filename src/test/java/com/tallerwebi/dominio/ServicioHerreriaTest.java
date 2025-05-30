package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.NivelDeEquipamientoMaximoException;
import com.tallerwebi.dominio.excepcion.OroInsuficienteException;
import com.tallerwebi.infraestructura.RepositorioInventarioImpl;
import com.tallerwebi.infraestructura.RepositorioPersonajeImpl;
import com.tallerwebi.presentacion.MejoraDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ServicioHerreriaTest {

    private RepositorioInventario repositorioInventario;
    private ServicioHerreria servicioHerreria;
    private MejoraDto mejoraDtoMock;
    private RepositorioPersonaje repositorioPersonaje;
    private List<Equipamiento> listaEquipamientosMock;
    private Long idPersonajeMock;
    private Equipamiento equipamientoMock;
    private Personaje personajeMock;
    private Estadisticas estadisticasMock;

    @BeforeEach
    public void init() {
        repositorioInventario = mock(RepositorioInventario.class);
        repositorioPersonaje = mock(RepositorioPersonaje.class);
        mejoraDtoMock = mock(MejoraDto.class);
        equipamientoMock = mock(Equipamiento.class);
        listaEquipamientosMock = new ArrayList<>();
        listaEquipamientosMock.add(equipamientoMock);
        idPersonajeMock = 1L;
        servicioHerreria = new ServicioHerreriaImpl(repositorioInventario, repositorioPersonaje);
        personajeMock = mock(Personaje.class);
        estadisticasMock = new Estadisticas();
        estadisticasMock.setAgilidad(0);
        estadisticasMock.setInteligencia(0);
        estadisticasMock.setFuerza(0);
        estadisticasMock.setArmadura(0);
        when(personajeMock.getOro()).thenReturn(50);
        when(repositorioInventario.obtenerInventario(idPersonajeMock)).thenReturn(listaEquipamientosMock);
        when(mejoraDtoMock.getEquipamiento()).thenReturn(new Arma("espada", estadisticasMock, new Guerrero(), 100, 100, 100, 0, false));
        when(repositorioPersonaje.buscarPersonaje(idPersonajeMock)).thenReturn(personajeMock);
    }

    @Test
    public void queSePuedanObtenerLosTodosLosEquipamientosDelPersonaje() {

        List<Equipamiento> inventario = servicioHerreria.obtenerInventario(1L);

        assertThat(inventario, hasItems());
    }

    @Test
    public void queSePuedaRealizarUnaMejoraDeEquipamientoSiLaCantidadDeOroEsSuficiente() throws NivelDeEquipamientoMaximoException, OroInsuficienteException {

        Integer oroUsuarioSuficiente = 200;
        when(mejoraDtoMock.getOroUsuario()).thenReturn(oroUsuarioSuficiente);

        servicioHerreria.mejorarEquipamiento(mejoraDtoMock.getEquipamiento(), mejoraDtoMock.getOroUsuario());

        Integer valorFuerzaEsperado = 4;
        Integer valorFuerzaObtenido = mejoraDtoMock.getEquipamiento().getStats().getFuerza();

        assertThat(valorFuerzaObtenido, equalTo(valorFuerzaEsperado));
    }

    @Test
    public void queNoSePuedaRealizarUnaMejoraDeEquipamientoSiLaCantidadDeOroEsInsuficienteYLanceUna() throws NivelDeEquipamientoMaximoException {

        Integer oroUsuarioInsuficiente = 5;
        when(mejoraDtoMock.getOroUsuario()).thenReturn(oroUsuarioInsuficiente);

        assertThrows(OroInsuficienteException.class, () -> {
            servicioHerreria.mejorarEquipamiento(mejoraDtoMock.getEquipamiento(), mejoraDtoMock.getOroUsuario());
        });
    }

    @Test
    public void quePuedaObtenerElOroDelPersonaje() {

        Integer oroObtenido = servicioHerreria.obtenerOroDelPersonaje(idPersonajeMock);
        Integer oroEsperado = 50;

        assertThat(oroEsperado, equalTo(oroObtenido));
    }

    @Test
    public void queSePuedaCrearUnEquipamientoArma() {

        String nombre = "espada";
        Estadisticas stats = new Estadisticas();
        Rol rol = new Guerrero();
        Integer costoCompra = 100;
        Integer costoVenta = 100;
        Integer costoMejora = 100;
        Integer nivel = 0;
        Boolean equipado = false;

        Equipamiento equipamiento = new Arma(nombre, stats, rol, costoCompra, costoVenta, costoMejora, nivel, equipado);

        assertThat(equipamiento, notNullValue());
    }

    @Test
    public void queAlMejorarCualquierEquipamientoSubaDeNivel() throws NivelDeEquipamientoMaximoException, OroInsuficienteException {

        Integer oroUsuarioSuficiente = 200;
        Equipamiento armaGuerrero = new Arma();
        Estadisticas stats = new Estadisticas();
        Rol rol = new Guerrero();
        stats.setFuerza(0);
        stats.setArmadura(0);
        stats.setInteligencia(0);
        stats.setAgilidad(0);
        armaGuerrero.setStats(stats);
        armaGuerrero.setRol(rol);
        armaGuerrero.setCostoMejora(1);
        armaGuerrero.setNivel(0);

        servicioHerreria.mejorarEquipamiento(armaGuerrero, oroUsuarioSuficiente);

        Integer nivelEsperado = 1;
        Integer nivelObtenido = armaGuerrero.getNivel();
        assertThat(nivelObtenido, equalTo(nivelEsperado));
    }

    @Test
    public void queAlIntentarMejorarUnEquipamientoDeCualquierTipoNivel5MelanceUnaNivelDeEquipamientoMaximoException() throws NivelDeEquipamientoMaximoException {

        Integer oroUsuarioSuficiente = 200;
        Equipamiento armaGuerrero = new Arma();
        Estadisticas stats = new Estadisticas();
        Rol rol = new Guerrero();
        stats.setFuerza(0);
        stats.setArmadura(0);
        stats.setInteligencia(0);
        stats.setAgilidad(0);
        armaGuerrero.setStats(stats);
        armaGuerrero.setRol(rol);
        armaGuerrero.setCostoMejora(1);
        armaGuerrero.setNivel(5);
        assertThrows(NivelDeEquipamientoMaximoException.class, () -> {
            servicioHerreria.mejorarEquipamiento(armaGuerrero, oroUsuarioSuficiente);
        });
    }

    @Test
    public void queAlMejorarUnEquipamientoArmaDeRolGuerreroSeMejorenSoloLasEstadisticasDeAtaque() throws NivelDeEquipamientoMaximoException, OroInsuficienteException {
        Integer oroUsuarioSuficiente = 200;
        Equipamiento armaGuerrero = new Arma();
        Estadisticas stats = new Estadisticas();
        Rol rol = new Guerrero();
        stats.setFuerza(0);
        stats.setArmadura(0);
        stats.setInteligencia(0);
        stats.setAgilidad(0);
        armaGuerrero.setStats(stats);
        armaGuerrero.setRol(rol);
        armaGuerrero.setCostoMejora(1);
        armaGuerrero.setNivel(0);

        servicioHerreria.mejorarEquipamiento(armaGuerrero, oroUsuarioSuficiente);

        Integer valorFuerzaEsperado = 4;
        Integer valorFuerzaObtenido = armaGuerrero.getStats().getFuerza();

        Integer valorArmaduraEsperado = 0;
        Integer valorArmaduraObtenido = armaGuerrero.getStats().getArmadura();

        Integer valorInteligenciaEsperado = 0;
        Integer valorInteligenciaObtenido = armaGuerrero.getStats().getInteligencia();

        Integer valorAgilidadEsperado = 0;
        Integer valorAgilidadObtenido = armaGuerrero.getStats().getAgilidad();

        assertThat(valorFuerzaObtenido, equalTo(valorFuerzaEsperado));
        assertThat(valorArmaduraEsperado, equalTo(valorArmaduraObtenido));
        assertThat(valorInteligenciaEsperado, equalTo(valorInteligenciaObtenido));
        assertThat(valorAgilidadEsperado, equalTo(valorAgilidadObtenido));

    }

    @Test
    public void queAlMejorarUnEquipamientoEscudoDeRolGuerreroSeMejorenSoloLasEstadisticasDeDefensa() throws NivelDeEquipamientoMaximoException, OroInsuficienteException {

        Integer oroUsuarioSuficiente = 200;
        Equipamiento escudoGuerrero = new Escudo();
        Estadisticas stats = new Estadisticas();
        Rol rol = new Guerrero();
        stats.setFuerza(0);
        stats.setArmadura(0);
        stats.setInteligencia(0);
        stats.setAgilidad(0);
        escudoGuerrero.setStats(stats);
        escudoGuerrero.setRol(rol);
        escudoGuerrero.setCostoMejora(1);
        escudoGuerrero.setNivel(0);

        servicioHerreria.mejorarEquipamiento(escudoGuerrero, oroUsuarioSuficiente);

        Integer valorFuerzaEsperado = 0;
        Integer valorFuerzaObtenido = escudoGuerrero.getStats().getFuerza();

        Integer valorArmaduraEsperado = 3;
        Integer valorArmaduraObtenido = escudoGuerrero.getStats().getArmadura();

        Integer valorInteligenciaEsperado = 0;
        Integer valorInteligenciaObtenido = escudoGuerrero.getStats().getInteligencia();

        Integer valorAgilidadEsperado = 0;
        Integer valorAgilidadObtenido = escudoGuerrero.getStats().getAgilidad();

        assertThat(valorFuerzaObtenido, equalTo(valorFuerzaEsperado));
        assertThat(valorArmaduraEsperado, equalTo(valorArmaduraObtenido));
        assertThat(valorInteligenciaEsperado, equalTo(valorInteligenciaObtenido));
        assertThat(valorAgilidadEsperado, equalTo(valorAgilidadObtenido));

    }

    @Test
    public void queAlMejorarUnEquipamientoCascoDeRolGuerreroSeMejorenSoloLasEstadisticasDeArmadura() throws NivelDeEquipamientoMaximoException, OroInsuficienteException {

        Integer oroUsuarioSuficiente = 200;
        Equipamiento cascoGuerrero = new Casco();
        Estadisticas stats = new Estadisticas();
        Rol rol = new Guerrero();
        stats.setFuerza(0);
        stats.setArmadura(0);
        stats.setInteligencia(0);
        stats.setAgilidad(0);
        cascoGuerrero.setStats(stats);
        cascoGuerrero.setRol(rol);
        cascoGuerrero.setCostoMejora(1);
        cascoGuerrero.setNivel(0);

        servicioHerreria.mejorarEquipamiento(cascoGuerrero, oroUsuarioSuficiente);

        Integer valorFuerzaEsperado = 0;
        Integer valorFuerzaObtenido = cascoGuerrero.getStats().getFuerza();

        Integer valorArmaduraEsperado = 2;
        Integer valorArmaduraObtenido = cascoGuerrero.getStats().getArmadura();

        Integer valorInteligenciaEsperado = 0;
        Integer valorInteligenciaObtenido = cascoGuerrero.getStats().getInteligencia();

        Integer valorAgilidadEsperado = 0;
        Integer valorAgilidadObtenido = cascoGuerrero.getStats().getAgilidad();

        assertThat(valorFuerzaObtenido, equalTo(valorFuerzaEsperado));
        assertThat(valorArmaduraEsperado, equalTo(valorArmaduraObtenido));
        assertThat(valorInteligenciaEsperado, equalTo(valorInteligenciaObtenido));
        assertThat(valorAgilidadEsperado, equalTo(valorAgilidadObtenido));

    }

    @Test
    public void queAlMejorarUnEquipamientoPecheraDeRolGuerreroSeMejorenSoloLasEstadisticasDeArmadura() throws NivelDeEquipamientoMaximoException, OroInsuficienteException {

        Integer oroUsuarioSuficiente = 200;
        Equipamiento pecheraGuerrero = new Pechera();
        Estadisticas stats = new Estadisticas();
        Rol rol = new Guerrero();
        stats.setFuerza(0);
        stats.setArmadura(0);
        stats.setInteligencia(0);
        stats.setAgilidad(0);
        pecheraGuerrero.setStats(stats);
        pecheraGuerrero.setRol(rol);
        pecheraGuerrero.setCostoMejora(1);
        pecheraGuerrero.setNivel(0);

        servicioHerreria.mejorarEquipamiento(pecheraGuerrero, oroUsuarioSuficiente);


        Integer valorFuerzaEsperado = 0;
        Integer valorFuerzaObtenido = pecheraGuerrero.getStats().getFuerza();

        Integer valorArmaduraEsperado = 4;
        Integer valorArmaduraObtenido = pecheraGuerrero.getStats().getArmadura();

        Integer valorInteligenciaEsperado = 0;
        Integer valorInteligenciaObtenido = pecheraGuerrero.getStats().getInteligencia();

        Integer valorAgilidadEsperado = 0;
        Integer valorAgilidadObtenido = pecheraGuerrero.getStats().getAgilidad();

        assertThat(valorFuerzaObtenido, equalTo(valorFuerzaEsperado));
        assertThat(valorArmaduraEsperado, equalTo(valorArmaduraObtenido));
        assertThat(valorInteligenciaEsperado, equalTo(valorInteligenciaObtenido));
        assertThat(valorAgilidadEsperado, equalTo(valorAgilidadObtenido));

    }

    @Test
    public void queAlMejorarUnEquipamientoPantalonesDeRolGuerreroSeMejorenSoloLasEstadisticasDeArmadura() throws NivelDeEquipamientoMaximoException, OroInsuficienteException {

        Integer oroUsuarioSuficiente = 200;
        Equipamiento pantalonesGuerrero = new Pantalones();
        Estadisticas stats = new Estadisticas();
        Rol rol = new Guerrero();
        stats.setFuerza(0);
        stats.setArmadura(0);
        stats.setInteligencia(0);
        stats.setAgilidad(0);
        pantalonesGuerrero.setStats(stats);
        pantalonesGuerrero.setRol(rol);
        pantalonesGuerrero.setCostoMejora(1);
        pantalonesGuerrero.setNivel(0);

        servicioHerreria.mejorarEquipamiento(pantalonesGuerrero, oroUsuarioSuficiente);


        Integer valorFuerzaEsperado = 0;
        Integer valorFuerzaObtenido = pantalonesGuerrero.getStats().getFuerza();

        Integer valorArmaduraEsperado = 2;
        Integer valorArmaduraObtenido = pantalonesGuerrero.getStats().getArmadura();

        Integer valorInteligenciaEsperado = 0;
        Integer valorInteligenciaObtenido = pantalonesGuerrero.getStats().getInteligencia();

        Integer valorAgilidadEsperado = 0;
        Integer valorAgilidadObtenido = pantalonesGuerrero.getStats().getAgilidad();

        assertThat(valorFuerzaObtenido, equalTo(valorFuerzaEsperado));
        assertThat(valorArmaduraEsperado, equalTo(valorArmaduraObtenido));
        assertThat(valorInteligenciaEsperado, equalTo(valorInteligenciaObtenido));
        assertThat(valorAgilidadEsperado, equalTo(valorAgilidadObtenido));

    }

    @Test
    public void queAlMejorarUnEquipamientoBotasDeRolGuerreroSeMejorenSoloLasEstadisticasDeAgilidadYArmadura() throws NivelDeEquipamientoMaximoException, OroInsuficienteException {

        Integer oroUsuarioSuficiente = 200;
        Equipamiento botasGuerrero = new Botas();
        Estadisticas stats = new Estadisticas();
        Rol rol = new Guerrero();
        stats.setFuerza(0);
        stats.setArmadura(0);
        stats.setInteligencia(0);
        stats.setAgilidad(0);
        botasGuerrero.setStats(stats);
        botasGuerrero.setRol(rol);
        botasGuerrero.setCostoMejora(1);
        botasGuerrero.setNivel(0);

        servicioHerreria.mejorarEquipamiento(botasGuerrero, oroUsuarioSuficiente);


        Integer valorFuerzaEsperado = 0;
        Integer valorFuerzaObtenido = botasGuerrero.getStats().getFuerza();

        Integer valorArmaduraEsperado = 2;
        Integer valorArmaduraObtenido = botasGuerrero.getStats().getArmadura();

        Integer valorInteligenciaEsperado = 0;
        Integer valorInteligenciaObtenido = botasGuerrero.getStats().getInteligencia();

        Integer valorAgilidadEsperado = 1;
        Integer valorAgilidadObtenido = botasGuerrero.getStats().getAgilidad();

        assertThat(valorFuerzaObtenido, equalTo(valorFuerzaEsperado));
        assertThat(valorArmaduraEsperado, equalTo(valorArmaduraObtenido));
        assertThat(valorInteligenciaEsperado, equalTo(valorInteligenciaObtenido));
        assertThat(valorAgilidadEsperado, equalTo(valorAgilidadObtenido));

    }

    @Test
    public void queAlMejorarUnEquipamientoArmaDeRolMagoSeMejorenSoloLasEstadisticasDeInteligencia() throws NivelDeEquipamientoMaximoException, OroInsuficienteException {

        Integer oroUsuarioSuficiente = 200;
        Equipamiento armaMago = new Arma();
        Estadisticas stats = new Estadisticas();
        Rol rol = new Mago();
        stats.setFuerza(0);
        stats.setArmadura(0);
        stats.setInteligencia(0);
        stats.setAgilidad(0);
        armaMago.setStats(stats);
        armaMago.setRol(rol);
        armaMago.setCostoMejora(1);
        armaMago.setNivel(0);

        servicioHerreria.mejorarEquipamiento(armaMago, oroUsuarioSuficiente);

        Integer valorFuerzaEsperado = 0;
        Integer valorFuerzaObtenido = armaMago.getStats().getFuerza();

        Integer valorArmaduraEsperado = 0;
        Integer valorArmaduraObtenido = armaMago.getStats().getArmadura();

        Integer valorInteligenciaEsperado = 3;
        Integer valorInteligenciaObtenido = armaMago.getStats().getInteligencia();

        Integer valorAgilidadEsperado = 0;
        Integer valorAgilidadObtenido = armaMago.getStats().getAgilidad();

        assertThat(valorFuerzaObtenido, equalTo(valorFuerzaEsperado));
        assertThat(valorArmaduraEsperado, equalTo(valorArmaduraObtenido));
        assertThat(valorInteligenciaEsperado, equalTo(valorInteligenciaObtenido));
        assertThat(valorAgilidadEsperado, equalTo(valorAgilidadObtenido));

    }

    @Test
    public void queAlMejorarUnEquipamientoEscudoDeRolMagoSeMejorenSoloLasEstadisticasDeDefensa() throws NivelDeEquipamientoMaximoException, OroInsuficienteException {

        Integer oroUsuarioSuficiente = 200;
        Equipamiento escudoMago = new Escudo();
        Estadisticas stats = new Estadisticas();
        Rol rol = new Mago();
        stats.setFuerza(0);
        stats.setArmadura(0);
        stats.setInteligencia(0);
        stats.setAgilidad(0);
        escudoMago.setStats(stats);
        escudoMago.setRol(rol);
        escudoMago.setCostoMejora(1);
        escudoMago.setNivel(0);

        servicioHerreria.mejorarEquipamiento(escudoMago, oroUsuarioSuficiente);

        Integer valorFuerzaEsperado = 0;
        Integer valorFuerzaObtenido = escudoMago.getStats().getFuerza();

        Integer valorArmaduraEsperado = 2;
        Integer valorArmaduraObtenido = escudoMago.getStats().getArmadura();

        Integer valorInteligenciaEsperado = 0;
        Integer valorInteligenciaObtenido = escudoMago.getStats().getInteligencia();

        Integer valorAgilidadEsperado = 0;
        Integer valorAgilidadObtenido = escudoMago.getStats().getAgilidad();

        assertThat(valorFuerzaObtenido, equalTo(valorFuerzaEsperado));
        assertThat(valorArmaduraEsperado, equalTo(valorArmaduraObtenido));
        assertThat(valorInteligenciaEsperado, equalTo(valorInteligenciaObtenido));
        assertThat(valorAgilidadEsperado, equalTo(valorAgilidadObtenido));

    }

    @Test
    public void queAlMejorarUnEquipamientoCascoDeRolMagoSeMejorenSoloLasEstadisticasDeArmadura() throws NivelDeEquipamientoMaximoException, OroInsuficienteException {

        Integer oroUsuarioSuficiente = 200;
        Equipamiento cascoMago = new Casco();
        Estadisticas stats = new Estadisticas();
        Rol rol = new Mago();
        stats.setFuerza(0);
        stats.setArmadura(0);
        stats.setInteligencia(0);
        stats.setAgilidad(0);
        cascoMago.setStats(stats);
        cascoMago.setRol(rol);
        cascoMago.setCostoMejora(1);
        cascoMago.setNivel(0);

        servicioHerreria.mejorarEquipamiento(cascoMago, oroUsuarioSuficiente);

        Integer valorFuerzaEsperado = 0;
        Integer valorFuerzaObtenido = cascoMago.getStats().getFuerza();

        Integer valorArmaduraEsperado = 1;
        Integer valorArmaduraObtenido = cascoMago.getStats().getArmadura();

        Integer valorInteligenciaEsperado = 0;
        Integer valorInteligenciaObtenido = cascoMago.getStats().getInteligencia();

        Integer valorAgilidadEsperado = 0;
        Integer valorAgilidadObtenido = cascoMago.getStats().getAgilidad();

        assertThat(valorFuerzaObtenido, equalTo(valorFuerzaEsperado));
        assertThat(valorArmaduraEsperado, equalTo(valorArmaduraObtenido));
        assertThat(valorInteligenciaEsperado, equalTo(valorInteligenciaObtenido));
        assertThat(valorAgilidadEsperado, equalTo(valorAgilidadObtenido));

    }

    @Test
    public void queAlMejorarUnEquipamientoPecheraDeRolMagoSeMejorenSoloLasEstadisticasDeArmaduraEInteligencia() throws NivelDeEquipamientoMaximoException, OroInsuficienteException {

        Integer oroUsuarioSuficiente = 200;
        Equipamiento pecheraMago = new Pechera();
        Estadisticas stats = new Estadisticas();
        Rol rol = new Mago();
        stats.setFuerza(0);
        stats.setArmadura(0);
        stats.setInteligencia(0);
        stats.setAgilidad(0);
        pecheraMago.setStats(stats);
        pecheraMago.setRol(rol);
        pecheraMago.setCostoMejora(1);
        pecheraMago.setNivel(0);

        servicioHerreria.mejorarEquipamiento(pecheraMago, oroUsuarioSuficiente);

        Integer valorFuerzaEsperado = 0;
        Integer valorFuerzaObtenido = pecheraMago.getStats().getFuerza();

        Integer valorArmaduraEsperado = 2;
        Integer valorArmaduraObtenido = pecheraMago.getStats().getArmadura();

        Integer valorInteligenciaEsperado = 2;
        Integer valorInteligenciaObtenido = pecheraMago.getStats().getInteligencia();

        Integer valorAgilidadEsperado = 0;
        Integer valorAgilidadObtenido = pecheraMago.getStats().getAgilidad();

        assertThat(valorFuerzaObtenido, equalTo(valorFuerzaEsperado));
        assertThat(valorArmaduraEsperado, equalTo(valorArmaduraObtenido));
        assertThat(valorInteligenciaEsperado, equalTo(valorInteligenciaObtenido));
        assertThat(valorAgilidadEsperado, equalTo(valorAgilidadObtenido));

    }

    @Test
    public void queAlMejorarUnEquipamientoPantalonesDeRolMagoSeMejorenSoloLasEstadisticasDeArmadura() throws NivelDeEquipamientoMaximoException, OroInsuficienteException {

        Integer oroUsuarioSuficiente = 200;
        Equipamiento pantalonesMago = new Pantalones();
        Estadisticas stats = new Estadisticas();
        Rol rol = new Mago();
        stats.setFuerza(0);
        stats.setArmadura(0);
        stats.setInteligencia(0);
        stats.setAgilidad(0);
        pantalonesMago.setStats(stats);
        pantalonesMago.setRol(rol);
        pantalonesMago.setCostoMejora(1);
        pantalonesMago.setNivel(0);

        servicioHerreria.mejorarEquipamiento(pantalonesMago, oroUsuarioSuficiente);

        Integer valorFuerzaEsperado = 0;
        Integer valorFuerzaObtenido = pantalonesMago.getStats().getFuerza();

        Integer valorArmaduraEsperado = 1;
        Integer valorArmaduraObtenido = pantalonesMago.getStats().getArmadura();

        Integer valorInteligenciaEsperado = 0;
        Integer valorInteligenciaObtenido = pantalonesMago.getStats().getInteligencia();

        Integer valorAgilidadEsperado = 0;
        Integer valorAgilidadObtenido = pantalonesMago.getStats().getAgilidad();

        assertThat(valorFuerzaObtenido, equalTo(valorFuerzaEsperado));
        assertThat(valorArmaduraEsperado, equalTo(valorArmaduraObtenido));
        assertThat(valorInteligenciaEsperado, equalTo(valorInteligenciaObtenido));
        assertThat(valorAgilidadEsperado, equalTo(valorAgilidadObtenido));

    }

    @Test
    public void queAlMejorarUnEquipamientoBotasDeRolMagoSeMejorenSoloLasEstadisticasDeAgilidadYArmadura() throws NivelDeEquipamientoMaximoException, OroInsuficienteException {

        Integer oroUsuarioSuficiente = 200;
        Equipamiento botasMago = new Botas();
        Estadisticas stats = new Estadisticas();
        Rol rol = new Mago();
        stats.setFuerza(0);
        stats.setArmadura(0);
        stats.setInteligencia(0);
        stats.setAgilidad(0);
        botasMago.setStats(stats);
        botasMago.setRol(rol);
        botasMago.setCostoMejora(1);
        botasMago.setNivel(0);

        servicioHerreria.mejorarEquipamiento(botasMago, oroUsuarioSuficiente);

        Integer valorFuerzaEsperado = 0;
        Integer valorFuerzaObtenido = botasMago.getStats().getFuerza();

        Integer valorArmaduraEsperado = 1;
        Integer valorArmaduraObtenido = botasMago.getStats().getArmadura();

        Integer valorInteligenciaEsperado = 0;
        Integer valorInteligenciaObtenido = botasMago.getStats().getInteligencia();

        Integer valorAgilidadEsperado = 1;
        Integer valorAgilidadObtenido = botasMago.getStats().getAgilidad();

        assertThat(valorFuerzaObtenido, equalTo(valorFuerzaEsperado));
        assertThat(valorArmaduraEsperado, equalTo(valorArmaduraObtenido));
        assertThat(valorInteligenciaEsperado, equalTo(valorInteligenciaObtenido));
        assertThat(valorAgilidadEsperado, equalTo(valorAgilidadObtenido));

    }

    @Test
    public void queAlMejorarUnEquipamientoArmaDeRolBandidoSeMejorenSoloLasEstadisticasDeInteligencia() throws NivelDeEquipamientoMaximoException, OroInsuficienteException {
        Integer oroUsuarioSuficiente = 200;
        Equipamiento armaBandido = new Arma();
        Estadisticas stats = new Estadisticas();
        Rol rol = new Bandido();
        stats.setFuerza(0);
        stats.setArmadura(0);
        stats.setInteligencia(0);
        stats.setAgilidad(0);
        armaBandido.setStats(stats);
        armaBandido.setRol(rol);
        armaBandido.setCostoMejora(1);
        armaBandido.setNivel(0);

        servicioHerreria.mejorarEquipamiento(armaBandido, oroUsuarioSuficiente);

        Integer valorFuerzaEsperado = 1;
        Integer valorFuerzaObtenido = armaBandido.getStats().getFuerza();

        Integer valorArmaduraEsperado = 0;
        Integer valorArmaduraObtenido = armaBandido.getStats().getArmadura();

        Integer valorInteligenciaEsperado = 0;
        Integer valorInteligenciaObtenido = armaBandido.getStats().getInteligencia();

        Integer valorAgilidadEsperado = 3;
        Integer valorAgilidadObtenido = armaBandido.getStats().getAgilidad();

        assertThat(valorFuerzaObtenido, equalTo(valorFuerzaEsperado));
        assertThat(valorArmaduraEsperado, equalTo(valorArmaduraObtenido));
        assertThat(valorInteligenciaEsperado, equalTo(valorInteligenciaObtenido));
        assertThat(valorAgilidadEsperado, equalTo(valorAgilidadObtenido));
    }

    @Test
    public void queAlMejorarUnEquipamientoEscudoDeRolBandidoSeMejorenSoloLasEstadisticasDeDefensa() throws NivelDeEquipamientoMaximoException, OroInsuficienteException {
        Integer oroUsuarioSuficiente = 200;
        Equipamiento escudoBandido = new Escudo();
        Estadisticas stats = new Estadisticas();
        Rol rol = new Bandido();
        stats.setFuerza(0);
        stats.setArmadura(0);
        stats.setInteligencia(0);
        stats.setAgilidad(0);
        escudoBandido.setStats(stats);
        escudoBandido.setRol(rol);
        escudoBandido.setCostoMejora(1);
        escudoBandido.setNivel(0);

        servicioHerreria.mejorarEquipamiento(escudoBandido, oroUsuarioSuficiente);

        Integer valorFuerzaEsperado = 0;
        Integer valorFuerzaObtenido = escudoBandido.getStats().getFuerza();

        Integer valorArmaduraEsperado = 2;
        Integer valorArmaduraObtenido = escudoBandido.getStats().getArmadura();

        Integer valorInteligenciaEsperado = 0;
        Integer valorInteligenciaObtenido = escudoBandido.getStats().getInteligencia();

        Integer valorAgilidadEsperado = 0;
        Integer valorAgilidadObtenido = escudoBandido.getStats().getAgilidad();

        assertThat(valorFuerzaObtenido, equalTo(valorFuerzaEsperado));
        assertThat(valorArmaduraEsperado, equalTo(valorArmaduraObtenido));
        assertThat(valorInteligenciaEsperado, equalTo(valorInteligenciaObtenido));
        assertThat(valorAgilidadEsperado, equalTo(valorAgilidadObtenido));
    }

    @Test
    public void queAlMejorarUnEquipamientoCascoDeRolBandidoSeMejorenSoloLasEstadisticasDeArmadura() throws NivelDeEquipamientoMaximoException, OroInsuficienteException {
        Integer oroUsuarioSuficiente = 200;
        Equipamiento cascoBandido = new Casco();
        Estadisticas stats = new Estadisticas();
        Rol rol = new Bandido();
        stats.setFuerza(0);
        stats.setArmadura(0);
        stats.setInteligencia(0);
        stats.setAgilidad(0);
        cascoBandido.setStats(stats);
        cascoBandido.setRol(rol);
        cascoBandido.setCostoMejora(1);
        cascoBandido.setNivel(0);

        servicioHerreria.mejorarEquipamiento(cascoBandido, oroUsuarioSuficiente);

        Integer valorFuerzaEsperado = 0;
        Integer valorFuerzaObtenido = cascoBandido.getStats().getFuerza();

        Integer valorArmaduraEsperado = 1;
        Integer valorArmaduraObtenido = cascoBandido.getStats().getArmadura();

        Integer valorInteligenciaEsperado = 0;
        Integer valorInteligenciaObtenido = cascoBandido.getStats().getInteligencia();

        Integer valorAgilidadEsperado = 0;
        Integer valorAgilidadObtenido = cascoBandido.getStats().getAgilidad();

        assertThat(valorFuerzaObtenido, equalTo(valorFuerzaEsperado));
        assertThat(valorArmaduraEsperado, equalTo(valorArmaduraObtenido));
        assertThat(valorInteligenciaEsperado, equalTo(valorInteligenciaObtenido));
        assertThat(valorAgilidadEsperado, equalTo(valorAgilidadObtenido));
    }

    @Test
    public void queAlMejorarUnEquipamientoPecheraDeRolBandidoSeMejorenSoloLasEstadisticasDeArmadura() throws NivelDeEquipamientoMaximoException, OroInsuficienteException {
        Integer oroUsuarioSuficiente = 200;
        Equipamiento pecheraBandido = new Pechera();
        Estadisticas stats = new Estadisticas();
        Rol rol = new Bandido();
        stats.setFuerza(0);
        stats.setArmadura(0);
        stats.setInteligencia(0);
        stats.setAgilidad(0);
        pecheraBandido.setStats(stats);
        pecheraBandido.setRol(rol);
        pecheraBandido.setCostoMejora(1);
        pecheraBandido.setNivel(0);

        servicioHerreria.mejorarEquipamiento(pecheraBandido, oroUsuarioSuficiente);

        Integer valorFuerzaEsperado = 0;
        Integer valorFuerzaObtenido = pecheraBandido.getStats().getFuerza();

        Integer valorArmaduraEsperado = 2;
        Integer valorArmaduraObtenido = pecheraBandido.getStats().getArmadura();

        Integer valorInteligenciaEsperado = 0;
        Integer valorInteligenciaObtenido = pecheraBandido.getStats().getInteligencia();

        Integer valorAgilidadEsperado = 0;
        Integer valorAgilidadObtenido = pecheraBandido.getStats().getAgilidad();

        assertThat(valorFuerzaObtenido, equalTo(valorFuerzaEsperado));
        assertThat(valorArmaduraEsperado, equalTo(valorArmaduraObtenido));
        assertThat(valorInteligenciaEsperado, equalTo(valorInteligenciaObtenido));
        assertThat(valorAgilidadEsperado, equalTo(valorAgilidadObtenido));
    }

    @Test
    public void queAlMejorarUnEquipamientoPantalonesDeRolBandidoSeMejorenSoloLasEstadisticasDeArmadura() throws NivelDeEquipamientoMaximoException, OroInsuficienteException {
        Integer oroUsuarioSuficiente = 200;
        Equipamiento pantalonesBandido = new Pantalones();
        Estadisticas stats = new Estadisticas();
        Rol rol = new Bandido();
        stats.setFuerza(0);
        stats.setArmadura(0);
        stats.setInteligencia(0);
        stats.setAgilidad(0);
        pantalonesBandido.setStats(stats);
        pantalonesBandido.setRol(rol);
        pantalonesBandido.setCostoMejora(1);
        pantalonesBandido.setNivel(0);

        servicioHerreria.mejorarEquipamiento(pantalonesBandido, oroUsuarioSuficiente);

        Integer valorFuerzaEsperado = 0;
        Integer valorFuerzaObtenido = pantalonesBandido.getStats().getFuerza();

        Integer valorArmaduraEsperado = 1;
        Integer valorArmaduraObtenido = pantalonesBandido.getStats().getArmadura();

        Integer valorInteligenciaEsperado = 0;
        Integer valorInteligenciaObtenido = pantalonesBandido.getStats().getInteligencia();

        Integer valorAgilidadEsperado = 0;
        Integer valorAgilidadObtenido = pantalonesBandido.getStats().getAgilidad();

        assertThat(valorFuerzaObtenido, equalTo(valorFuerzaEsperado));
        assertThat(valorArmaduraEsperado, equalTo(valorArmaduraObtenido));
        assertThat(valorInteligenciaEsperado, equalTo(valorInteligenciaObtenido));
        assertThat(valorAgilidadEsperado, equalTo(valorAgilidadObtenido));
    }

    @Test
    public void queAlMejorarUnEquipamientoBotasDeRolBandidoSeMejorenSoloLasEstadisticasDeAgilidadYArmadura() throws NivelDeEquipamientoMaximoException, OroInsuficienteException {
        Integer oroUsuarioSuficiente = 200;
        Equipamiento botasBandido = new Botas();
        Estadisticas stats = new Estadisticas();
        Rol rol = new Bandido();
        stats.setFuerza(0);
        stats.setArmadura(0);
        stats.setInteligencia(0);
        stats.setAgilidad(0);
        botasBandido.setStats(stats);
        botasBandido.setRol(rol);
        botasBandido.setCostoMejora(1);
        botasBandido.setNivel(0);

        servicioHerreria.mejorarEquipamiento(botasBandido, oroUsuarioSuficiente);

        Integer valorFuerzaEsperado = 0;
        Integer valorFuerzaObtenido = botasBandido.getStats().getFuerza();

        Integer valorArmaduraEsperado = 1;
        Integer valorArmaduraObtenido = botasBandido.getStats().getArmadura();

        Integer valorInteligenciaEsperado = 0;
        Integer valorInteligenciaObtenido = botasBandido.getStats().getInteligencia();

        Integer valorAgilidadEsperado = 2;
        Integer valorAgilidadObtenido = botasBandido.getStats().getAgilidad();

        assertThat(valorFuerzaObtenido, equalTo(valorFuerzaEsperado));
        assertThat(valorArmaduraEsperado, equalTo(valorArmaduraObtenido));
        assertThat(valorInteligenciaEsperado, equalTo(valorInteligenciaObtenido));
        assertThat(valorAgilidadEsperado, equalTo(valorAgilidadObtenido));
    }

}
