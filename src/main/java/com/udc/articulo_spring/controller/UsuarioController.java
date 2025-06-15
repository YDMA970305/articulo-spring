package com.udc.articulo_spring.controller;

import com.udc.articulo_spring.model.Usuario;
import com.udc.articulo_spring.service.UsuarioService;
import jakarta.annotation.PostConstruct; // Para inicializar datos al inicio
import jakarta.servlet.http.HttpSession; // Para manejar la sesión HTTP
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model; // Para pasar datos a la vista
import org.springframework.web.bind.annotation.*; // Anotaciones para mapeo web
import org.springframework.web.servlet.mvc.support.RedirectAttributes; // Para atributos de redirección

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/usuario") 
@SessionAttributes({"foundUsuario", "usuarioList", "currentUsuario"}) // Atributos que se guardarán en sesión
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    
    @PostConstruct
    public void init() {
        usuarioService.initializeRolesAndUsuariosForDev();
    }

    
    @GetMapping("/login")
    public String showLoginForm() {
        return "Usuario/login";
    }

    
    @PostMapping("/iniciar_sesion")
    public String iniciarSesion(@RequestParam String nombreUsuario, @RequestParam String contrasena,
                                HttpSession session, RedirectAttributes redirectAttributes) {
        Optional<Usuario> usuarioOptional = usuarioService.authenticate(nombreUsuario, contrasena);
        if (usuarioOptional.isPresent()) {
            session.setAttribute("currentUsuario", usuarioOptional.get()); 
            return "redirect:/menu"; 
        } else {
            redirectAttributes.addAttribute("error", true); 
            return "redirect:/usuario/login"; 
        }
    }

    
    @GetMapping("/cerrar_sesion")
    public String cerrarSesion(HttpSession session, RedirectAttributes redirectAttributes) {
        session.removeAttribute("currentUsuario"); 
        session.invalidate(); 
        redirectAttributes.addAttribute("logout", true); 
        return "redirect:/usuario/login"; 
    }

   
    @GetMapping("/recoverypass")
    public String showRecoveryPassForm() {
        return "Usuario/recoverypass";
    }

   
    @PostMapping("/cambiar_pass")
    public String cambiarPass(@RequestParam String newPassword, HttpSession session,
                              RedirectAttributes redirectAttributes) {
        Usuario currentUsuario = (Usuario) session.getAttribute("currentUsuario");
        if (currentUsuario != null) {
            if (usuarioService.changePassword(currentUsuario.getNombreUsuario(), newPassword)) {
                session.setAttribute("currentUsuario", usuarioService.findUsuarioByNombreUsuario(currentUsuario.getNombreUsuario()).get());
                redirectAttributes.addFlashAttribute("changeSuccess", true); 
                return "redirect:/menu";
            }
        }
        redirectAttributes.addFlashAttribute("error", true); 
        return "redirect:/usuario/recoverypass";
    }

    
    @GetMapping("/agregar")
    public String showAddUsuarioForm(Model model) {
        model.addAttribute("usuario", new Usuario()); 
        return "Usuario/agregar";
    }

   
    @PostMapping("/agregar")
    public String agregar(@ModelAttribute Usuario usuario, RedirectAttributes redirectAttributes) {
        usuarioService.saveUsuario(usuario); 
        redirectAttributes.addFlashAttribute("message", "Usuario guardado exitosamente!");
        return "redirect:/usuario/listar"; 
    }

    
    @GetMapping("/buscar")
    public String buscar(@RequestParam(required = false) String nombreUsuario, Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        if (nombreUsuario != null && !nombreUsuario.isEmpty()) {
            Optional<Usuario> foundUsuario = usuarioService.findUsuarioByNombreUsuario(nombreUsuario);
            if (foundUsuario.isPresent()) {
                session.setAttribute("foundUsuario", foundUsuario.get()); 
            } else {
                session.removeAttribute("foundUsuario");
                redirectAttributes.addAttribute("notFound", true); 
            }
        }
        return "Usuario/buscar";
    }

    
    @PostMapping("/editar")
    public String editar(@ModelAttribute Usuario usuario, HttpSession session, RedirectAttributes redirectAttributes) {
        Usuario sessionUsuario = (Usuario) session.getAttribute("foundUsuario");
        if (sessionUsuario != null && sessionUsuario.getNombreUsuario().equals(usuario.getNombreUsuario())) {
            
            sessionUsuario.setEmail(usuario.getEmail());
            sessionUsuario.setNombre(usuario.getNombre());
            sessionUsuario.setTipo(usuario.getTipo());

            
            if (usuario.getContrasena() != null && !usuario.getContrasena().isEmpty()) {
                sessionUsuario.setContrasena(usuario.getContrasena());
            }
            usuarioService.saveUsuario(sessionUsuario);
            session.setAttribute("foundUsuario", sessionUsuario); 
            redirectAttributes.addFlashAttribute("message", "Usuario actualizado!");
            return "redirect:/usuario/buscar";
        }
        redirectAttributes.addFlashAttribute("error", "No se pudo editar el usuario.");
        return "redirect:/usuario/buscar";
    }

   
    @GetMapping("/eliminar")
    public String eliminar(HttpSession session, RedirectAttributes redirectAttributes) {
        Usuario usuarioToDelete = (Usuario) session.getAttribute("foundUsuario");
        if (usuarioToDelete != null && usuarioToDelete.getNombreUsuario() != null) {
            usuarioService.deleteUsuario(usuarioToDelete.getNombreUsuario());
            session.removeAttribute("foundUsuario"); 
            redirectAttributes.addFlashAttribute("message", "Usuario eliminado!");
        } else {
            redirectAttributes.addFlashAttribute("error", "No hay usuario seleccionado para eliminar.");
        }
        return "redirect:/usuario/listar";
    }

    
    @GetMapping("/listar")
    public String listar(Model model, HttpSession session) {
        List<Usuario> usuarios = usuarioService.findAllUsuarios();
        session.setAttribute("usuarioList", usuarios); // Guarda la lista en sesión
        return "Usuario/listar";
    }
}