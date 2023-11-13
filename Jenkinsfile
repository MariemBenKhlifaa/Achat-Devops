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