pipeline {
    agent any

    environment {
        REGISTRY = "docker.io"
        IMAGE_NAME = "my-kubernetes-image"
        KUBECTL_VERSION = "1.28.2"
    }

    stages {
        stage('Checkout code') {
            steps {
                git branch: 'master', url: 'https://github.com/Kamalesh3112/Building-and-deploying-docker-image-using-Jenkins-CI/tree/main.git'
            }
        }8
        stage('Build Docker image') {
            steps {
                sh """
                    docker build -t $REGISTRY/$IMAGE_NAME .
                """
            }
        }

        stage('Deploy to Kubernetes') {
            steps {
                sh """
                    kubectl apply -f deployment.yaml --namespace my-namespace
                    kubectl apply -f service.yaml --namespace my-namespace
                """
            }
        }

        stage('Post-deployment verification') {
            steps {
                sleep 30
                sh """
                    kubectl get pods --namespace my-namespace
                    kubectl get services --namespace my-namespace
                """
            }
        }
    }

    post {
        success {
            echo "Deployment successful!"
        }
        failure {
            echo "Deployment failed!"
        }
    }
}
