package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioInventario;

class ControladorEquipamientoTest {

    private ServicioInventario servicioInventario;
    private ControladorInventario controlador;
/*
    @BeforeEach
    void init() {
        servicioInventario = mock(ServicioInventario.class);
        controlador = new ControladorInventario(servicioInventario);
    }

    @Test
    void queSePuedaVerLaPaginaDeEquipamiento() {
        Equipamiento eq1 = new Arma();
        eq1.setNombre("espada");
        eq1.setEquipado(true);

        Equipamiento eq2 = new Arma();
        eq2.setNombre("daga");
        eq2.setEquipado(false);

        List<Equipamiento> listaMock = Arrays.asList(eq1, eq2);

        when(servicioInventario.mostrarEquipamiento()).thenReturn(listaMock);

        ModelAndView mav = controlador.verEquipamiento();

        assertEquals("equipamiento", mav.getViewName());

        List<Equipamiento> contenido = (List<Equipamiento>) mav.getModel().get("contenido");
        assertEquals(2, contenido.size());
        assertEquals("espada", ((Equipamiento) mav.getModel().get("equipoSeleccionado")).getNombre());

        verify(servicioInventario).mostrarEquipamiento();
    }*/
}
