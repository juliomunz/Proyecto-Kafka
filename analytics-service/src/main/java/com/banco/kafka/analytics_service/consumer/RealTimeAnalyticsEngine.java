package com.banco.kafka.analytics_service.consumer;

import com.banco.kafka.analytics_service.event.TransferenciaIniciadaEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

@Component
public class RealTimeAnalyticsEngine {

    private static final Logger log = LoggerFactory.getLogger(RealTimeAnalyticsEngine.class);

    // Variables Thread-Safe para mantener el estado en memoria
    private final AtomicInteger totalTransacciones = new AtomicInteger(0);
    private final AtomicReference<BigDecimal> volumenTotalDinero = new AtomicReference<>(BigDecimal.ZERO);

    @KafkaListener(topics = "transferencias-iniciadas", groupId = "analytics-group")
    public void procesarMetricas(TransferenciaIniciadaEvent evento) {

        // 1. Aumentar el contador de transacciones
        int totalTx = totalTransacciones.incrementAndGet();

        // 2. Acumular el volumen de dinero de forma segura (expresión lambda)
        BigDecimal volumenActual = volumenTotalDinero.accumulateAndGet(
                evento.monto(),
                BigDecimal::add
        );

        log.info("--- DASHBOARD ANALÍTICO EN TIEMPO REAL ---");
        log.info("Transacciones procesadas : {}", totalTx);
        log.info("Volumen total transado   : ${}", volumenActual);
        if (evento.monto().intValue() == 666) {
            log.warn("ALERTA PATRÓN INUSUAL: Detectada transacción con monto sospechoso ($666)");
        }
        log.info("------------------------------------------------");
    }
}
