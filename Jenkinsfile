pipeline {

    agent any
    
    stages {
        stage('Static Analysis') {
            steps {
                withMaven(maven: 'M3') {
                    withSonarQubeEnv(installationName:'SonarQube', credentialsId: 'sonar-token') {
                        withCredentials([string(credentialsId: 'jasypt-secret', variable: 'JASYPT'), 
                                        string(credentialsId: 'sonar-token', variable: 'SONAR_TOKEN')]) {
                                            sh 'mvn clean package sonar:sonar \
                                                -Dsonar.host.url=https://sonarsolaris.ddns.net \
                                                -Dsonar.login=${SONAR_TOKEN} \
                                                -Dspring.profiles.active=ci \
                                                -Dsonar.log.level=DEBUG \
                                                -Djasypt.encryptor.password=${JASYPT}'
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
                        sh 'mvn compile -Dspring.profiles.active=ci \
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
            when {
                branch 'master'
            }
            steps {
                withMaven (maven: 'M3') {
                    withCredentials([string(credentialsId: 'jasypt-secret', variable: 'JASYPT')]) {
                        sh 'mvn package -Dspring.profiles.active=ci -Djasypt.encryptor.password=${JASYPT}'
                    }
                }
            }      
        }
        stage('Deploy') {
            when {
                branch 'master'
            }
            steps {
                echo '----- Deploy app -----'
                script {
                    deploy adapters: [tomcat9(credentialsId: 'tomcat-deploy-user', path: '', url: 'https://deploysolaris.ddns.net')], contextPath: '/', onFailure: false, war: '**/*.war'
                }
            }
        }
    }
    //
}