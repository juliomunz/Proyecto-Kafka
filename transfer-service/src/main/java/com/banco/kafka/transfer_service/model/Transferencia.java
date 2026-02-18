package com.banco.kafka.transfer_service.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "transferencias")
@Data
public class Transferencia {
    @Id
    private String id;
    private String cuentaOrigen;
    private String cuentaDestino;
    private BigDecimal monto;
    private String estado; // PENDIENTE, COMPLETADA, RECHAZADA
    private LocalDateTime fechaCreacion;

    public Transferencia() {
        this.id = UUID.randomUUID().toString();
        this.fechaCreacion = LocalDateTime.now();
    }
}
