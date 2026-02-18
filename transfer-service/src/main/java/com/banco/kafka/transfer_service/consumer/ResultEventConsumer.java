package com.banco.kafka.transfer_service.consumer;

import com.banco.kafka.transfer_service.event.TransferenciaEvaluadaEvent;
import com.banco.kafka.transfer_service.repository.TransferenciaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ResultEventConsumer {

    private static final Logger log = LoggerFactory.getLogger(ResultEventConsumer.class);
    private final TransferenciaRepository repository;

    public ResultEventConsumer(TransferenciaRepository repository) {
        this.repository = repository;
    }

    @KafkaListener(topics = "transferencias-evaluadas", groupId = "transfer-group")
    @Transactional
    public void escucharResultadoRiesgo(TransferenciaEvaluadaEvent evento) {
        log.info("Recibido resultado de riesgo: ID {} -> {}", evento.idTransferencia(), evento.estado());

        // Buscamos la transferencia en Postgres y actualizamos su estado
        repository.findById(evento.idTransferencia()).ifPresentOrElse(transferencia -> {
            transferencia.setEstado(evento.estado());
            repository.save(transferencia);
            log.info("Base de Datos actualizada: Transferencia {}", evento.estado());
        }, () -> log.error("Error: No se encontró la transferencia con ID {}", evento.idTransferencia()));
    }
}