package com.banco.kafka.transfer_service.service;

import com.banco.kafka.transfer_service.event.TransferenciaIniciadaEvent;
import com.banco.kafka.transfer_service.model.Transferencia;
import com.banco.kafka.transfer_service.model.TransferenciaRequestDTO;
import com.banco.kafka.transfer_service.producer.TransferEventProducer;
import com.banco.kafka.transfer_service.repository.TransferenciaRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransferenciaService {

    private static final Logger log = LoggerFactory.getLogger(TransferenciaService.class);
    private final TransferenciaRepository repository;
    private final TransferEventProducer producer;

    public TransferenciaService(TransferenciaRepository repository, TransferEventProducer producer) {
        this.repository = repository;
        this.producer = producer;
    }

    @Transactional
    public Transferencia iniciarTransferencia(TransferenciaRequestDTO request) {
        log.info("Procesando nueva solicitud de transferencia...");

        // 1. Guardar en Base de Datos como PENDIENTE
        Transferencia transferencia = new Transferencia();
        transferencia.setCuentaOrigen(request.cuentaOrigen());
        transferencia.setCuentaDestino(request.cuentaDestino());
        transferencia.setMonto(request.monto());
        transferencia.setEstado("PENDIENTE"); // Estado inicial de la Saga

        Transferencia guardada = repository.save(transferencia);

        // 2. Crear el evento
        TransferenciaIniciadaEvent evento = new TransferenciaIniciadaEvent(
                guardada.getId(),
                guardada.getCuentaOrigen(),
                guardada.getCuentaDestino(),
                guardada.getMonto(),
                guardada.getEstado()
        );

        // 3. Publicar en Kafka
        producer.publicarTransferenciaIniciada(evento);

        return guardada;
    }
}
