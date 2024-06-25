pipeline {
    agent none 
    stages {
        stage('Build') {
            agent { docker { image 'gradle:8.6-jdk21' } } 
            steps {
                echo 'Hello, Gradle'
                sh './gradlew clean build -x test'
            }
        }
        stage('Run') {
            agent { docker { image 'openjdk:21-jdk-slim' } } 
            steps {
                echo 'Hello, JDK'
                
            }
        }
    }
}
