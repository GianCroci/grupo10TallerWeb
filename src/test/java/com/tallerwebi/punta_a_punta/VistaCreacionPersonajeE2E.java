package com.tallerwebi.punta_a_punta;

import com.microsoft.playwright.*;
import com.tallerwebi.punta_a_punta.vistas.VistaCreacionPersonaje;
import com.tallerwebi.punta_a_punta.vistas.VistaLogin;
import com.tallerwebi.punta_a_punta.vistas.VistaNuevoUsuario;
import org.junit.jupiter.api.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsStringIgnoringCase;

public class VistaCreacionPersonajeE2E {

    static Playwright playwright;
    static Browser browser;
    BrowserContext context;
    VistaCreacionPersonaje vistaCreacionPersonaje;
    VistaNuevoUsuario vistaNuevoUsuario;

    @BeforeAll
    static void abrirNavegador() {
        playwright = Playwright.create();
        //browser = playwright.chromium().launch();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(50));

    }

    @AfterAll
    static void cerrarNavegador() {
        playwright.close();
    }

    @BeforeEach
    void crearContextoYPagina() {
        context = browser.newContext();
        Page page = context.newPage();

        // Hacer login primero
        vistaNuevoUsuario = new VistaNuevoUsuario(page);
        vistaNuevoUsuario.escribirEMAIL("gian@unlam.edu.ar");
        vistaNuevoUsuario.escribirClave("test");
        vistaNuevoUsuario.darClickEnIniciarSesion();

        // Crear vista de creación personaje en la misma página
        vistaCreacionPersonaje = new VistaCreacionPersonaje(page);
    }

    @AfterEach
    void cerrarContexto() {
        context.close();
    }

    @Test
    void deberiaEscribirElNombreDelPersonajeElegirAlMagoCrearElPersonajeYRedirigirAHome() {

        vistaCreacionPersonaje.escribirNombre("Legolas");
        vistaCreacionPersonaje.darClickEnFlechaDerechaDeSeleccionDePj();
        vistaCreacionPersonaje.darClickEnFlechaDerechaDeSeleccionDePj();
        vistaCreacionPersonaje.darClickEnCrearPersonaje();
        String url = vistaCreacionPersonaje.obtenerURLActual();
        assertThat(url, containsStringIgnoringCase("/spring/home"));
    }

    @Test
    void deberiaEscribirElNombreDelPersonajeElegirALaBandidaCrearElPersonajeYRedirigirAHome() {

        vistaCreacionPersonaje.escribirNombre("Legolas");
        vistaCreacionPersonaje.darClickEnFlechaIzquierdaDeSeleccionDePj();
        vistaCreacionPersonaje.darClickEnCrearPersonaje();
        String url = vistaCreacionPersonaje.obtenerURLActual();
        assertThat(url, containsStringIgnoringCase("/spring/home"));
    }

    @Test
    void deberiaNoDejarteCrearElPersonajeSiNoLeAsignasUnNombre() {
        vistaCreacionPersonaje.escribirNombre("");
        vistaCreacionPersonaje.darClickEnFlechaDerechaDeSeleccionDePj();
        vistaCreacionPersonaje.darClickEnFlechaDerechaDeSeleccionDePj();
        vistaCreacionPersonaje.darClickEnCrearPersonaje();
        String url = vistaCreacionPersonaje.obtenerURLActual();
        assertThat(url, containsStringIgnoringCase("/spring/creacion-personaje"));
    }
}
