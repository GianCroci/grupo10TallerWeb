package com.tallerwebi.dominio;


import com.tallerwebi.dominio.entidad.*;
import com.tallerwebi.dominio.excepcion.InventarioVacioException;
import com.tallerwebi.dominio.excepcion.NivelDeEquipamientoMaximoException;
import com.tallerwebi.dominio.excepcion.OroInsuficienteException;
import com.tallerwebi.dominio.interfaz.repositorio.RepositorioInventario;
import com.tallerwebi.dominio.interfaz.repositorio.RepositorioPersonaje;
import com.tallerwebi.dominio.interfaz.servicio.ServicioHerreria;
import com.tallerwebi.dominio.interfaz.servicio.ServicioTaberna;
import com.tallerwebi.presentacion.MejoraDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class ServicioHerreriaTest {

    private RepositorioInventario repositorioInventario;
    private ServicioHerreria servicioHerreria;
    private MejoraDto mejoraDtoMock;
    private RepositorioPersonaje repositorioPersonaje;
    private ServicioTaberna servicioTaberna;
    private Long idPersonajeMock;
    private Personaje personajeMock;
    private Estadisticas estadisticasMock;
    private Long idEquipamientoMock;
    private Equipamiento equipamientoMock;
    private Equipamiento equipamientoMock2;

    @BeforeEach
    public void init() {
        repositorioInventario = mock(RepositorioInventario.class);
        repositorioPersonaje = mock(RepositorioPersonaje.class);
        mejoraDtoMock = mock(MejoraDto.class);
        idPersonajeMock = 1L;
        idEquipamientoMock = 1L;
        servicioHerreria = new ServicioHerreriaImpl(repositorioInventario, repositorioPersonaje);
        personajeMock = mock(Personaje.class);
        estadisticasMock = new Estadisticas();
        estadisticasMock.setAgilidad(0);
        estadisticasMock.setInteligencia(0);
        estadisticasMock.setFuerza(0);
        estadisticasMock.setArmadura(0);
        equipamientoMock = new Arma("a", estadisticasMock,new Guerrero(), 10, 10, 50, 0, true );
        equipamientoMock2 = new Arma("z", estadisticasMock,new Guerrero(), 10, 10, 50, 0, true );
        when(personajeMock.getOro()).thenReturn(50);
        when(mejoraDtoMock.getIdEquipamiento()).thenReturn(idEquipamientoMock);
        when(repositorioPersonaje.buscarPersonaje(idPersonajeMock)).thenReturn(personajeMock);
        doNothing().when(repositorioInventario).modificarEquipamiento(equipamientoMock);
        doNothing().when(repositorioPersonaje).modificar(personajeMock);
    }

    @Test
    public void queSePuedanObtenerLosTodosLosEquipamientosDelPersonajeOrdenadosPorNombre() throws InventarioVacioException {
        List<Equipamiento> listaEquipamientosConObjetosMock = new ArrayList<>();
        listaEquipamientosConObjetosMock.add(equipamientoMock2);
        listaEquipamientosConObjetosMock.add(equipamientoMock);
        when(repositorioInventario.obtenerInventario(idPersonajeMock)).thenReturn(listaEquipamientosConObjetosMock);
        List<Equipamiento> inventario = servicioHerreria.obtenerInventario(1L);

        assertThat(inventario.get(0).getNombre(), equalToIgnoringCase("a"));
        assertThat(inventario.get(1).getNombre(), equalToIgnoringCase("z"));
    }

    @Test
    public void queSiElInventarioObtenidoEstaVacioLanceUnaInventarioVacioException() throws InventarioVacioException {
        List<Equipamiento> listaEquipamientosVaciaMock = new ArrayList<>();
        when(repositorioInventario.obtenerInventario(idPersonajeMock)).thenReturn(listaEquipamientosVaciaMock);


        assertThrows(InventarioVacioException.class, () -> {
            List<Equipamiento> inventario = servicioHerreria.obtenerInventario(1L);
        });
    }

    @Test
    public void queSePuedaRealizarUnaMejoraDeEquipamientoSiLaCantidadDeOroEsSuficiente() throws NivelDeEquipamientoMaximoException, OroInsuficienteException {
        //when(servicioTaberna.getCervezasInvitadas(PersonajeTaberna.HERRERO)).thenReturn(5);
        Integer oroUsuarioSuficiente = 200;
        when(mejoraDtoMock.getOroUsuario()).thenReturn(oroUsuarioSuficiente);

        when(repositorioInventario.obtenerEquipamientoPorId(mejoraDtoMock.getIdEquipamiento())).thenReturn(equipamientoMock);

        servicioHerreria.mejorarEquipamiento(mejoraDtoMock.getIdEquipamiento(), mejoraDtoMock.getOroUsuario(), idPersonajeMock);

        Integer valorFuerzaEsperado = 4;
        Integer valorFuerzaObtenido = equipamientoMock.getStats().getFuerza();

        assertThat(valorFuerzaObtenido, equalTo(valorFuerzaEsperado));
        verify(repositorioInventario, times(1)).modificarEquipamiento(equipamientoMock);
    }

    @Test
    public void queNoSePuedaRealizarUnaMejoraDeEquipamientoSiLaCantidadDeOroEsInsuficienteYLanceUna() throws NivelDeEquipamientoMaximoException {
        Integer oroUsuarioInsuficiente = 5;
        when(mejoraDtoMock.getOroUsuario()).thenReturn(oroUsuarioInsuficiente);
        when(repositorioInventario.obtenerEquipamientoPorId(mejoraDtoMock.getIdEquipamiento())).thenReturn(equipamientoMock);

        assertThrows(OroInsuficienteException.class, () -> {
            servicioHerreria.mejorarEquipamiento(mejoraDtoMock.getIdEquipamiento(), mejoraDtoMock.getOroUsuario(), idPersonajeMock);
        });
    }

    @Test
    public void quePuedaObtenerElOroDelPersonaje() {
        when(repositorioPersonaje.buscarOroPersonaje(idPersonajeMock)).thenReturn(50);
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
        Rol rol = new Guerrero();
        armaGuerrero.setStats(estadisticasMock);
        armaGuerrero.setRol(rol);
        armaGuerrero.setCostoCompra(0);
        armaGuerrero.setCostoVenta(0);
        armaGuerrero.setCostoMejora(1);
        armaGuerrero.setNivel(0);
        armaGuerrero.setId(1l);

        when(repositorioInventario.obtenerEquipamientoPorId(mejoraDtoMock.getIdEquipamiento())).thenReturn(armaGuerrero);

        servicioHerreria.mejorarEquipamiento(armaGuerrero.getId(), oroUsuarioSuficiente, idPersonajeMock);

        Integer nivelEsperado = 1;
        Integer nivelObtenido = armaGuerrero.getNivel();
        assertThat(nivelObtenido, equalTo(nivelEsperado));
    }

    @Test
    public void queAlMejorarCualquierEquipamientoSubanTodosLosCostos() throws NivelDeEquipamientoMaximoException, OroInsuficienteException {
        Integer oroUsuarioSuficiente = 200;
        Equipamiento armaGuerrero = new Arma();
        Rol rol = new Guerrero();
        armaGuerrero.setStats(estadisticasMock);
        armaGuerrero.setRol(rol);
        armaGuerrero.setCostoMejora(1);
        armaGuerrero.setNivel(0);
        armaGuerrero.setId(1l);
        armaGuerrero.setCostoMejora(0);
        armaGuerrero.setCostoCompra(0);
        armaGuerrero.setCostoVenta(0);

        when(repositorioInventario.obtenerEquipamientoPorId(mejoraDtoMock.getIdEquipamiento())).thenReturn(armaGuerrero);

        servicioHerreria.mejorarEquipamiento(armaGuerrero.getId(), oroUsuarioSuficiente, idPersonajeMock);

        Integer costoMejoraEsperado = 20;
        Integer costoMejoraObtenido = armaGuerrero.getCostoMejora();

        Integer costoCompraEsperado = 20;
        Integer costoCompraObtenido = armaGuerrero.getCostoCompra();

        Integer costoVentaEsperado = 20;
        Integer costoVentaObtenido = armaGuerrero.getCostoVenta();

        assertThat(costoMejoraObtenido, equalTo(costoMejoraEsperado));
        assertThat(costoCompraObtenido, equalTo(costoCompraEsperado));
        assertThat(costoVentaObtenido, equalTo(costoVentaEsperado));
    }

    @Test
    public void queAlIntentarMejorarUnEquipamientoDeCualquierTipoNivel5MelanceUnaNivelDeEquipamientoMaximoException() throws NivelDeEquipamientoMaximoException {
        Integer oroUsuarioSuficiente = 200;
        Equipamiento armaGuerrero = new Arma();
        Estadisticas stats = new Estadisticas();
        Rol rol = new Guerrero();
        armaGuerrero.setStats(estadisticasMock);
        armaGuerrero.setRol(rol);
        armaGuerrero.setCostoCompra(0);
        armaGuerrero.setCostoVenta(0);
        armaGuerrero.setCostoMejora(1);
        armaGuerrero.setNivel(5);
        armaGuerrero.setId(1l);

        when(repositorioInventario.obtenerEquipamientoPorId(mejoraDtoMock.getIdEquipamiento())).thenReturn(armaGuerrero);

        assertThrows(NivelDeEquipamientoMaximoException.class, () -> {
            servicioHerreria.mejorarEquipamiento(armaGuerrero.getId(), oroUsuarioSuficiente, idPersonajeMock);
        });
    }

    @Test
    public void queAlMejorarUnEquipamientoArmaDeRolGuerreroSeMejorenSoloLasEstadisticasDeAtaque() throws NivelDeEquipamientoMaximoException, OroInsuficienteException {
        Integer oroUsuarioSuficiente = 200;
        Equipamiento armaGuerrero = new Arma();
        Estadisticas stats = new Estadisticas();
        Rol rol = new Guerrero();
        armaGuerrero.setStats(estadisticasMock);
        armaGuerrero.setRol(rol);
        armaGuerrero.setCostoCompra(0);
        armaGuerrero.setCostoVenta(0);
        armaGuerrero.setCostoMejora(1);
        armaGuerrero.setNivel(0);
        armaGuerrero.setId(1l);

        when(repositorioInventario.obtenerEquipamientoPorId(mejoraDtoMock.getIdEquipamiento())).thenReturn(armaGuerrero);

        servicioHerreria.mejorarEquipamiento(armaGuerrero.getId(), oroUsuarioSuficiente, idPersonajeMock);

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
        Rol rol = new Guerrero();
        escudoGuerrero.setStats(estadisticasMock);
        escudoGuerrero.setRol(rol);
        escudoGuerrero.setCostoCompra(0);
        escudoGuerrero.setCostoVenta(0);
        escudoGuerrero.setCostoMejora(1);
        escudoGuerrero.setNivel(0);
        escudoGuerrero.setId(1l);

        when(repositorioInventario.obtenerEquipamientoPorId(mejoraDtoMock.getIdEquipamiento())).thenReturn(escudoGuerrero);

        servicioHerreria.mejorarEquipamiento(escudoGuerrero.getId(), oroUsuarioSuficiente, idPersonajeMock);

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
        Rol rol = new Guerrero();
        cascoGuerrero.setStats(estadisticasMock);
        cascoGuerrero.setRol(rol);
        cascoGuerrero.setCostoCompra(0);
        cascoGuerrero.setCostoVenta(0);
        cascoGuerrero.setCostoMejora(1);
        cascoGuerrero.setNivel(0);
        cascoGuerrero.setId(1l);

        when(repositorioInventario.obtenerEquipamientoPorId(mejoraDtoMock.getIdEquipamiento())).thenReturn(cascoGuerrero);

        servicioHerreria.mejorarEquipamiento(cascoGuerrero.getId(), oroUsuarioSuficiente, idPersonajeMock);

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
        Rol rol = new Guerrero();
        pecheraGuerrero.setStats(estadisticasMock);
        pecheraGuerrero.setRol(rol);
        pecheraGuerrero.setCostoCompra(0);
        pecheraGuerrero.setCostoVenta(0);
        pecheraGuerrero.setCostoMejora(1);
        pecheraGuerrero.setNivel(0);
        pecheraGuerrero.setId(1l);

        when(repositorioInventario.obtenerEquipamientoPorId(mejoraDtoMock.getIdEquipamiento())).thenReturn(pecheraGuerrero);

        servicioHerreria.mejorarEquipamiento(pecheraGuerrero.getId(), oroUsuarioSuficiente, idPersonajeMock);

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
        Rol rol = new Guerrero();
        pantalonesGuerrero.setStats(estadisticasMock);
        pantalonesGuerrero.setRol(rol);
        pantalonesGuerrero.setCostoCompra(0);
        pantalonesGuerrero.setCostoVenta(0);
        pantalonesGuerrero.setCostoMejora(1);
        pantalonesGuerrero.setNivel(0);
        pantalonesGuerrero.setId(1l);

        when(repositorioInventario.obtenerEquipamientoPorId(mejoraDtoMock.getIdEquipamiento())).thenReturn(pantalonesGuerrero);

        servicioHerreria.mejorarEquipamiento(pantalonesGuerrero.getId(), oroUsuarioSuficiente, idPersonajeMock);

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
        Rol rol = new Guerrero();
        botasGuerrero.setStats(estadisticasMock);
        botasGuerrero.setRol(rol);
        botasGuerrero.setCostoCompra(0);
        botasGuerrero.setCostoVenta(0);
        botasGuerrero.setCostoMejora(1);
        botasGuerrero.setNivel(0);
        botasGuerrero.setId(1l);

        when(repositorioInventario.obtenerEquipamientoPorId(mejoraDtoMock.getIdEquipamiento())).thenReturn(botasGuerrero);

        servicioHerreria.mejorarEquipamiento(botasGuerrero.getId(), oroUsuarioSuficiente, idPersonajeMock);


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
        Rol rol = new Mago();
        armaMago.setStats(estadisticasMock);
        armaMago.setRol(rol);
        armaMago.setCostoCompra(0);
        armaMago.setCostoVenta(0);
        armaMago.setCostoMejora(1);
        armaMago.setNivel(0);
        armaMago.setId(1l);

        when(repositorioInventario.obtenerEquipamientoPorId(mejoraDtoMock.getIdEquipamiento())).thenReturn(armaMago);

        servicioHerreria.mejorarEquipamiento(armaMago.getId(), oroUsuarioSuficiente, idPersonajeMock);

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
        Rol rol = new Mago();
        escudoMago.setStats(estadisticasMock);
        escudoMago.setRol(rol);
        escudoMago.setCostoCompra(0);
        escudoMago.setCostoVenta(0);
        escudoMago.setCostoMejora(1);
        escudoMago.setNivel(0);
        escudoMago.setId(1l);

        when(repositorioInventario.obtenerEquipamientoPorId(mejoraDtoMock.getIdEquipamiento())).thenReturn(escudoMago);

        servicioHerreria.mejorarEquipamiento(escudoMago.getId(), oroUsuarioSuficiente, idPersonajeMock);

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
        Rol rol = new Mago();
        cascoMago.setStats(estadisticasMock);
        cascoMago.setRol(rol);
        cascoMago.setCostoCompra(0);
        cascoMago.setCostoVenta(0);
        cascoMago.setCostoMejora(1);
        cascoMago.setNivel(0);
        cascoMago.setId(1l);

        when(repositorioInventario.obtenerEquipamientoPorId(mejoraDtoMock.getIdEquipamiento())).thenReturn(cascoMago);

        servicioHerreria.mejorarEquipamiento(cascoMago.getId(), oroUsuarioSuficiente, idPersonajeMock);

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
        Rol rol = new Mago();
        pecheraMago.setStats(estadisticasMock);
        pecheraMago.setRol(rol);
        pecheraMago.setCostoCompra(0);
        pecheraMago.setCostoVenta(0);
        pecheraMago.setCostoMejora(1);
        pecheraMago.setNivel(0);
        pecheraMago.setId(1l);

        when(repositorioInventario.obtenerEquipamientoPorId(mejoraDtoMock.getIdEquipamiento())).thenReturn(pecheraMago);

        servicioHerreria.mejorarEquipamiento(pecheraMago.getId(), oroUsuarioSuficiente, idPersonajeMock);

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
        Rol rol = new Mago();
        pantalonesMago.setStats(estadisticasMock);
        pantalonesMago.setRol(rol);
        pantalonesMago.setCostoCompra(0);
        pantalonesMago.setCostoVenta(0);
        pantalonesMago.setCostoMejora(1);
        pantalonesMago.setNivel(0);
        pantalonesMago.setId(1l);

        when(repositorioInventario.obtenerEquipamientoPorId(mejoraDtoMock.getIdEquipamiento())).thenReturn(pantalonesMago);

        servicioHerreria.mejorarEquipamiento(pantalonesMago.getId(), oroUsuarioSuficiente, idPersonajeMock);

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
        Rol rol = new Mago();
        botasMago.setStats(estadisticasMock);
        botasMago.setRol(rol);
        botasMago.setCostoCompra(0);
        botasMago.setCostoVenta(0);
        botasMago.setCostoMejora(1);
        botasMago.setNivel(0);
        botasMago.setId(1l);

        when(repositorioInventario.obtenerEquipamientoPorId(mejoraDtoMock.getIdEquipamiento())).thenReturn(botasMago);

        servicioHerreria.mejorarEquipamiento(botasMago.getId(), oroUsuarioSuficiente, idPersonajeMock);

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
        Rol rol = new Bandido();
        armaBandido.setStats(estadisticasMock);
        armaBandido.setRol(rol);
        armaBandido.setCostoCompra(0);
        armaBandido.setCostoVenta(0);
        armaBandido.setCostoMejora(1);
        armaBandido.setNivel(0);
        armaBandido.setId(1l);

        when(repositorioInventario.obtenerEquipamientoPorId(mejoraDtoMock.getIdEquipamiento())).thenReturn(armaBandido);

        servicioHerreria.mejorarEquipamiento(armaBandido.getId(), oroUsuarioSuficiente, idPersonajeMock);

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
        Rol rol = new Bandido();
        escudoBandido.setStats(estadisticasMock);
        escudoBandido.setRol(rol);
        escudoBandido.setCostoCompra(0);
        escudoBandido.setCostoVenta(0);
        escudoBandido.setCostoMejora(1);
        escudoBandido.setNivel(0);
        escudoBandido.setId(1l);

        when(repositorioInventario.obtenerEquipamientoPorId(mejoraDtoMock.getIdEquipamiento())).thenReturn(escudoBandido);

        servicioHerreria.mejorarEquipamiento(escudoBandido.getId(), oroUsuarioSuficiente, idPersonajeMock);

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
        Rol rol = new Bandido();
        cascoBandido.setStats(estadisticasMock);
        cascoBandido.setRol(rol);
        cascoBandido.setCostoCompra(0);
        cascoBandido.setCostoVenta(0);
        cascoBandido.setCostoMejora(1);
        cascoBandido.setNivel(0);
        cascoBandido.setId(1l);

        when(repositorioInventario.obtenerEquipamientoPorId(mejoraDtoMock.getIdEquipamiento())).thenReturn(cascoBandido);

        servicioHerreria.mejorarEquipamiento(cascoBandido.getId(), oroUsuarioSuficiente, idPersonajeMock);

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
        Rol rol = new Bandido();
        pecheraBandido.setStats(estadisticasMock);
        pecheraBandido.setRol(rol);
        pecheraBandido.setCostoCompra(0);
        pecheraBandido.setCostoVenta(0);
        pecheraBandido.setCostoMejora(1);
        pecheraBandido.setNivel(0);
        pecheraBandido.setId(1l);

        when(repositorioInventario.obtenerEquipamientoPorId(mejoraDtoMock.getIdEquipamiento())).thenReturn(pecheraBandido);

        servicioHerreria.mejorarEquipamiento(pecheraBandido.getId(), oroUsuarioSuficiente, idPersonajeMock);

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
        Rol rol = new Bandido();
        pantalonesBandido.setStats(estadisticasMock);
        pantalonesBandido.setRol(rol);
        pantalonesBandido.setCostoCompra(0);
        pantalonesBandido.setCostoVenta(0);
        pantalonesBandido.setCostoMejora(1);
        pantalonesBandido.setNivel(0);
        pantalonesBandido.setId(1l);

        when(repositorioInventario.obtenerEquipamientoPorId(mejoraDtoMock.getIdEquipamiento())).thenReturn(pantalonesBandido);

        servicioHerreria.mejorarEquipamiento(pantalonesBandido.getId(), oroUsuarioSuficiente, idPersonajeMock);

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
        Rol rol = new Bandido();
        botasBandido.setStats(estadisticasMock);
        botasBandido.setRol(rol);
        botasBandido.setCostoCompra(0);
        botasBandido.setCostoVenta(0);
        botasBandido.setCostoMejora(1);
        botasBandido.setNivel(0);
        botasBandido.setId(1l);

        when(repositorioInventario.obtenerEquipamientoPorId(mejoraDtoMock.getIdEquipamiento())).thenReturn(botasBandido);

        servicioHerreria.mejorarEquipamiento(botasBandido.getId(), oroUsuarioSuficiente, idPersonajeMock);

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