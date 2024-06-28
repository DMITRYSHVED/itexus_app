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
                    // Указываем Dockerfile в корне проекта
                    dockerfile {
                        filename 'Dockerfile'
                    }
                }
            }
            steps {
                script {
                    def appName = "miracles_store"
                    def imageName = "dmitryshved/${appName}:${env.BUILD_ID}"
                    sh "docker build -t ${imageName} ."
                }
            }
        }
    }
}






