package com.tallerwebi.presentacion;
import com.tallerwebi.dominio.entidad.Arma;
import com.tallerwebi.dominio.entidad.Equipamiento;
import com.tallerwebi.dominio.excepcion.InventarioVacioException;
import com.tallerwebi.dominio.interfaz.servicio.ServicioInventario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.*;

public class ControladorInventarioTest {

    private ServicioInventario servicioInventarioMock;
    private ControladorInventario controladorInventario;
    private HttpSession sessionMock;


    @BeforeEach
    public void init(){
        servicioInventarioMock = mock(ServicioInventario.class);
        sessionMock = mock(HttpSession.class);
        when(sessionMock.getAttribute("idPersonaje")).thenReturn(1L);

        controladorInventario = new ControladorInventario(servicioInventarioMock);
    }
}