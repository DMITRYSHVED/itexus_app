pipeline {
    agent none
    stages {
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
        stage('Run') {
            agent {
                docker {
                    image 'openjdk:21' // Образ с JDK 21
                    args '-u root:root'
                }
            }
            steps {
                echo 'Hello, JDK'
                // Здесь можно добавить команды для запуска приложения
            }
        }
    }
}





