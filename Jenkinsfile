pipeline {
    agent none
    stages {
        stage('Build') {
            agent {
                docker {
                    image 'gradle:8.6-jdk21'
                    args '-u root:root'
                }
            }
            steps {
                echo 'Hello, Gradle'
                sh './gradlew clean build -x test'
            }
        }
        stage('Run') {
            agent {
                docker {
                    image 'openjdk:21'
                    args '-u root:root'
                }
            }
            steps {
                echo 'Hello, JDK'
                // run app
            }
        }
    }
}



