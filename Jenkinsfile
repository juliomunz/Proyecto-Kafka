pipeline {
    agent any
    environment {
        DOCKER_API_VERSION = '1.43'
    }
    stages {
        stage('1. Checkout Code') {
            steps {
                checkout scm
                echo 'Código descargado correctamente.'
            }
        }

        stage('2. Build & Test (Maven)') {
            steps {
                dir('transfer-service') {
                    sh './mvnw clean package -DskipTests'
                }
            }
        }

        stage('3. Static Analysis (SonarQube)') {
            steps {
                dir('transfer-service') {
                    echo 'Iniciando análisis de calidad portátil...'
                    sh './mvnw sonar:sonar \
                        -Dsonar.host.url=http://sonarqube:9000 \
                        -Dsonar.token=squ_f5f87e09cb7b294224bd57afd4d9f642c924a8e1'
                }
            }
        }

        stage('4. Build Image (Docker)') {
            steps {
                sh 'docker build --no-cache -t mi-banco/transfer-service:latest ./transfer-service'
            }
        }

        stage('5. Auto-Deploy (Run Container)') {
            steps {
                script {
                    sh 'docker rm -f transfer-service-live || true'
                    sh '''
                    docker run -d \
                        --name transfer-service-live \
                        --network bank-network \
                        -p 8081:8081 \
                        -e SPRING_KAFKA_BOOTSTRAP_SERVERS=bank_kafka:9092 \
                        -e SPRING_DATASOURCE_URL=jdbc:postgresql://bank_postgres:5432/bank_db \
                        mi-banco/transfer-service:latest
                    '''
                }
            }
        }
    }
    post {
        always {
            cleanWs()
        }
    }
}