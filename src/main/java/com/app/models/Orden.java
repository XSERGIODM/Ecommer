package com.app.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

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

    @ToString.Exclude
    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "detalle_orden_id", unique = true)
    private DetalleOrden detalleOrden;

}
