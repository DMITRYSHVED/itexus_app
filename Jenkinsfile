pipeline {
    agent none
    stages {
        stage('Checkout') {
            agent any
            steps {
                checkout scm
            }
        }
        stage('Build') {
            agent {
                docker {
                    image 'gradle:8.6-jdk21' // Образ с Gradle и JDK 21
                    args '-u root:root'
                }
            }
            steps {
                echo 'Hello, Gradle'
                sh 'chmod +x ./gradlew' // Устанавливаем права на выполнение
                sh './gradlew clean build -x test'
            }
        }
        
    }
}







