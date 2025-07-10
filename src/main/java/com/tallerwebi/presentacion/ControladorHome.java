package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.entidad.Personaje;
import com.tallerwebi.dominio.entidad.Producto;
import com.tallerwebi.dominio.interfaz.servicio.ServicioMercadoPago;
import com.tallerwebi.dominio.interfaz.servicio.ServicioPersonaje;
import com.tallerwebi.dominio.interfaz.servicio.ServicioProducto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Controller
public class ControladorHome {

    private final ServicioPersonaje servicioPersonaje;
    private final ServicioProducto servicioProducto;
    private final ServicioMercadoPago servicioMercadoPago;

    @Autowired
    public ControladorHome(ServicioPersonaje servicioPersonaje, ServicioProducto servicioProducto, ServicioMercadoPago servicioMercadoPago) {
        this.servicioPersonaje = servicioPersonaje;
        this.servicioProducto = servicioProducto;
        this.servicioMercadoPago = servicioMercadoPago;
    }

    @RequestMapping("/home")
    public ModelAndView irAlHome(HttpSession session, RedirectAttributes redirectAttributes, @RequestParam(required = false) String operacionMP) {
        ModelMap model = new ModelMap();

        Long idPersonaje = (Long) session.getAttribute("idPersonaje");
        if (idPersonaje == null) {
            redirectAttributes.addFlashAttribute("error", "No puede acceder a la vista home sin haber iniciado sesion");
            return new ModelAndView("redirect:/login");
        }
        Optional<String> mensajito = servicioMercadoPago.obtenerMensajeOperacion(operacionMP);
        if (mensajito.isPresent()) {
            model.put("mensajito", mensajito.get());
        }

        List<Producto> productosParaComprarPorMP = servicioProducto.obtenerTodosLosProductos();
        Personaje personaje = servicioPersonaje.buscarPersonaje(idPersonaje);
        model.put("infoPersonaje", personaje);

        model.put("productosParaComprarPorMP", productosParaComprarPorMP);
        return new ModelAndView("home", model);
    }
}
