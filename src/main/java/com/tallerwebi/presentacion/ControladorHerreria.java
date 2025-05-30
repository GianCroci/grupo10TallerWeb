package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Equipamiento;
import com.tallerwebi.dominio.ServicioHerreria;
import com.tallerwebi.dominio.excepcion.NivelDeEquipamientoMaximoException;
import com.tallerwebi.dominio.excepcion.OroInsuficienteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class ControladorHerreria {

    private final ServicioHerreria servicioHerreria;

    @Autowired
    public ControladorHerreria(ServicioHerreria servicioHerreria) {
        this.servicioHerreria = servicioHerreria;
    }

    @RequestMapping("/herreria")
    public ModelAndView irALaHerreria(HttpSession session) {

        ModelMap model = new ModelMap();

        Long idPersonaje = (Long) session.getAttribute("idPersonaje");

        Integer oroPersonaje = servicioHerreria.obtenerOroDelPersonaje(idPersonaje);

        model.put("oroPersonaje", oroPersonaje);

        model.put("mejoraDto", new MejoraDto());

        List<Equipamiento> inventario = servicioHerreria.obtenerInventario(idPersonaje);
        if (inventario.isEmpty()) {
            model.put("vacio", "No hay equipamientos para mejorar");
        }

        model.put("inventario", inventario);

        return new ModelAndView("herreria", model);
    }

    @RequestMapping(path = "/mejorar-equipamiento", method = RequestMethod.POST)
    public ModelAndView mejorarEquipamiento(@ModelAttribute("mejoraDto") MejoraDto mejoraDto) {

        ModelMap model = new ModelMap();

        try {
            servicioHerreria.mejorarEquipamiento(mejoraDto.getEquipamiento(), mejoraDto.getOroUsuario());
            model.put("mensaje", "El equipamiento se ha mejorado correctamente");

        } catch (NivelDeEquipamientoMaximoException | OroInsuficienteException e) {

            model.put("mensaje", e.getMessage());
            return new ModelAndView("redirect:/herreria", model);
        }

        return new ModelAndView("redirect:/herreria", model);
    }
}


/*
<input type="hidden" th:field="*{equipamiento.id}" th:value="${equipamientoActual.id}"/>
                <input type="hidden" th:field="*{equipamiento.nombre}" th:value="${equipamientoActual.nombre}"/>
                <input type="hidden" th:field="*{equipamiento.fuerza}" th:value="${equipamientoActual.fuerza}"/>
                <input type="hidden" th:field="*{equipamiento.armadura}" th:value="${equipamientoActual.armadura}"/>
                <input type="hidden" th:field="*{equipamiento.inteligencia}" th:value="${equipamientoActual.inteligencia}"/>
                <input type="hidden" th:field="*{equipamiento.agilidad}" th:value="${equipamientoActual.agilidad}" />
                <input type="hidden" th:field="*{equipamiento.costoMejora}" th:value="${equipamientoActual.costoMejora}"/>
                <input type="hidden" th:field="*{equipamiento.costoCompra}" th:value="${equipamientoActual.costoCompra}"/>
                <input type="hidden" th:field="*{equipamiento.costoVenta}" th:value="${equipamientoActual.costoVenta}"/>
                <input type="hidden" th:field="*{equipamiento.equipado}" th:value="${equipamientoActual.equipado}"/>
                <input type="hidden" th:field="*{oroUsuario}" th:value="1000.0" />

 */
