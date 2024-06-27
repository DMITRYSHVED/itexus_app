pipeline {
    agent {
        docker {
            image 'gradle:latest' // Укажите образ Docker, который будете использовать
            args '-v /var/run/docker.sock:/var/run/docker.sock' // Проброс Docker сокета
        }
    }
    stages {
        stage('Build') {
            steps {
                sh 'gradle clean build'
            }
        }
        stage('Test') {
            steps {
                sh 'gradle test'
            }
        }
        stage('Docker Build') {
            steps {
                script {
                    docker.build("my-spring-boot-app:${env.BUILD_ID}")
                }
            }
        }
        stage('Deploy') {
            steps {
                sh "docker run -d -p 8080:8080 my-spring-boot-app:${env.BUILD_ID}"
            }
        }
    }
}


