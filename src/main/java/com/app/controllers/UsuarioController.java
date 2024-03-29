package com.app.controllers;

import com.app.models.Usuario;
import com.app.security.SpringBootSecurity;
import com.app.services.IOrdenService;
import com.app.services.IUsuarioService;
import jakarta.servlet.http.HttpSession;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/usuario")
@Slf4j
@AllArgsConstructor
@FieldDefaults( makeFinal = true,level = AccessLevel.PRIVATE)
public class UsuarioController {

    IUsuarioService usuarioService;
    IOrdenService ordenService;
    BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/registro")
    public String registro() {
        return "user/registro";
    }

    @GetMapping("/login")
    public String login() {
        return "user/login";
    }
    @PostMapping("/login")
    public String loginn() {
        return "user/login";
    }

    @GetMapping("/compras")
    public String compras(HttpSession session, Model model) {
        model.addAttribute("listaOrdenes", ordenService.findByUsuario_Id((Integer) session.getAttribute("usuarioId")));
        model.addAttribute("sessionModel", session.getAttribute("usuarioId"));
        return "user/compras";
    }

    @GetMapping("/detallesCompras/{id}")
    public String detallesCompras(HttpSession session, Model model, @PathVariable(name = "id") Integer id) {
        model.addAttribute("listaDetallesCompras", ordenService.findById(id).get().getDetalleOrden());
        model.addAttribute("sessionModel", session.getAttribute("usuarioId"));
        return "user/detalleCompra";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("usuarioId");
        session.invalidate();
        return "redirect:/";
    }

    @PostMapping("/save")
    public String save(Usuario usuario) {
        usuario.setTipo("USER");
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        usuarioService.save(usuario);
        return "redirect:/usuario/login";
    }

    @GetMapping("/acceder")
    public String acceder(Usuario usuario, HttpSession session) {
        int id = (Integer) session.getAttribute("usuarioId");
        Optional<Usuario> optionalUsuario = usuarioService.findById(id);
        if (optionalUsuario.isPresent()) {
            Usuario usuarioEncontrado = optionalUsuario.get();
            session.setAttribute("usuarioId", usuarioEncontrado.getId());
            if (usuarioEncontrado.getTipo().equals("ADMIN")) {
                log.info("Acceso: {}", usuarioEncontrado);
                return "redirect:/administrador";
            } else {
                log.info("Acceso: {}", usuarioEncontrado);
                return "redirect:/";
            }
        } else {
            log.info("Usuario no encontrado");
            return "redirect:/usuario/login";
        }
    }
}
