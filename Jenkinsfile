pipeline {
    agent none
    stages {
        stage('Checkout') {
            agent any
            steps {
                checkout scm
            }
        }
      
        stage('Build Docker Image') {
            agent {
                docker {
                    image 'docker:latest' // Используем Docker образ
                    args '-u root:root -v /var/run/docker.sock:/var/run/docker.sock'
                }
            }
            steps {
                script {
                    def imageName = "dmitryshved/miracles_store:${env.BUILD_ID}"
                    def dockerfile = "Dockerfile"  // Замените на путь к вашему Dockerfile
                    
                    // Собираем Docker образ
                    docker.build(imageName, "-f ${dockerfile} .")
                }
            }
        }
    }
}






