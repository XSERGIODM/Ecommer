package com.app.services;

import com.app.models.Usuario;
import com.app.repositories.IUsuarioRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class UsuarioServiceImpl implements IUsuarioService{

    IUsuarioRepository IUsuarioRepository;

    @Override
    public Optional<Usuario> findById(Integer id) {
        return IUsuarioRepository.findById(id);
    }
}
