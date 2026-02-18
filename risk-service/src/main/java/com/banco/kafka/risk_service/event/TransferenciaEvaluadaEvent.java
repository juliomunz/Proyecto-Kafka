package com.banco.kafka.risk_service.event;

public record TransferenciaEvaluadaEvent(
        String idTransferencia,
        String estado, // APROBADA o RECHAZADA
        String mensaje
) {}
