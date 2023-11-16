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

                                                        stage('Docker compose') {
                                                                 steps {
                                                                     script {
                                                                         sh 'docker compose up -d'
                                                                     }
                                                                 }
                                                              }
                                                              stage('prometheus grafana') {
                                                                          steps {
                                                                              echo 'prometheus grafana '
                                                                          }
                                                                      }

                                                                       stage('Mailing') {
                                                                                  steps {
                                                                                      echo "OK"
                                                                                  }
                                                                             }
  }

   post {
        success {
            echo 'successfully.'
              emailext body: 'Votre build a réussi. Veuillez consulter Jenkins pour les détails.',
                                  subject: 'Jenkins Build Successful',
                                  to: 'elaa.boulifi@esprit.tn'

        }
        failure {
            echo 'Failed'
              emailext body: "Votre build a échoué. Détails de l'erreur :\n${currentBuild.rawBuild.getLog(100)}",
                                  subject: 'Jenkins Build Failed',
                                  to: 'elaa.boulifi@esprit.tn'
        }

    }
}