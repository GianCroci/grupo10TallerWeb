package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.excepcion.Equipamiento;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.ui.ModelMap;
import java.util.HashMap;
import java.util.Map;

import com.tallerwebi.dominio.Inventario;

@Controller
@RequestMapping("/inventario")
public class ControladorInventario {

    private Map<Integer, Inventario> inventarios;

    public ControladorInventario() {
        Inventario inventario = new Inventario();
        inventarios = new HashMap<>();
        inventarios.put(1, inventario);
    }

    public Inventario buscarInventario(Integer id) {
        if (this.inventarios.containsKey(id)) {
            return this.inventarios.get(id);
        }else {
            return null;
        }
    }

    @GetMapping("/{id}")
    public ModelAndView verInventario(@PathVariable("id") Integer idInventario) {
        ModelMap model = new ModelMap();
        if (inventarios.containsKey(idInventario)) {
            model.addAttribute("contenido-inventario", "Inventario " + idInventario);
        } else {
            model.addAttribute("error", "Inventario no encontrado");
        }
        return new ModelAndView("inventario", model);
    }

    @GetMapping("/{id}/ver-armas")
    public ModelAndView verArmasDelInventario(@PathVariable("id") Integer idInventario){
        ModelMap model = new ModelMap();
        if(buscarInventario(idInventario) != null) {
            model.addAttribute("armas" , buscarInventario(idInventario).getNombreArmas());
        }
        return new ModelAndView("inventario/armas", model);
    }

    @GetMapping("/{id}/ver-vestimentas")
    public ModelAndView verVestimentasDelInventario(@PathVariable("id") Integer idInventario){
        ModelMap model = new ModelMap();
        if(buscarInventario(idInventario) != null) {
            model.addAttribute("vestimentas" , buscarInventario(idInventario).getNombreVestimentas());
        }
        return new ModelAndView("inventario/vestimentas", model);
    }

    @GetMapping("/{id}/ver-inventario-completo")
    public ModelAndView verTodoElContenidoDelInventario(@PathVariable("id") Integer idInventario){
        ModelMap model = new ModelMap();
        if(buscarInventario(idInventario) != null) {
            model.addAttribute("contenido-completo", buscarInventario(idInventario).getTodoELInventario());
        }
        return new ModelAndView("inventario/inventario-completo", model);
    }

    @GetMapping("/{id}/agregar-equipo")
    public ModelAndView agregarEquipoAlInventario(@PathVariable("id") Integer idInventario, Equipamiento equipoNuevo){
        ModelMap model = new ModelMap();
    if(buscarInventario(idInventario) != null) {
        if (equipoNuevo.getTipo() == "ARMA") {
            if (buscarInventario(idInventario).getArmas().size() < 5) {
                buscarInventario(idInventario).agregarEquipo(equipoNuevo);
                model.addAttribute("mensaje", "Arma agregada al inventario con exito");
            }else{
                model.addAttribute("mensaje", "Almacenamiento de armas lleno");
            }
        }
        if (equipoNuevo.getTipo() == "VESTIMENTA") {
            if (buscarInventario(idInventario).getVestimentas().size() <= 5) {
                buscarInventario(idInventario).agregarEquipo(equipoNuevo);
                model.addAttribute("mensaje", "Vestimenta agregada al inventario con exito");
            }else{
                model.addAttribute("mensaje", "Almacenamiento de vestimentas lleno");
            }
        }
    }
        return new ModelAndView("mercado/agregar-equipo",model);
    }

    @GetMapping("/{id}/equipar-arma")
    public ModelAndView equiparArmaAlPersonaje(@PathVariable("id") Integer idInventario,String nombreArma){
        ModelMap model = new ModelMap();
        if(buscarInventario(idInventario) != null && buscarInventario(idInventario).buscarArmaPorNombre(nombreArma) != null) {
            model.addAttribute("mensaje","Arma equipada");
        }else {
            model.addAttribute("mensaje","No se pudo equipar el arma");
        }
        return new ModelAndView("inventario/arma-equipada",model);
    }

    @GetMapping("/{id}/equipar-vestimenta")
    public ModelAndView equiparVestimentaAlPersonaje(@PathVariable("id") Integer idInventario,String nombreVestimenta){
        ModelMap model = new ModelMap();
        if(buscarInventario(idInventario) != null && buscarInventario(idInventario).buscarVestimentaPorNombre(nombreVestimenta) != null) {
            model.addAttribute("mensaje","Vestimenta equipada");
        }else {
            model.addAttribute("mensaje","No se pudo equipar la vestimenta");
        }
        return new ModelAndView("inventario/vestimenta-equipada",model);    }
}