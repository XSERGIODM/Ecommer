package com.app.controllers;

import com.app.models.Producto;
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
@RequestMapping("/")
@Slf4j
@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class HomeController {

    IProductoService productoService;
    @GetMapping("")
    public String home(Model model){
        model.addAttribute("listaProducto", productoService.findAll());
        return "user/home";
    }
    @GetMapping("productoHome/{id}")
    public String productoHome(@PathVariable (value = "id") Integer id, Model model){
        model.addAttribute("producto", productoService.findById(id).get());
        return "user/productoHome";
    }

    @PostMapping("/cart")
    public String cart(){
        return "user/carrito";
    }

}
