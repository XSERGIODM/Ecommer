package com.app.controllers;

import com.app.models.Usuario;
import com.app.services.IUsuarioService;
import jakarta.servlet.http.HttpSession;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@RequestMapping("/usuario")
@Slf4j
@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class UsuarioController {

    IUsuarioService usuarioService;

    @GetMapping("/registro")
    public String registro(){
        return "user/registro";
    }

    @GetMapping("/login")
    public String login(){
        return "user/login";
    }

    @PostMapping("/save")
    public String save(Usuario usuario){
        usuario.setTipo("USER");
        usuarioService.save(usuario);
        return "redirect:/";
    }

    @PostMapping("/acceder")
    public String acceder(Usuario usuario, HttpSession session){

        log.info("Acceso: {}", usuario);
        Optional<Usuario> optionalUsuario = usuarioService.findByEmailIgnoreCase(usuario.getEmail());
        //sin validar la clave aun, si esta el email y dependiendo el tipo me mande a una vista u otra
        if(optionalUsuario.isPresent()){
            Usuario usuarioEncontrado = optionalUsuario.get();
            session.setAttribute("usuarioId", usuarioEncontrado.getId());
            if(usuarioEncontrado.getTipo().equals("ADMIN")){
                log.info("Acceso: {}", usuarioEncontrado);
                return "redirect:/administrador";
            }else{
                log.info("Acceso: {}", usuarioEncontrado);
                return "redirect:/";
            }
        }else {
            log.info("Usuario no encontrado");
            return "redirect:/usuario/login";
        }
    }
}
