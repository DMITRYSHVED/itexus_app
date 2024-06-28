pipeline {
    agent none
    stages {
        stage('Checkout') {
            agent any
            steps {
                checkout scm
            }
        }
        // stage('Build') {
        //     agent {
        //         docker {
        //             image 'gradle:8.6-jdk21' // Образ с Gradle и JDK 21
        //             args '-u root:root'
        //         }
        //     }
        //     steps {
        //         echo 'Hello, Gradle'
        //         sh 'chmod +x ./gradlew' // Устанавливаем права на выполнение
        //         sh './gradlew clean build -x test'
        //     }
        // }
        stage('Build Docker Image') {
            agent {
                docker {
                    image 'docker:latest' // Используем Docker образ
                    args '-u root:root -v /var/run/docker.sock:/var/run/docker.sock'
                    // Укажите путь к Dockerfile, который находится в корне проекта
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







