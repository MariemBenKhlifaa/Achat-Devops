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