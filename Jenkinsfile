pipeline {

    agent any

    stages {
        stage('Build') {
            steps {
                echo '----- Build app -----'
                withMaven {
                    sh 'mvn compile'
                }
            }
        }
        stage('Test') {
            steps {
                echo '----- Test app -----'
                withMaven {
                    sh 'mvn test'
                }
            }
        }
        stage('Deploy') {
            steps {
                echo '----- Deploy app -----'
            }
        }
    }
    post {
        always {
            
        }
    }
}