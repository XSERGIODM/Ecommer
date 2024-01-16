package com.app.repositories;

import com.app.models.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IProductoRepository extends JpaRepository<Producto, Integer> {
    List<Producto> findByNombreContainsIgnoreCase(String nombre);

}