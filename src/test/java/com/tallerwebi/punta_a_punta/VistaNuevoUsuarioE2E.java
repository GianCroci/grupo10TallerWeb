package com.tallerwebi.punta_a_punta;

import com.microsoft.playwright.*;
import com.tallerwebi.punta_a_punta.vistas.VistaLogin;
import com.tallerwebi.punta_a_punta.vistas.VistaNuevoUsuario;
import org.junit.jupiter.api.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsStringIgnoringCase;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;

public class VistaNuevoUsuarioE2E {
    static Playwright playwright;
    static Browser browser;
    BrowserContext context;
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
        vistaNuevoUsuario = new VistaNuevoUsuario(page);
    }

    @AfterEach
    void cerrarContexto() {
        context.close();
    }


    @Test
    void deberiaCrearUnUsuarioAlIngresarUnMailNuevoYContraseniaValidadYNavegarACreacionPersonaje() {
        vistaNuevoUsuario.escribirEMAIL("dam@unlam.edu.ar");
        vistaNuevoUsuario.escribirClave("unlam");
        vistaNuevoUsuario.darClickEnIniciarSesion();
        String url = vistaNuevoUsuario.obtenerURLActual();
        assertThat(url, containsStringIgnoringCase("/spring/creacion-personaje"));
    }

}
