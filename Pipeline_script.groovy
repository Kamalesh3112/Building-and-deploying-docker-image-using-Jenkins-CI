pipeline {
    agent any
    stages {
        stage('Build') {
            steps {
                script {
                    def dockerImage = docker.build("my-image:${env.BUILD_NUMBER}")
                    dockerImage.push()
                }
            }
        }
        stage('Deploy') {
            steps {
                script {
                    sh "kubectl apply -f kubernetes.yaml"
                }
            }
        }
    }
}
