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


                                     stage('Docker push image to Docker Hub') {
                                                 steps {
                                                     echo 'Pushing Docker image to Docker Hub'
                                                     sh "docker login -u ellaboulifi -p dockerdocker"
                                                     sh 'docker push ellaboulifi/achat:latest'
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