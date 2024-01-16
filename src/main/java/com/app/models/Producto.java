package com.app.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "productos")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    String nombre;
    String descripcion;
    String imagen;
    Double precio;
    Integer cantidad;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

}
