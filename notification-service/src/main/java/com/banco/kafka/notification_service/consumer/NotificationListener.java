package com.banco.kafka.notification_service.consumer;

import com.banco.kafka.notification_service.event.TransferenciaEvaluadaEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class NotificationListener {

    private static final Logger log = LoggerFactory.getLogger(NotificationListener.class);

    @KafkaListener(topics = "transferencias-evaluadas", groupId = "notification-group")
    public void enviarNotificacion(TransferenciaEvaluadaEvent evento) {
        log.info("--------------------------------------------------");
        log.info("PREPARANDO ENVÍO DE CORREO AL CLIENTE...");

        if ("APROBADA".equals(evento.estado())) {
            log.info("Asunto: Tu transferencia ha sido exitosa");
            log.info("Cuerpo: La transacción {} se completó. {}", evento.idTransferencia(), evento.mensaje());
        } else {
            log.warn("Asunto: Problemas con tu transferencia");
            log.warn("Cuerpo: La transacción {} fue rechazada. Motivo: {}", evento.idTransferencia(), evento.mensaje());
        }
        log.info("--------------------------------------------------");
    }
}
