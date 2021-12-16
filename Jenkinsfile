pipeline {

    agent any
    
    stages {
        stage('Static Analysis') {
            steps {
                withMaven(maven: 'M3') {
                    withSonarQubeEnv(installationName:'SonarQube', credentialsId: 'sonar-token') {
                        withCredentials([string(credentialsId: 'jasypt-secret', variable: 'JASYPT'), 
                                        string(credentialsId: 'sonar-token', variable: 'SONAR_TOKEN')]) {
                                            // sh 'mvn clean verify sonar:sonar \
                                            //     -Dsonar.projectKey=solaris \
                                            //     -Dsonar.host.url=https://sonarsolaris.ddns.net \
                                            //     -Dsonar.login=${SONAR_TOKEN} \
                                            //     -Dsonar.qualityProfile=solaris \
                                            //     -Dspring.profiles.active=ci \
                                            //     -Djasypt.encryptor.password=${JASYPT}'
                                            sh 'mvn clean verify sonar:sonar \
                                                    -Dsonar.projectKey=test \
                                                    -Dsonar.host.url=https://sonarsolaris.ddns.net \
                                                    -Dspring.profiles.active=ci \
                                                    -Dsonar.login=${SONAR_TOKEN} \
                                                    -Djasypt.encryptor.password=${JASYPT}'
                        }
                    }
                }
            }
        }
        stage('QualityGate') {
            steps {
                sh 'sleep 1'
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
                sh 'deploy adapters: [tomcat9(path: '', url: 'http://kindercloud.ddns.net:9003')], contextPath: '/', war: '**/*.war''
            }
        }
    }
}