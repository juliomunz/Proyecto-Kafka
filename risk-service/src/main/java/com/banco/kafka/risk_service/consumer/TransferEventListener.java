package com.banco.kafka.risk_service.consumer;

import com.banco.kafka.risk_service.event.TransferenciaIniciadaEvent;
import com.banco.kafka.risk_service.event.TransferenciaEvaluadaEvent;
import com.banco.kafka.risk_service.producer.RiskEventProducer;
import com.banco.kafka.risk_service.service.RiskEvaluationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class TransferEventListener {

    private static final Logger log = LoggerFactory.getLogger(TransferEventListener.class);
    private final RiskEvaluationService riskService;
    private final RiskEventProducer riskProducer;

    public TransferEventListener(RiskEvaluationService riskService, RiskEventProducer riskProducer) {
        this.riskService = riskService;
        this.riskProducer = riskProducer;
    }

    @KafkaListener(topics = "transferencias-iniciadas", groupId = "risk-group-v2")
    public void consumirEventoTransferencia(TransferenciaIniciadaEvent evento) {
        log.info("EVALUANDO RIESGO: ID {}", evento.idTransferencia());

        if (evento.monto().intValue() == 666) {
            log.error("ERROR DEL SISTEMA: Falla simulada al procesar transferencia!");
            throw new RuntimeException("Falla interna conectando a la base de datos de riesgo");
        }

        // 1. Evaluar con la lógica de Redis
        boolean esSegura = riskService.esTransferenciaSegura(evento.cuentaOrigen(), evento.monto());

        // 2. Crear el evento de respuesta
        TransferenciaEvaluadaEvent respuesta = new TransferenciaEvaluadaEvent(
                evento.idTransferencia(),
                esSegura ? "APROBADA" : "RECHAZADA",
                esSegura ? "Evaluación exitosa" : "Riesgo detectado / Cliente bloqueado"
        );

        // 3. ¡Publicar el resultado de vuelta a Kafka!
        riskProducer.publicarResultadoEvaluacion(respuesta);

        log.info("Resultado '{}' enviado a Kafka.", respuesta.estado());
        log.info("========================================");
    }

    @KafkaListener(topics = "transferencias-iniciadas-dlt", groupId = "dlq-group")
    public void escucharMensajesMuertos(TransferenciaIniciadaEvent eventoFallido) {
        log.error("[DLQ] TRANSFERENCIA EN CUARENTENA RECIBIDA");
        log.error("[DLQ] La transacción {} falló después de todos los reintentos.", eventoFallido.idTransferencia());
        log.error("[DLQ] Guardando en tabla de auditoría para revisión manual...");
        // Simula guardar en una bd real
    }

}
