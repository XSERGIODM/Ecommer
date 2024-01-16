package com.app.controllers;

import com.app.services.IOrdenService;
import com.app.services.IProductoService;
import com.app.services.IUsuarioService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/administrador")
@Slf4j
@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class AdministradorController {

    IProductoService productoService;
    IUsuarioService usuarioService;
    IOrdenService ordenService;

    @GetMapping("")
    public String home(Model model) {
        model.addAttribute("listaProducto", productoService.findAll());
        return "admin/home";
    }

    @GetMapping("/usuarios")
    public String usuarios(Model model) {
        model.addAttribute("listaUsuario", usuarioService.findAll());
        return "admin/usuarios";
    }

    @GetMapping("/ordenes")
    public String ordenes(Model model) {
        model.addAttribute("listaOrden", ordenService.findAll());
        return "admin/ordenes";
    }

    @GetMapping("/detalleOrden/{id}")
    public String detalleOrden(Model model, @PathVariable(name = "id") Integer id) {
        model.addAttribute("listaDetalle", ordenService.findById(id).get().getDetalleOrden());
        return "admin/detalleOrden";
    }
}
