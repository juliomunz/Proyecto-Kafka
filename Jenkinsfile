pipeline {
    agent any // Jenkins utilizará cualquier nodo disponible para ejecutar pipe.

    stages {
        stage('1. Checkout Code') {
            steps {
                // Descarga desde repo
                checkout scm
                echo 'Código descargado correctamente.'
            }
        }

        stage('2. Build Maven (transfer-service)') {
            steps {
                // Selecciona directorio del ms utilizando wrapper 'mvnw'.
                dir('transfer-service') {
                    sh './mvnw clean package -DskipTests'
                }
            }
        }

        stage('3. Build Docker Image (transfer-service)') {
            steps {
                // Build de la imagen usando Dockerfile dentro de './transfer-service'.
                sh 'docker build -t mi-banco/transfer-service:latest ./transfer-service'
            }
        }
    }

    post {
        always {
            echo 'Limpiando espacio de trabajo...'
            cleanWs()
        }
        failure {
            echo 'El pipeline falló. Revisar logs de Maven o Docker del ms transfer-service.'
        }
    }
}