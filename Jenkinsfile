pipeline {

    agent any
    
    stages {
        stage('Build') {
            steps {
                echo '----- Build app -----'
                withMaven (maven: 'M3') {
                    sh 'mvn compile --spring.profiles.active=ci'
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