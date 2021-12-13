pipeline {

    agent any
    
    stages {
        environment {
            DB_PASSWORD = credentials('db-password')
        }
        stage('Build') {
            steps {
                echo '----- Build app -----'
                withMaven (maven: 'M3') {
                    sh 'echo ----------------'
                    sh 'echo ${DB_PASSWORD}'
                    sh 'echo ----------------'
                    sh 'mvn compile -Dspring.profiles.active=ci'
                }
            }
        }
        stage('Test') {
            steps {
                echo '----- Test app -----'
                withMaven (maven: 'M3') {
                    sh 'mvn test -Dspring.profiles.active=ci'
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