package com.app.controllers;

import com.app.models.DetalleOrden;
import com.app.models.Orden;
import com.app.models.Producto;
import com.app.services.IProductoService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/")
@Slf4j
@AllArgsConstructor
@FieldDefaults(makeFinal = true,level = AccessLevel.PRIVATE)
public class HomeController {

    IProductoService productoService;
    List<DetalleOrden> detalleOrdenList = new ArrayList<>();
    Orden orden = new Orden();

    @GetMapping("")
    public String home(Model model) {
        model.addAttribute("listaProducto", productoService.findAll());
        return "user/home";
    }

    @GetMapping("productoHome/{id}")
    public String productoHome(@PathVariable(value = "id") Integer id, Model model) {
        model.addAttribute("producto", productoService.findById(id).get());
        return "user/productoHome";
    }

    @PostMapping("/cart")
    public String addCart(@RequestParam Integer id, @RequestParam Integer cantidad, Model model) {
        DetalleOrden detalleOrden = new DetalleOrden();
        Producto producto = productoService.findById(id).get();
        double sumaTotal = 0;

        detalleOrden.setCantidad(cantidad);
        detalleOrden.setPrecio(producto.getPrecio());
        detalleOrden.setNombre(producto.getNombre());
        detalleOrden.setTotal(producto.getPrecio() * cantidad);
        detalleOrden.setProducto(producto);

        detalleOrdenList.add(detalleOrden);
        sumaTotal = detalleOrdenList.stream().mapToDouble(DetalleOrden::getTotal).sum();

        orden.setTotal(sumaTotal);

        model.addAttribute("listaDetalleOrden", detalleOrdenList);
        model.addAttribute("orden", orden);


        return "user/carrito";
    }

}
