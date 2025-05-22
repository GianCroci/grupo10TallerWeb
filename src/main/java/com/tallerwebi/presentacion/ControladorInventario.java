package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioInventario;
import com.tallerwebi.dominio.Equipamiento;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.ui.ModelMap;

@Controller
@RequestMapping("/inventario")
public class ControladorInventario {

    private final ServicioInventario servicioInventario;

    public ControladorInventario(ServicioInventario servicioInventario) {
        this.servicioInventario = servicioInventario;
    }

    @GetMapping("/{id}")
    public ModelAndView verInventario(@PathVariable Integer id) {
        ModelMap model = new ModelMap();
        if (servicioInventario.buscarInventario(id) != null) {
            model.addAttribute("contenido-inventario", "Inventario " + id);
        } else {
            model.addAttribute("error", "Inventario no encontrado");
        }
        return new ModelAndView("inventario", model);
    }

    @GetMapping("/{id}/ver-armas")
    public ModelAndView verArmas(@PathVariable Integer id) {
        ModelMap model = new ModelMap();
        model.addAttribute("armas", servicioInventario.obtenerNombresArmas(id));
        return new ModelAndView("inventario/armas", model);
    }

    @GetMapping("/{id}/ver-vestimentas")
    public ModelAndView verVestimentas(@PathVariable Integer id) {
        ModelMap model = new ModelMap();
        model.addAttribute("vestimentas", servicioInventario.obtenerNombresVestimentas(id));
        return new ModelAndView("inventario/vestimentas", model);
    }

    @GetMapping("/{id}/ver-inventario-completo")
    public ModelAndView verInventarioCompleto(@PathVariable Integer id) {
        ModelMap model = new ModelMap();
        model.addAttribute("contenido-completo", servicioInventario.obtenerContenidoCompleto(id));
        return new ModelAndView("inventario/inventario-completo", model);
    }

    @GetMapping("/{id}/agregar-equipo")
    public ModelAndView agregarEquipo(@PathVariable Integer id, Equipamiento equipo) {
        ModelMap model = new ModelMap();
        model.addAttribute("mensaje", servicioInventario.agregarEquipo(id, equipo));
        return new ModelAndView("mercado/agregar-equipo", model);
    }

    @GetMapping("/{id}/equipar-arma")
    public ModelAndView equiparArma(@PathVariable Integer id, @RequestParam String nombreArma) {
        ModelMap model = new ModelMap();
        model.addAttribute("mensaje", servicioInventario.equiparArma(id, nombreArma));
        return new ModelAndView("inventario/arma-equipada", model);
    }

    @GetMapping("/{id}/equipar-vestimenta")
    public ModelAndView equiparVestimenta(@PathVariable Integer id, @RequestParam String nombreVestimenta) {
        ModelMap model = new ModelMap();
        model.addAttribute("mensaje", servicioInventario.equiparVestimenta(id, nombreVestimenta));
        return new ModelAndView("inventario/vestimenta-equipada", model);
    }
}
