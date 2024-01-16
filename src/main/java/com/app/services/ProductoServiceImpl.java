package com.app.services;

import com.app.models.Producto;
import com.app.repositories.IProductoRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class ProductoServiceImpl implements IProductoService{

    IProductoRepository IProductoRepository;

    @Override
    public Producto save(Producto producto) {
        return IProductoRepository.save(producto);
    }

    @Override
    public Optional<Producto> findById(Integer id) {
        return IProductoRepository.findById(id);
    }

    @Override
    public void update(Producto producto) {
        IProductoRepository.save(producto);
    }

    @Override
    public void delete(Integer id) {
        IProductoRepository.deleteById(id);
    }

    @Override
    public List<Producto> findAll() {
        return IProductoRepository.findAll();
    }
}
