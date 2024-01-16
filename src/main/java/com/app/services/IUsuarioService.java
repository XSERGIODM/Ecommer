package com.app.services;

import com.app.models.Usuario;

import java.util.List;
import java.util.Optional;

public interface IUsuarioService {
    Optional<Usuario> findById(Integer id);
    Usuario save(Usuario usuario);
    Optional<Usuario> findByEmailIgnoreCase(String email);
    List<Usuario> findAll();
}
