package com.banco.kafka.transfer_service.model;
import java.math.BigDecimal;

public record TransferenciaRequestDTO(
        String cuentaOrigen,
        String cuentaDestino,
        BigDecimal monto
) {}
