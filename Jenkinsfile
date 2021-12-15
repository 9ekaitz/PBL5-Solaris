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
                                                -Dsonar.projectKey=solaris \
                                                -Dsonar.host.url=https://sonarsolaris.ddns.net \
                                                -Dsonar.login=${SONAR_TOKEN} \
                                                -Dspring.profiles.active=ci \
                                                -Djasypt.encryptor.password=${JASYPT}'
                        } //pakcage al principio era verify
                        
                    }
                    // withSonarQubeEnv('SonarQube') {
                    //     sh "./gradlew sonarqube"
                    // }
                }
            }
        }
        stage('QualityGate') {
            steps {
                timeout(time: 1, unit: 'HOURS') {
                    def qg = waitForQualityGate()
                    if (qg.status != 'OK') {
                        error "Pipeline aborted due to quality gate failure: ${qg.status}"
                    }
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
        stage('Testing') {
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
        stage('Deploy') {
            steps {
                echo '----- Deploy app -----'
            }
        }
    }
}