package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioInventario;
import com.tallerwebi.dominio.ServicioInventarioImpl;
import com.tallerwebi.dominio.excepcion.Equipamiento;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class ControladorInventarioTest {

    ServicioInventario servicioInventario = new ServicioInventarioImpl();

    @Test
    public void verInventario(){
        //preparación
        ControladorInventario controladorInventario = new ControladorInventario(servicioInventario);
        Integer idInventario = 1;
        String vistaEsperada = "inventario";
        String modeloEsperado = "Inventario 1";

        //ejecución
        ModelAndView mav = controladorInventario.verInventario(idInventario);

        //verificación
        assertThat(vistaEsperada, equalTo(mav.getViewName()));
        assertThat(modeloEsperado, equalTo(mav.getModel().get("contenido-inventario")));
    }

    @Test
    public void verArmasDelInventario() {
        //preparación
        ControladorInventario controladorInventario = new ControladorInventario(servicioInventario);
        String vistaEsperada = "inventario/armas";
        List <String> modeloEsperado = new ArrayList<String>();
        modeloEsperado.add("Espada");

        //ejecución
        ModelAndView mav = controladorInventario.verArmas(01);

        //verificación
        assertThat(vistaEsperada, equalTo(mav.getViewName()));
        assertThat(modeloEsperado, equalTo(mav.getModel().get("armas")));
    }

    @Test
    public void verVestimentasDelInventario() {
        //preparación
        ControladorInventario controladorInventario = new ControladorInventario(servicioInventario);
        String vistaEsperada = "inventario/vestimentas";
        List <String> modeloEsperado = new ArrayList<String>();
        modeloEsperado.add("Armadura");

        //ejecución
        ModelAndView mav = controladorInventario.verVestimentas(01);

        //verificación
        assertThat(vistaEsperada, equalTo(mav.getViewName()));
        assertThat(modeloEsperado, equalTo(mav.getModel().get("vestimentas")));
    }

    @Test
    public void verTodoElContenidoDelInventario() {
        //preparación
        ControladorInventario controladorInventario = new ControladorInventario(servicioInventario);
        String vistaEsperada = "inventario/inventario-completo";
        List <String> modeloEsperado = new ArrayList<String>();
        modeloEsperado.add("Espada");
        modeloEsperado.add("Armadura");

        //ejecución
        ModelAndView mav = controladorInventario.verInventarioCompleto(01);

        //verificación
        assertThat(vistaEsperada, equalTo(mav.getViewName()));
        assertThat(modeloEsperado, equalTo(mav.getModel().get("contenido-completo")));
    }

    @Test
    public void queCuandoSeAgregueUnArmaAlInventarioSeVeaUnMensajeDeExito(){
        //preparación
        ControladorInventario controladorInventario = new ControladorInventario(servicioInventario);
        String vistaEsperada = "mercado/agregar-equipo";
        String modeloEsperado = "Arma agregada al inventario con exito";
        Equipamiento equipoNuevo = new Equipamiento("Hacha","ARMA");

        //ejecución
        ModelAndView mav = controladorInventario.agregarEquipo(01,equipoNuevo);

        //verificación
        assertThat(vistaEsperada, equalTo(mav.getViewName()));
        assertThat(modeloEsperado, equalTo(mav.getModel().get("mensaje")));
    }

    @Test
    public void queCuandoSeAgregueUnaVestimentaAlInventarioSeVeaUnMensajeDeExito(){
        //preparación
        ControladorInventario controladorInventario = new ControladorInventario(servicioInventario);
        String vistaEsperada = "mercado/agregar-equipo";
        String modeloEsperado = "Vestimenta agregada al inventario con exito";
        Equipamiento equipoNuevo = new Equipamiento("Armadura dorada","VESTIMENTA");

        //ejecución
        ModelAndView mav = controladorInventario.agregarEquipo(01,equipoNuevo);

        //verificación
        assertThat(vistaEsperada, equalTo(mav.getViewName()));
        assertThat(modeloEsperado, equalTo(mav.getModel().get("mensaje")));
    }

    @Test
    public void queCuandoElInventarioEsteLlenoMuestreUnMensajeDeError(){
        //preparación
        ControladorInventario controladorInventario = new ControladorInventario(servicioInventario);
        String vistaEsperada = "mercado/agregar-equipo";
        String modeloEsperado = "Almacenamiento de vestimentas lleno";
        Equipamiento equipoNuevo = new Equipamiento("Armadura dorada","VESTIMENTA");
        Equipamiento equipoNuevo2 = new Equipamiento("Armadura rosa","VESTIMENTA");
        Equipamiento equipoNuevo3 = new Equipamiento("Armadura violeta","VESTIMENTA");
        Equipamiento equipoNuevo4 = new Equipamiento("Armadura plateada","VESTIMENTA");
        Equipamiento equipoNuevo5 = new Equipamiento("Armadura celeste","VESTIMENTA");
        Equipamiento equipoNuevo6 = new Equipamiento("Armadura roja","VESTIMENTA");

        //ejecución
        controladorInventario.agregarEquipo(01,equipoNuevo);
        controladorInventario.agregarEquipo(01,equipoNuevo2);
        controladorInventario.agregarEquipo(01,equipoNuevo3);
        controladorInventario.agregarEquipo(01,equipoNuevo4);
        controladorInventario.agregarEquipo(01,equipoNuevo5);
        ModelAndView mav = controladorInventario.agregarEquipo(01,equipoNuevo6);

        //verificación
        assertThat(vistaEsperada, equalTo(mav.getViewName()));
        assertThat(modeloEsperado, equalTo(mav.getModel().get("mensaje")));
    }

    @Test
    public void equiparArmaDelInventarioAlPersonaje(){
        //Solo la va a poner en el arma equipada del inventario, no del personaje (hablarlo con los chicos (╥_╥) )
        //Otra cosa! El arma que vamos a equipar la buscamos por nombre porque tiene que estar dentro del inventario! (de paso hacemos el metodo de buscar arma/vestimenta)
        //preparación
        ControladorInventario controladorInventario = new ControladorInventario(servicioInventario);
        String vistaEsperada = "inventario/arma-equipada";
        String modeloEsperado = "Arma equipada";
        String nombreArma = "Hacha";
        Equipamiento equipoNuevo = new Equipamiento("Hacha", "ARMA");

        //ejecución
        controladorInventario.agregarEquipo(01,equipoNuevo);
        ModelAndView mav = controladorInventario.equiparArma(01,nombreArma);

        //verificación
        assertThat(vistaEsperada, equalTo(mav.getViewName()));
        assertThat(modeloEsperado, equalTo(mav.getModel().get("mensaje")));
    }

    @Test
    public void equiparVestimentaDelInventarioAlPersonaje(){
        //preparacion
        ControladorInventario controladorInventario = new ControladorInventario(servicioInventario);
        String vistaEsperada = "inventario/vestimenta-equipada";
        String modeloEsperado = "Vestimenta equipada";
        String nombreVestimenta = "Armadura rosa";
        Equipamiento equipoNuevo = new Equipamiento("Armadura rosa", "VESTIMENTA");

        //ejecución
        controladorInventario.agregarEquipo(01,equipoNuevo);
        ModelAndView mav = controladorInventario.equiparVestimenta(01,nombreVestimenta);

        //verificación
        assertThat(vistaEsperada, equalTo(mav.getViewName()));
        assertThat(modeloEsperado, equalTo(mav.getModel().get("mensaje")));
    }


}