package com.banco.kafka.risk_service.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;

@Service
public class RiskEvaluationService {

    private static final Logger log = LoggerFactory.getLogger(RiskEvaluationService.class);
    private final StringRedisTemplate redisTemplate;

    // Definimos un límite 100.000 CLP
    private static final BigDecimal LIMITE_ALTO_RIESGO = new BigDecimal("100000");

    public RiskEvaluationService(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public boolean esTransferenciaSegura(String cuentaOrigen, BigDecimal monto) {
        // 1. Consultamos a Redis por el estado del cliente
        String estadoCliente = redisTemplate.opsForValue().get("cliente:" + cuentaOrigen + ":estado");

        // 2. Lógica de negocio:
        // Si el cliente está bloqueado en Redis, rechazamos de inmediato
        if ("BLOQUEADO".equals(estadoCliente)) {
            log.warn("RIESGO CRÍTICO: El cliente {} se encuentra BLOQUEADO en Redis.", cuentaOrigen);
            return false;
        }

        // 3. Si el monto supera límite, simulamos una validación extra
        if (monto.compareTo(LIMITE_ALTO_RIESGO) > 0) {
            log.info("Analizando transferencia de alto valor (${}) para la cuenta {}...", monto, cuentaOrigen);
            return true; // Por ahora lo dejamos pasar si no está bloqueado
        }

        return true;
    }
}