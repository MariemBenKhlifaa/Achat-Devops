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
        stage('Login') {
		steps {
                	script {
                        	 sh 'docker login --username mariem78  --password momo22377981'
                       }
                    }
	}
	stage('Build Docker Image') {
            steps {
               script {
                   sh 'docker build -t mariem78/achat-devops:latest .'
                }
            }
        }
        stage('Push to DockerHub') {
            steps {
                script {
                    sh 'docker push mariem78/achat-devops:latest'
                }
            }
        }
        stage('Docker compose') {
            steps {
                script {
                    sh 'docker-compose up -d'
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
