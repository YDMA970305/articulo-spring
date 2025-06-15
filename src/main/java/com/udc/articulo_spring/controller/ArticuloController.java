package com.udc.articulo_spring.controller;

import com.udc.articulo_spring.model.Articulo;
import com.udc.articulo_spring.model.Usuario;
import com.udc.articulo_spring.service.ArticuloService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;
import java.math.BigDecimal;

@Controller
@RequestMapping("/articulo")
@SessionAttributes({"foundArticulo", "articuloList"})
public class ArticuloController {

    private final ArticuloService articuloService;

    public ArticuloController(ArticuloService articuloService) {
        this.articuloService = articuloService;
    }

    @GetMapping("/agregar")
    public String showAddArticuloForm(Model model) {
        model.addAttribute("articulo", new Articulo());
        return "Articulo/agregar";
    }

    @PostMapping("/agregar")
    public String agregar(@ModelAttribute Articulo articulo, HttpSession session, RedirectAttributes redirectAttributes) {
        Usuario currentUsuario = (Usuario) session.getAttribute("currentUsuario");
        if (currentUsuario != null) {
            articulo.setNombreUsuario(currentUsuario.getNombreUsuario());
        } else {
            redirectAttributes.addFlashAttribute("error", "Debes iniciar sesión para agregar un artículo.");
            return "redirect:/usuario/login";
        }

        articuloService.saveArticulo(articulo);
        redirectAttributes.addFlashAttribute("message", "Artículo guardado exitosamente!");
        return "redirect:/articulo/listar";
    }

    @GetMapping("/buscar")
    public String buscar(@RequestParam(required = false) Integer idArticulo, Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        if (idArticulo != null) {
            Optional<Articulo> foundArticulo = articuloService.findArticuloById(idArticulo);
            if (foundArticulo.isPresent()) {
                session.setAttribute("foundArticulo", foundArticulo.get());
            } else {
                session.removeAttribute("foundArticulo");
                redirectAttributes.addAttribute("notFound", true);
            }
        }
        return "Articulo/buscar";
    }

    @PostMapping("/editar")
    public String editar(@ModelAttribute Articulo articulo, HttpSession session, RedirectAttributes redirectAttributes) {
        Articulo sessionArticulo = (Articulo) session.getAttribute("foundArticulo");
        if (sessionArticulo != null && sessionArticulo.getIdArticulo().equals(articulo.getIdArticulo())) {
            sessionArticulo.setMarca(articulo.getMarca());
            sessionArticulo.setPrecioVenta(articulo.getPrecioVenta());
            sessionArticulo.setPrecioCompra(articulo.getPrecioCompra());
            sessionArticulo.setIva(articulo.getIva());
            sessionArticulo.setModelo(articulo.getModelo());
            sessionArticulo.setProveedor(articulo.getProveedor());
            sessionArticulo.setTienda(articulo.getTienda());
            sessionArticulo.setCantidad(articulo.getCantidad());
            sessionArticulo.setCategoria(articulo.getCategoria());
            sessionArticulo.setDescripcion(articulo.getDescripcion());

            articuloService.saveArticulo(sessionArticulo);
            session.setAttribute("foundArticulo", sessionArticulo);
            redirectAttributes.addFlashAttribute("message", "Artículo actualizado!");
            return "redirect:/articulo/buscar";
        }
        redirectAttributes.addFlashAttribute("error", "No se pudo editar el artículo.");
        return "redirect:/articulo/buscar";
    }

    @GetMapping("/eliminar")
    public String eliminar(HttpSession session, RedirectAttributes redirectAttributes) {
        Articulo articuloToDelete = (Articulo) session.getAttribute("foundArticulo");
        if (articuloToDelete != null && articuloToDelete.getIdArticulo() != null) {
            articuloService.deleteArticulo(articuloToDelete.getIdArticulo());
            session.removeAttribute("foundArticulo");
            redirectAttributes.addFlashAttribute("message", "Artículo eliminado!");
        } else {
            redirectAttributes.addFlashAttribute("error", "No hay artículo seleccionado para eliminar.");
        }
        return "redirect:/articulo/listar";
    }

    @GetMapping("/listar")
    public String listar(Model model, HttpSession session) {
        List<Articulo> articulos = articuloService.findAllArticulos();
        session.setAttribute("articuloList", articulos);
        return "Articulo/listar";
    }
}