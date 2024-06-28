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
                    image 'docker:latest'
                    args '-u root:root -v /var/run/docker.sock:/var/run/docker.sock'
                }
            }
            steps {
                script {
                    def imageName = "dmitryshved/miracles_store:${env.BUILD_ID}"
                    def dockerfile = "Dockerfile"  // Замените на путь к вашему Dockerfile
                   
                    docker.build(imageName, "-f ${dockerfile} .")
                }
            }
        }

        stage('Push Docker Image') {
            agent any
            steps {
                script {
                    def dockerImage = "dmitryshved/miracles_store:${env.BUILD_ID}"
                    def registryCredentials = 'dockerHub' 
                    
                    withCredentials([usernamePassword(credentialsId: registryCredentials, usernameVariable: 'DOCKER_USERNAME', passwordVariable: 'DOCKER_PASSWORD')]) {                    
                        sh "docker login -u ${DOCKER_USERNAME} -p ${DOCKER_PASSWORD}"
                        sh "docker push ${dockerImage}"
                    }
                }
            }
        }
    }
}






