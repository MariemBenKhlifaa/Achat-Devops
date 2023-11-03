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
