package com.tallerwebi.punta_a_punta;

import com.microsoft.playwright.*;
import com.tallerwebi.punta_a_punta.vistas.*;
import org.junit.jupiter.api.*;

import java.net.MalformedURLException;
import java.net.URL;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.matchesPattern;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;

public class VistaHerreriaE2E {

    static Playwright playwright;
    static Browser browser;
    BrowserContext context;
    VistaLogin vistaLogin;
    VistaHerreria vistaHerreria;
    VistaHome vistaHome;
    VistaNuevoUsuario vistaNuevoUsuario;
    VistaCreacionPersonaje vistaCreacionPersonaje;

    @BeforeAll
    static void abrirNavegador() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(50));

    }

    @AfterAll
    static void cerrarNavegador() {
        playwright.close();
    }

    @BeforeEach
    void crearContextoYPagina() {
        ReiniciarDB.limpiarBaseDeDatos();
        context = browser.newContext();
        Page page = context.newPage();
        vistaLogin = new VistaLogin(page);
    }

    @AfterEach
    void cerrarContexto() {
        context.close();
    }

    @Test
    void queAlMejorarUnEquipamientoConNivelMaximoSeVeaUnMensajeDeErrorEnLaPagina() throws MalformedURLException {
        dadoQueElUsuarioIniciaSesionConEmailYPassEsRedirigidoAlHome("test@unlam.edu.ar", "test");
        elUsuarioHaceClickEnElBotonHerreriaYEsRedirigidoALaVistaHerreria();
        elUsuarioHaceClickEnElBotonMejorarDeUnEquipamientoEspecificoATravesDeSuNombre("Espada");
        elUsuarioRecibeUnMensajeDependiendoDelEstadoDeLaMejora("Se ha alcanzado el nivel maximo de este equipamiento");
    }

    @Test
    void queAlMejorarUnEquipamientoExitosamenteSeVeaUnMensajeDeExitoEnLaPagina() throws MalformedURLException {
        dadoQueElUsuarioIniciaSesionConEmailYPassEsRedirigidoAlHome("test@unlam.edu.ar", "test");
        elUsuarioHaceClickEnElBotonHerreriaYEsRedirigidoALaVistaHerreria();
        elUsuarioHaceClickEnElBotonMejorarDeUnEquipamientoEspecificoATravesDeSuNombre("Daga");
        elUsuarioRecibeUnMensajeDependiendoDelEstadoDeLaMejora("El equipamiento se ha mejorado correctamente");
    }

    @Test
    void queAlMejorarUnEquipamientoSinElOroSuficienteSeVeaUnMensajeDeErrorEnLaPagina() throws MalformedURLException {
        dadoQueElUsuarioIniciaSesionConEmailYPassEsRedirigidoAlHome("test@unlam.edu.ar", "test");
        elUsuarioHaceClickEnElBotonHerreriaYEsRedirigidoALaVistaHerreria();
        elUsuarioHaceClickEnElBotonMejorarDeUnEquipamientoEspecificoATravesDeSuNombre("Casco");
        elUsuarioRecibeUnMensajeDependiendoDelEstadoDeLaMejora("Tu oro no es suficiente para realizar esta accion");
    }

    @Test
    void queCuandoUnUsuarioEntreALaVistaHerreriaYNoTengaNingunEquipamientoSeVeaUnMensajeDeError() throws MalformedURLException {
        dadoQueElUsuarioHaceClickEnELBotonRegistrarseEsRedirigidoALaVistaNuevoUsuario();
        dadoQueElUsuarioSeRegistraConEmailYPasswordYHaceClickEnRegistrarseEsRedirigidoACreacionPersonaje("agus@gmail.com", "agus");
        dadoQueElUsuarioSeCreaUnPersonajeConNombreEsRedirigidoALaVistaHome("Agus");
        elUsuarioHaceClickEnElBotonHerreriaYEsRedirigidoALaVistaHerreria();
        elUsuarioRecibeUnMensajeDeErrorPorqueNoSeEncontraronEquipamientos();
    }

    private void elUsuarioRecibeUnMensajeDeErrorPorqueNoSeEncontraronEquipamientos() {
        this.vistaHerreria = new VistaHerreria(context.pages().get(0));
        String mensajeError = this.vistaHerreria.obtenerMensajeDeError();
        assertThat(mensajeError, equalToIgnoringCase("No se han encontrado equipamientos en su inventario"));
    }

    private void dadoQueElUsuarioSeCreaUnPersonajeConNombreEsRedirigidoALaVistaHome(String nombre) throws MalformedURLException {
        this.vistaCreacionPersonaje = new VistaCreacionPersonaje(context.pages().get(0));
        this.vistaCreacionPersonaje.escribirNombre(nombre);
        this.vistaCreacionPersonaje.darClickEnCrearPersonaje();
        URL url = vistaCreacionPersonaje.obtenerURLActualtipoURL();
        assertThat(url.getPath(), matchesPattern("^/spring/home(?:;jsessionid=[^/\\s]+)?$"));
    }

    private void dadoQueElUsuarioHaceClickEnELBotonRegistrarseEsRedirigidoALaVistaNuevoUsuario() throws MalformedURLException {
        this.vistaLogin.darClickEnRegistrarse();
        URL url = this.vistaLogin.obtenerURLActualtipoURL();
        assertThat(url.getPath(), matchesPattern("^/spring/nuevo-usuario(?:;jsessionid=[^/\\s]+)?$"));
    }

    private void dadoQueElUsuarioSeRegistraConEmailYPasswordYHaceClickEnRegistrarseEsRedirigidoACreacionPersonaje(String email, String pass) throws MalformedURLException {
        this.vistaNuevoUsuario = new VistaNuevoUsuario(context.pages().get(0));
        this.vistaNuevoUsuario.escribirEMAIL(email);
        this.vistaNuevoUsuario.escribirClave(pass);
        this.vistaNuevoUsuario.darClickEnIniciarSesion();
        URL url = this.vistaNuevoUsuario.obtenerURLActualtipoURL();
        assertThat(url.getPath(), matchesPattern("^/spring/creacion-personaje(?:;jsessionid=[^/\\s]+)?$"));
    }

    private void elUsuarioRecibeUnMensajeDependiendoDelEstadoDeLaMejora(String estadoMejora) {
        String mensajeError = this.vistaHerreria.obtenerMensajeDeMejora();
        assertThat(mensajeError, equalToIgnoringCase(estadoMejora));
    }

    private void elUsuarioHaceClickEnElBotonMejorarDeUnEquipamientoEspecificoATravesDeSuNombre(String nombreEquipamiento) throws MalformedURLException {
        this.vistaHerreria = new VistaHerreria(context.pages().get(0));
        this.vistaHerreria.hacerClickEnMejorarEquipamiento(nombreEquipamiento);
    }

    private void elUsuarioHaceClickEnElBotonHerreriaYEsRedirigidoALaVistaHerreria() throws MalformedURLException {
        this.vistaHome = new VistaHome(context.pages().get(0));
        this.vistaHome.darClickEnHerreria();
        URL url = this.vistaHome.obtenerURLActualtipoURL();
        assertThat(url.getPath(), matchesPattern("^/spring/herreria(?:;jsessionid=[^/\\s]+)?$"));
    }

    private void dadoQueElUsuarioIniciaSesionConEmailYPassEsRedirigidoAlHome(String email, String pass) throws MalformedURLException {
        this.vistaLogin.escribirEMAIL(email);
        this.vistaLogin.escribirClave(pass);
        this.vistaLogin.darClickEnIniciarSesion();
        URL url = vistaLogin.obtenerURLActualtipoURL();
        assertThat(url.getPath(), matchesPattern("^/spring/home(?:;jsessionid=[^/\\s]+)?$"));
    }
}
