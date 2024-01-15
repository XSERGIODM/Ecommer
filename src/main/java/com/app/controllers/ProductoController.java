package com.app.controllers;

import com.app.models.Producto;
import com.app.models.Usuario;
import com.app.services.IProductoService;
import com.app.services.UploadFileService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/productos")
@Slf4j
@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ProductoController {

    IProductoService productoService;
    UploadFileService uploadFileService;

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
        Producto producto = productoService.findById(id).get();
        uploadFileService.deleteImage(producto.getImagen());
        productoService.delete(id);
        return "redirect:/productos";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") Integer id, Model model) {
        model.addAttribute("producto", productoService.findById(id));
        return "product/edit";
    }

    @PostMapping("/save")
    public String save(Producto producto, @RequestParam("img") MultipartFile file) throws IOException {
        Usuario usuario = new Usuario();
        usuario.setId(1);
        producto.setUsuario(usuario);
        producto.setImagen(uploadFileService.saveImage(file));
        log.info("Producto: {}", producto);
        productoService.save(producto);
        return "redirect:/productos";
    }

    @PostMapping("/update")
    public String update(Producto producto, @RequestParam("img") MultipartFile file) throws IOException {
        Usuario usuario = new Usuario();
        usuario.setId(1);
        producto.setUsuario(usuario);
        if (file.isEmpty()) {
            Producto producto1 = productoService.findById(producto.getId()).get();
            producto.setImagen(producto1.getImagen());
        } else {
            producto.setImagen(uploadFileService.saveImage(file));
            uploadFileService.deleteImage(producto.getImagen());
        }
        log.info("Producto: {}", producto);
        productoService.update(producto);
        return "redirect:/productos";
    }
}
