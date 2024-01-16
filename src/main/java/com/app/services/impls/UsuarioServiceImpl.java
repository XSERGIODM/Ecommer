package com.app.services.impls;

import com.app.models.Usuario;
import com.app.repositories.IUsuarioRepository;
import com.app.services.IUsuarioService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class UsuarioServiceImpl implements IUsuarioService {

    IUsuarioRepository usuarioRepository;

    @Override
    public Optional<Usuario> findById(Integer id) {
        return usuarioRepository.findById(id);
    }

    @Override
    public Usuario save(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @Override
    public Optional<Usuario> findByEmailIgnoreCase(String email) {
        return usuarioRepository.findByEmailIgnoreCase(email);
    }
}
