pipeline {
  agent any
  stages {
        stage('Get source Code') {
              steps {
                 script {
                  checkout([$class: 'GitSCM', branches: [[name: 'dorsaf_charfeddine_5twin3']], userRemoteConfigs: [[url: 'https://github.com/MariemBenKhlifaa/Achat-Devops.git']]])
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
                    sh 'mvn test -Dtest=StockTest'
                }
            }
        }
       stage('SonarQube') {
                   steps {
                       withSonarQubeEnv('SonarQube') {
                           sh 'mvn org.sonarsource.scanner.maven:sonar-maven-plugin:3.9.0.2155:sonar'
                       }
                   }
       }
        stage('nexus') {
            steps{
                sh 'mvn deploy'
            }
        }
        stages {
            stage('Docker build ') {
                steps {
                    script {
                        // Build the Docker image
                        sh 'docker build -t dorsafch/achat:latest .'
                    }
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