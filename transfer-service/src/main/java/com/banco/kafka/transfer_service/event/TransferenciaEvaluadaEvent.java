package com.banco.kafka.transfer_service.event;

public record TransferenciaEvaluadaEvent(
        String idTransferencia,
        String estado,
        String mensaje
) {}
