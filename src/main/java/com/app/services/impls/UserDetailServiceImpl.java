package com.app.services.impls;

import com.app.models.Usuario;
import com.app.services.IUsuarioService;
import jakarta.servlet.http.HttpSession;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@AllArgsConstructor
@Slf4j
public class UserDetailServiceImpl implements UserDetailsService {

    IUsuarioService usuarioService;
    HttpSession httpSession;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Usuario> usuario = usuarioService.findByEmailIgnoreCase(username);
        if (usuario.isPresent()) {
            httpSession.setAttribute("usuarioId", usuario.get().getId());
            return User.builder()
                    .username(usuario.get().getNombre())
                    .password(usuario.get().getPassword())
                    .roles(usuario.get().getTipo())
                    .build();
        } else {
            throw new UsernameNotFoundException("Usuario no encontrado");
        }
    }
}
