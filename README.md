# Proyecto-Kafka: Coreografía de Microservicios & CI/CD Pipeline

Este repositorio contiene un sistema distribuido de procesamiento de transferencias en tiempo real bajo el **Patrón Saga (Coreografía)**, integrado con un ecosistema completo de **CI/CD** y observabilidad.

## 🚀 Arquitectura y Stack Tecnológico

* **Microservicios**: Java 17, Spring Boot 3.x (Transfer, Risk, Notification, Analytics).
* **Mensajería**: Apache Kafka (Modo KRaft).
* **Caché & Estado**: Redis.
* **Persistencia**: PostgreSQL.
* **CI/CD & Calidad**: Jenkins & SonarQube.
* **Infraestructura**: Docker & Docker Compose (Orquestación unificada).

## 🛠️ Pipeline de CI/CD (Jenkinsfile)

El proyecto incluye un flujo de automatización profesional con las siguientes etapas:
1. **Checkout**: Sincronización con el repositorio remoto.
2. **Build & Test**: Compilación con Maven Wrapper.
3. **Static Analysis**: Escaneo de calidad de código en SonarQube mediante Tokens de seguridad.
4. **Docker Build**: Generación de imágenes inmutables `mi-banco/transfer-service`.
5. **Auto-Deploy**: Despliegue automatizado del contenedor en la red `bank-network` con inyección dinámica de variables de entorno.

## 📦 Cómo ejecutar el Laboratorio

Para garantizar la portabilidad del entorno, se ha unificado la infraestructura core y las herramientas de soporte en un solo manifiesto.

### Requisitos previos
- Docker & Docker Compose instalado. Puertos libres: 
  - 8080 (Kafka-UI)
  - 8081 (App)
  - 8082 (Jenkins)
  - 9000 (SonarQube)
  - 5433 (Postgres)

### Pasos de ejecución
1. **Clonar el repositorio**:
   ```bash
   git clone https://github.com/juliomunz/Proyecto-Kafka.git
Levantar el ecosistema completo:

Bash
docker-compose up -d --build
Acceso a Herramientas:

Jenkins: http://localhost:8082

SonarQube: http://localhost:9000

Kafka-UI: http://localhost:8080

Configuración del Pipeline
Para ejecutar el build, cree un nuevo ítem de tipo "Pipeline" en Jenkins y apunte a este repositorio. El Jenkinsfile se encargará de orquestar el despliegue automático del microservicio transfer-service dentro del ecosistema de contenedores.