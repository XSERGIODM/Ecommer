package com.app.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Orden {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    String numero;
    LocalDateTime fechaCreacion;
    LocalDateTime fechaRecibido;
    Double total;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @OneToMany(mappedBy = "orden", orphanRemoval = true)
    private List<DetalleOrden> detalleOrden;

}
