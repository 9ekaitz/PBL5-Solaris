pipeline {

    agent any
    
    stages {
        stage('Build') {
            steps {
                echo '----- Build app -----'
                withMaven (maven: 'M3') {
                    sh 'mvn compile'
                }
            }
        }
        stage('Test') {
            steps {
                echo '----- Test app -----'
                withMaven (maven: 'M3') {
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
}