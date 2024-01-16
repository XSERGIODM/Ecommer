package com.app.services;

import com.app.models.Orden;

import java.util.List;

public interface IOrdenService {
    Orden save (Orden orden);
    List<Orden> findAll();
    String generarNUmeroOrden();
    List<Orden> findByUsuario_Id(Integer id);
}
