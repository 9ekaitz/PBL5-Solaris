pipeline {

    agent any
    
    stages {
        stage('Build') {
            steps {
                echo '----- Build app -----'
                withMaven (maven: 'M3') {
                    withCredentials([string(credentialsId: 'db-password', variable: 'DB_PASSWORD')]) {
                        sh 'mvn compile -Dspring.profiles.active=ci'
                    }
                }
            }
        }
        stage('Test') {
            steps {
                echo '----- Test app -----'
                withMaven (maven: 'M3') {
                    withCredentials([string(credentialsId: 'db-password', variable: 'DB_PASSWORD')]) {
                        sh 'mvn test -Dspring.profiles.active=ci'
                    }
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