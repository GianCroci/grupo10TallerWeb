package com.tallerwebi.punta_a_punta;

import com.microsoft.playwright.*;
import com.tallerwebi.punta_a_punta.vistas.VistaCreacionPersonaje;
import com.tallerwebi.punta_a_punta.vistas.VistaLogin;
import org.junit.jupiter.api.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsStringIgnoringCase;

public class VistaCreacionPersonajeE2E {

    static Playwright playwright;
    static Browser browser;
    BrowserContext context;
    VistaCreacionPersonaje vistaCreacionPersonaje;
    private VistaLogin vistaLogin;

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
        vistaCreacionPersonaje = new VistaCreacionPersonaje(page);
    }

    @AfterEach
    void cerrarContexto() {
        context.close();
    }

    @Test
    void deberiaEscribirElNombreDelPersonaje() {
        vistaLogin.escribirEMAIL("test@unlam.edu.ar");
        vistaLogin.escribirClave("test");
        vistaLogin.darClickEnIniciarSesion();

        vistaCreacionPersonaje.escribirNombre("Legolas");


        String url = vistaCreacionPersonaje.obtenerURLActual();
        assertThat(url, containsStringIgnoringCase("/spring/creacion-personaje"));
    }
}
