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

        stage('Push Docker Image') {
            agent any
            steps {
                script {
                    def dockerImage = "dmitryshved/miracles_store:${env.BUILD_ID}"
                    def registryCredentials = 'dockerhub_credentials' // Имя ваших Jenkins credentials для Docker Hub
                    
                    // Аутентификация в Docker Registry
                    withCredentials([usernamePassword(credentialsId: dockerHub, usernameVariable: 'DOCKER_USERNAME', passwordVariable: 'DOCKER_PASSWORD')]) {
                        // Логинимся в Docker Registry
                        sh "docker login -u ${DOCKER_USERNAME} -p ${DOCKER_PASSWORD}"
                        
                        // Пушим Docker образ в Docker Registry
                        sh "docker push ${dockerImage}"
                    }
                }
            }
        }
    }
}






