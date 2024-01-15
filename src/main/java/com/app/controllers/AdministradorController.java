package com.app.controllers;

import com.app.services.IProductoService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/administrador")
@Slf4j
@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class AdministradorController {

    IProductoService productoService;

    @GetMapping("")
    public String home(Model model) {
        model.addAttribute("listaProducto", productoService.findAll());
        return "admin/home";
    }
}
