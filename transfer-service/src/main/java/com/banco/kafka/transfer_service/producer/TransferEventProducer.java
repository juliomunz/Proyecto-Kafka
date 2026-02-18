package com.banco.kafka.transfer_service.producer;

import com.banco.kafka.transfer_service.event.TransferenciaIniciadaEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class TransferEventProducer {

    private static final Logger log = LoggerFactory.getLogger(TransferEventProducer.class);
    private static final String TOPIC = "transferencias-iniciadas"; // El nombre de nuestro tópico

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public TransferEventProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publicarTransferenciaIniciada(TransferenciaIniciadaEvent evento) {
        log.info("Iniciando SAGA: Publicando evento de transferencia -> {}", evento);
        // Enviamos el evento a Kafka. La "key" (evento.idTransferencia()) asegura que
        // los eventos de una misma transferencia vayan a la misma partición y mantengan el orden.
        kafkaTemplate.send(TOPIC, evento.idTransferencia(), evento);
    }
}