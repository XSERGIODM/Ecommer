package com.app.controllers;

import com.app.models.DetalleOrden;
import com.app.models.Orden;
import com.app.models.Producto;
import com.app.models.Usuario;
import com.app.services.IDestalleOrdenService;
import com.app.services.IOrdenService;
import com.app.services.IProductoService;
import com.app.services.IUsuarioService;
import jakarta.servlet.http.HttpSession;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/")
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HomeController {

    final IProductoService productoService;
    final IUsuarioService usuarioService;
    final IOrdenService ordenService;
    final IDestalleOrdenService detalleOrdenService;
    List<DetalleOrden> detalleOrdenList = new ArrayList<>();
    Orden orden = new Orden();

    public HomeController(IProductoService productoService, IUsuarioService usuarioService, IOrdenService ordenService, IDestalleOrdenService detalleOrdenService) {
        this.productoService = productoService;
        this.usuarioService = usuarioService;
        this.ordenService = ordenService;
        this.detalleOrdenService = detalleOrdenService;
        orden.setTotal(0.0);
    }


    @GetMapping("")
    public String home(Model model, HttpSession session) {
        log.info("Session: {}", session.getAttribute("usuarioId"));
        model.addAttribute("listaProducto", productoService.findAll());
        return "user/home";
    }

    @GetMapping("productoHome/{id}")
    public String productoHome(@PathVariable(value = "id") Integer id, Model model) {
        model.addAttribute("producto", productoService.findById(id).get());
        return "user/productoHome";
    }

    @GetMapping("/delete/cart/{id}")
    public String deleteProductCart(@PathVariable(value = "id") Integer id, Model model) {
        List<DetalleOrden> detalleOrdenListTemp = new ArrayList<>();

        detalleOrdenList.forEach(detalleOrden -> {
            if (!detalleOrden.getProducto().getId().equals(id)) {
                detalleOrdenListTemp.add(detalleOrden);
            }
        });

        detalleOrdenList = detalleOrdenListTemp;
        double sumaTotal = detalleOrdenList.stream().mapToDouble(DetalleOrden::getTotal).sum();
        orden.setTotal(sumaTotal);

        model.addAttribute("listaDetalleOrden", detalleOrdenList);
        model.addAttribute("orden", orden);
        return "user/carrito";
    }

    @GetMapping("/getCart")
    public String getCart(Model model) {
        model.addAttribute("listaDetalleOrden", detalleOrdenList);
        model.addAttribute("orden", orden);
        return "user/carrito";
    }

    @GetMapping("/order")
    public String order(Model model, HttpSession session) {
        int usuarioId = (int) session.getAttribute("usuarioId");
        model.addAttribute("listaDetalleOrden", detalleOrdenList);
        model.addAttribute("orden", orden);
        model.addAttribute("usuario", usuarioService.findById(usuarioId).get());
        return "user/resumenOrden";
    }

    @GetMapping("/saveOrder")
    public String saveOrder(HttpSession session) {
        if (!detalleOrdenList.isEmpty()){
            orden.setFechaCreacion(LocalDateTime.now());
            orden.setNumero(ordenService.generarNUmeroOrden());

            int usuarioId = (int) session.getAttribute("usuarioId");
            Usuario usuario = usuarioService.findById(usuarioId).get();
            orden.setUsuario(usuario);

            ordenService.save(orden);

            detalleOrdenList.forEach(detalleOrden -> {
                detalleOrden.setOrden(orden);
                detalleOrdenService.save(detalleOrden);
            });

            detalleOrdenList = new ArrayList<>();
            orden = new Orden();
            orden.setTotal(0.0);
        }
        return "redirect:/";
    }

    @PostMapping("/cart")
    public String addCart(@RequestParam Integer id, @RequestParam Integer cantidad, Model model) {
        DetalleOrden detalleOrden = new DetalleOrden();
        Producto producto = productoService.findById(id).get();
        double sumaTotal;

        detalleOrden.setCantidad(cantidad);
        detalleOrden.setPrecio(producto.getPrecio());
        detalleOrden.setNombre(producto.getNombre());
        detalleOrden.setTotal(producto.getPrecio() * cantidad);
        detalleOrden.setProducto(producto);

        // Validar si el producto ya existe en la lista, no se debe agregar
        if (detalleOrdenList.stream().noneMatch(detalle -> detalle.getProducto().getId().equals(producto.getId()))) {
            detalleOrdenList.add(detalleOrden);
        }

        sumaTotal = detalleOrdenList.stream().mapToDouble(DetalleOrden::getTotal).sum();

        orden.setTotal(sumaTotal);

        model.addAttribute("listaDetalleOrden", detalleOrdenList);
        model.addAttribute("orden", orden);


        return "user/carrito";
    }

    @PostMapping("/searchProduct")
    public String searchProduct(@RequestParam String nombre , Model model) {
        log.info("Nombre: " + nombre);
        List<Producto> listaProducto = productoService.findByNombreContainsIgnoreCase(nombre);
        model.addAttribute("listaProducto", listaProducto);
        return "user/home";
    }

}
