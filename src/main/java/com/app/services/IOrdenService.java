package com.app.services;

import com.app.models.Orden;

import java.util.List;
import java.util.Optional;

public interface IOrdenService {
    Orden save (Orden orden);
    List<Orden> findAll();
    String generarNUmeroOrden();
    List<Orden> findByUsuario_Id(Integer id);

    Optional<Orden> findById(Integer id);
}
