# Proyecto-Kafka
Coreografía de Microservicios: Simulación Bancaria con Kafka y Redis

Descripción:
Sistema distribuido de procesamiento de transferencias en tiempo real bajo el Patrón Saga (Coreografía). El proyecto simula el flujo de una transacción desde su creación hasta su validación de riesgo y notificación final, utilizando una arquitectura orientada a eventos.

Stack Tecnológico:

Backend: Java 17, Spring Boot 3.x

Mensajería: Apache Kafka

Caché: Redis (Detección de fraude y límites)

Base de Datos: PostgreSQL

Contenedores: Docker & Docker Compose

Arquitectura del Flujo:

transfer-service: Recibe la petición (REST) y publica el evento transferencias-iniciadas.

risk-service: Valida la transacción consultando estados en Redis. Publica el resultado en transferencias-evaluadas. Posee manejo de Dead Letter Queue (DLQ) para fallos críticos.

notification-service: Escucha los resultados y simula el envío de alertas al usuario (Patrón Fan-out).

analytics-service: Motor de analítica que procesa el volumen de transacciones y montos en tiempo real.

Cómo ejecutarlo:

Clonar el repositorio.

Ir a transfer-service/ y ejecutar docker-compose up -d.

Levantar cada microservicio en el orden: Transfer -> Risk -> Notification -> Analytics.
