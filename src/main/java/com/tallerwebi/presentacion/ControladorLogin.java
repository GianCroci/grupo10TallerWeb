package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.interfaz.servicio.ServicioLogin;
import com.tallerwebi.dominio.interfaz.servicio.ServicioUsuario;
import com.tallerwebi.dominio.entidad.Usuario;
import com.tallerwebi.dominio.excepcion.UsuarioExistente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class ControladorLogin {

    private ServicioLogin servicioLogin;
    private ServicioUsuario servicioUsuario;

    @Autowired
    public ControladorLogin(ServicioLogin servicioLogin, ServicioUsuario servicioUsuario){
        this.servicioLogin = servicioLogin;
        this.servicioUsuario = servicioUsuario;
    }

    @RequestMapping("/login")
    public ModelAndView irALogin() {

        ModelMap modelo = new ModelMap();
        modelo.put("datosLogin", new DatosLogin());
        return new ModelAndView("login", modelo);
    }

    @RequestMapping(path = "/validar-login", method = RequestMethod.POST)
    public ModelAndView validarLogin(@ModelAttribute("datosLogin") DatosLogin datosLogin, HttpServletRequest request) {
        ModelMap model = new ModelMap();

        Usuario usuarioBuscado = servicioLogin.consultarUsuario(datosLogin.getEmail(), datosLogin.getPassword());
        if (usuarioBuscado != null) {
            if (usuarioBuscado.getPersonaje() == null) {
                request.getSession().setAttribute("accesoCreacionPersonaje", true);
                return new ModelAndView("redirect:/creacion-personaje");
            }
            request.getSession().setAttribute("ROL", usuarioBuscado.getRol());
            request.getSession().setAttribute("idPersonaje", usuarioBuscado.getPersonaje().getId());
            request.getSession().setAttribute("nombrePersonaje", usuarioBuscado.getPersonaje().getNombre());
            request.getSession().setAttribute("imagenPersonaje", usuarioBuscado.getPersonaje().getImagen());
            return new ModelAndView("redirect:/home");
        } if (usuarioBuscado == null) {
            model.put("error", "Usuario o clave incorrecta");
        }
        return new ModelAndView("login", model);
    }

    @RequestMapping(path = "/registrarme", method = RequestMethod.POST)
    public ModelAndView registrarme(@ModelAttribute("usuario") Usuario usuario, HttpSession session) {
        ModelMap model = new ModelMap();


        try{
            servicioLogin.registrar(usuario);
        } catch (UsuarioExistente e){
            model.put("error", "El usuario ya existe");
            return new ModelAndView("nuevo-usuario", model);
        } catch (Exception e){
            model.put("error", "Error al registrar el nuevo usuario");
            return new ModelAndView("nuevo-usuario", model);
        }
        session.setAttribute("usuarioLogueado", usuario);
        session.setAttribute("accesoCreacionPersonaje", true);
        return new ModelAndView("redirect:/creacion-personaje");
    }

    @RequestMapping(path = "/nuevo-usuario", method = RequestMethod.GET)
    public ModelAndView nuevoUsuario() {
        ModelMap model = new ModelMap();
        model.put("usuario", new Usuario());
        return new ModelAndView("nuevo-usuario", model);
    }

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public ModelAndView inicio() {
        return new ModelAndView("redirect:/login");
    }

    @RequestMapping(path = "/logout", method = RequestMethod.GET)
    public ModelAndView logout(HttpSession session) {
        session.invalidate();
        return new ModelAndView("redirect:/login");
    }
}