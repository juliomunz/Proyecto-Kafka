pipeline {
agent any
    environment {
        DOCKER_API_VERSION = '1.43'
    }
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
                // Este comando es infalible: nos muestra el contenido real del archivo en el servidor
                sh 'cat ./transfer-service/Dockerfile'
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