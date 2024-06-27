pipeline {
    agent none
    stages {
        stage('Git Checkout') {
            steps {
                script {
                    git branch: 'master',
                        url: 'https://github.com/DMITRYSHVED/itexus_app'
                }
            }
        }
        stage('Build') {
            agent {
                docker {
                    image 'gradle:8.6-jdk21' // Образ с Gradle и JDK 21
                    args '-u root:root'
                }
            }
            // environment {
            //     JAVA_HOME = "/usr/lib/jvm/java-21-openjdk" // Установите правильный путь к JDK
            //     PATH = "$JAVA_HOME/bin:$PATH"
            // }
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
            environment {
                JAVA_HOME = "/usr/lib/jvm/java-21-openjdk" // Установите правильный путь к JDK
                PATH = "$JAVA_HOME/bin:$PATH"
            }
            steps {
                echo 'Hello, JDK'
                // Здесь можно добавить команды для запуска приложения
            }
        }
    }
}






