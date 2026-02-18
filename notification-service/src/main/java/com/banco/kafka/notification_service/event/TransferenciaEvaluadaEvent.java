package com.banco.kafka.notification_service.event;

public record TransferenciaEvaluadaEvent(
        String idTransferencia,
        String estado,
        String mensaje
) {}
