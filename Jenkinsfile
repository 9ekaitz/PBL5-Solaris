pipeline {

    agent any
    
    stages {
        stage('Static Analysis') {
            steps {
                withMaven(maven: 'M3') {
                    withSonarQubeEnv(installationName:'SonarQube', credentialsId: 'sonar-token') {
                        withCredentials([string(credentialsId: 'jasypt-secret', variable: 'JASYPT'), 
                                        string(credentialsId: 'sonar-token', variable: 'SONAR_TOKEN')]) {
                                            sh 'mvn sonar:sonar \
                                                -Dsonar.projectKey=solaris \
                                                -Dsonar.host.url=https://sonarsolaris.ddns.net \
                                                -Dsonar.login=${SONAR_TOKEN} \
                                                -Dsonar.qualityProfile=solaris'
                        }
                    }
                }
            }
        }
        stage('QualityGate') {
            steps {
                timeout(time: 5, unit: 'MINUTES') {
                    waitForQualityGate abortPipeline: true, credentialsId: 'sonar-token'
                }
            }
        }
        stage('Build') {
            steps {
                echo '----- Build app -----'
                withMaven (maven: 'M3') {
                    withCredentials([string(credentialsId: 'jasypt-secret', variable: 'JASYPT')]) {
                        sh 'mvn clean compile -Dspring.profiles.active=ci \
                            -Djasypt.encryptor.password=${JASYPT}'
                    }
                }
            }
        }
        stage('Unit Testing') {
            steps {
                echo '----- Test app -----'
                withMaven (maven: 'M3') {
                    withCredentials([string(credentialsId: 'jasypt-secret', variable: 'JASYPT')]) {
                        sh 'mvn test -Dspring.profiles.active=ci \
                            -Djasypt.encryptor.password=${JASYPT}'
                    }
                }
            }
        }
        stage('Generate WARs') {
            steps {
                withMaven (maven: 'M3') {
                    withCredentials([string(credentialsId: 'jasypt-secret', variable: 'JASYPT')]) {
                        sh 'mvn package -Dspring.profiles.active=ci -Djasypt.encryptor.password=${JASYPT}'
                    }
                }
            }
        }
        stage('Deploy') {
            steps {
                echo '----- Deploy app -----'
                script {
                    deploy adapters: [tomcat9(path: '', url: 'http://kindercloud.ddns.net:9003')], contextPath: '/', onFailure: false, war: '**/*.war'  
                }
            }
        }
    }
}