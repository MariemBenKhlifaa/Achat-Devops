pipeline {
  agent any
  stages {
        stage('Get source Code') {
              steps {
                 script {
                  checkout([$class: 'GitSCM', branches: [[name: 'elaa_boulifi_5twin3']], userRemoteConfigs: [[url: 'https://github.com/MariemBenKhlifaa/Achat-Devops.git']]])
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
  stage('SonarQube Analysis') {
             steps {
                 echo 'Analyzing code with SonarQube...'
                 sh 'mvn sonar:sonar -Dsonar.login=admin -Dsonar.password=sonar'
             }
         }
         stage('Deploy to Nexus') {
                     steps{
                         sh 'mvn deploy'
                     }

                 }

                             stage('Docker build ') {
                                 steps {
                                     script {
                                         // Build the Docker image
                                         sh 'docker build -t ellaboulifi/achat:latest .'
                                     }
                                 }
                             }
                                  stage('Docker push') {
                                         steps {
                                             script {
                                                 withCredentials([string(credentialsId: '60e2873a-55b8-4f1f-a6b3-969109211a98', variable: 'DOCKERHUB_PASSWORD')]) {
                                                 // Push the Docker image to Docker Hub
                                                 sh 'docker push ellaboulifi/achat:latest'
                                                 }
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