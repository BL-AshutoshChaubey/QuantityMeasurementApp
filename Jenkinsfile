pipeline {
    agent any

    environment {
        DOCKER_HUB_USER = "your-dockerhub-username"
        AWS_REGION = "us-east-1"
        APP_NAME = "quantity-measurement"
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build Microservices') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Build Frontend') {
            steps {
                dir('quantity-measurement-ui') {
                    sh 'npm install'
                    sh 'npm run build'
                }
            }
        }

        stage('Docker Build & Tag') {
            steps {
                sh 'docker build -t ${DOCKER_HUB_USER}/${APP_NAME}-eureka:latest -f infrastructure/eureka-server/Dockerfile .'
                sh 'docker build -t ${DOCKER_HUB_USER}/${APP_NAME}-gateway:latest -f infrastructure/api-gateway/Dockerfile .'
                sh 'docker build -t ${DOCKER_HUB_USER}/${APP_NAME}-identity:latest -f services/identity-service/Dockerfile .'
                sh 'docker build -t ${DOCKER_HUB_USER}/${APP_NAME}-measurement:latest -f services/measurement-service/Dockerfile .'
                sh 'docker build -t ${DOCKER_HUB_USER}/${APP_NAME}-ui:latest -f quantity-measurement-ui/Dockerfile ./quantity-measurement-ui'
            }
        }

        stage('Docker Push') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'docker-hub-credentials', usernameVariable: 'USER', passwordVariable: 'PASS')]) {
                    sh "docker login -u ${USER} -p ${PASS}"
                    sh "docker push ${DOCKER_HUB_USER}/${APP_NAME}-eureka:latest"
                    sh "docker push ${DOCKER_HUB_USER}/${APP_NAME}-gateway:latest"
                    sh "docker push ${DOCKER_HUB_USER}/${APP_NAME}-identity:latest"
                    sh "docker push ${DOCKER_HUB_USER}/${APP_NAME}-measurement:latest"
                    sh "docker push ${DOCKER_HUB_USER}/${APP_NAME}-ui:latest"
                }
            }
        }

        stage('Deploy to AWS') {
            steps {
                echo 'Deploying to AWS EC2...'
                // Example: Using SSH to pull and restart containers on an EC2 instance
                // withCredentials([sshUserPrivateKey(credentialsId: 'aws-ec2-key', keyFileVariable: 'PEM_FILE')]) {
                //    sh "ssh -i ${PEM_FILE} ec2-user@your-ec2-ip 'docker-compose pull && docker-compose up -d'"
                // }
            }
        }
    }

    post {
        always {
            cleanWs()
        }
    }
}
