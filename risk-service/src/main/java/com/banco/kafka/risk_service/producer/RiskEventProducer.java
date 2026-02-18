package com.banco.kafka.risk_service.producer;

import com.banco.kafka.risk_service.event.TransferenciaEvaluadaEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class RiskEventProducer {

    private static final Logger log = LoggerFactory.getLogger(RiskEventProducer.class);
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public RiskEventProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publicarResultadoEvaluacion(TransferenciaEvaluadaEvent evento) {
        log.info("Publicando resultado de riesgo para transaccion: {}", evento.idTransferencia());
        // Enviamos el resultado a un NUEVO tópico
        this.kafkaTemplate.send("transferencias-evaluadas", evento);
    }
}
