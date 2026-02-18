package com.banco.kafka.risk_service.event;

import java.math.BigDecimal;

public record TransferenciaIniciadaEvent(
        String idTransferencia,
        String cuentaOrigen,
        String cuentaDestino,
        BigDecimal monto,
        String estado
) {}
