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
        stage('Run') {
            agent {
                docker {
                    image 'openjdk:21' // Образ с JDK 21
                    args '-u root:root'
                }
            }
            steps {
                script {
                    def javaHome = sh(script: "docker run --rm openjdk:21 sh -c 'echo \$JAVA_HOME'", returnStdout: true).trim()
                    echo "JAVA_HOME is set to: ${javaHome}"
                }
                echo 'Hello, JDK'
                // Здесь можно добавить команды для запуска приложения
            }
        }
    }
}







