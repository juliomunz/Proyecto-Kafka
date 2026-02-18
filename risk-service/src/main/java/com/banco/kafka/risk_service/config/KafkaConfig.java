package com.banco.kafka.risk_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.util.backoff.FixedBackOff;

@Configuration
public class KafkaConfig {

    // Configuramos el manejador de errores global
    @Bean
    public DefaultErrorHandler errorHandler(KafkaTemplate<String, Object> template) {
        // 1. DeadLetterPublishingRecoverer: Se encarga de enviar el mensaje fallido al DLT
        DeadLetterPublishingRecoverer recoverer = new DeadLetterPublishingRecoverer(template);

        // 2. FixedBackOff: Le decimos que intente 3 veces, esperando 1 segundo (1000ms) entre cada intento
        FixedBackOff backOff = new FixedBackOff(1000L, 2); // 2 reintentos + 1 intento original = 3 intentos

        return new DefaultErrorHandler(recoverer, backOff);
    }
}
