package com.app.services;

import com.app.models.Producto;

import java.util.List;
import java.util.Optional;

public interface IProductoService {
    Producto save(Producto producto);
    Optional<Producto> findById(Integer id);
    void update(Producto producto);
    void delete(Integer id);
    List<Producto> findAll();
}
