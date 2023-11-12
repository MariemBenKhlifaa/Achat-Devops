pipeline {
  agent any
  stages {
        stage('Get source Code') {
              steps {
                 script {
                  checkout([$class: 'GitSCM', branches: [[name: 'mariem_benkhlifa_5twin3']], userRemoteConfigs: [[url: 'https://github.com/MariemBenKhlifaa/Achat-Devops.git']]])
                        }
                  }
        }
        stage('build stage') {
            steps{
                script {
                    sh 'mvn clean install'

                }
            }
        }
        stage('test stage') {
            steps {
                script {
                    sh 'mvn test'
                }
            }
        }
        stage('sonarqube') {
            steps {
                withSonarQubeEnv(installationName: 'sonarQube'){
                    sh 'mvn org.sonarsource.scanner.maven:sonar-maven-plugin:3.9.0.2155:sonar -Dsonar.java.binaries=target/classes'
                }
            }
        }
        stage('nexus') {
            steps{
                sh 'mvn deploy'
            }

        }
        

  }
   post {
        success {
            echo 'successfully.'
        }
        failure {
            echo 'Failed'
        }
    }
}
