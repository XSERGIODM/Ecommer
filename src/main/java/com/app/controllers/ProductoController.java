package com.app.controllers;

import com.app.models.Producto;
import com.app.models.Usuario;
import com.app.services.IProductoService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/productos")
@Slf4j
@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ProductoController {

    IProductoService productoService;

    @GetMapping("")
    public String show(Model model) {
        model.addAttribute("productos", productoService.findAll());
        return "product/show";
    }

    @GetMapping("/create")
    public String create() {
        return "product/create";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable(name = "id") Integer id) {
        productoService.delete(id);
        return "redirect:/productos";
    }

    @PostMapping("/save")
    public String save(Producto producto) {
        Usuario usuario = new Usuario();
        usuario.setId(1);
        producto.setUsuario(usuario);
        log.info("Producto: {}", producto);
        productoService.save(producto);
        return "redirect:/productos";
    }
}
