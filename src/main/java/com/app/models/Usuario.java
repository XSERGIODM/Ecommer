package com.app.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    String nombre;
    String username;
    String password;
    String email;
    String direccion;
    String telefono;
    String tipo;

    @ToString.Exclude
    @OneToMany(mappedBy = "usuario", orphanRemoval = true)
    private List<Producto> productos = new ArrayList<>();

    @ToString.Exclude
    @OneToMany(mappedBy = "usuario", orphanRemoval = true)
    private List<Orden> ordenes = new ArrayList<>();

}
