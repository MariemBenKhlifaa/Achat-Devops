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
        stage('Docker build ') {
            steps {
                  script {
                        // Log in to Docker Hub
                        withCredentials([string(credentialsId: 'dockerhub-credentials', variable: 'DOCKERHUB_PASSWORD')]) {
                            sh 'docker login -u dorsafch -p $DOCKERHUB_PASSWORD'
                        }

                        // Build the Docker image with a different name or tag
                        sh 'docker build -t dorsafch/achat_dorsaf:latest .'
                  }
            }
        }
        stage('Docker push') {
            steps {
                script {
                    withCredentials([string(credentialsId: 'dockerhub-credentials', variable: 'DOCKERHUB_PASSWORD')]) {
                    // Push the Docker image to Docker Hub
                    sh 'docker push dorsafch/achat_dorsaf:latest'
                    }
                }
            }
        }
         stage('Docker compose') {
            steps {
                script {
                    try  {
                        sh 'docker-compose up -d'
                    } catch (Exception e){
                        echo "build failed"
                        emailext body: "Votre build a échoué. Détails de l'erreur :\n${currentBuild.rawBuild.getLog(100)}",
                                 subject: 'Jenkins Build Failed',
                                 to: 'dorsaf.charfeddine@esprit.tn'
                    }
                 }
            }
         }
          stage('Check Docker Containers') {
             steps {
                 script {
                     // Check the status of Docker containers
                     sh 'docker ps'
                 }
             }
         }
         stage('Test Prometheus Metrics') {
             steps {
                 // Assuming Prometheus is running on the same machine where Jenkins is running
                 sh 'curl -s http://192.168.1.82:9090/api/v1/query?query=up'
                 // sh 'curl -s http://localhost:9090/api/v1/query?query=jenkins_builds_total'
             }
         }
       stage('Grafana Import Dashboard') {
           steps {
               script {
                       withCredentials([usernamePassword(credentialsId: 'GrafanaCredentialsId', usernameVariable: 'USERNAME_GRAFANA', passwordVariable: 'PASSWORD_GRAFANA')]) {
                       def grafanaUrl = 'http://192.168.1.82:3000/d/haryan-jenkins/jenkins3a-performance-and-health-overview'
                       def curlCommand = "curl -X GET -u ${USERNAME_GRAFANA}:${PASSWORD_GRAFANA} -H 'Content-Type: application/json' ${grafanaUrl}"
                       sh curlCommand
                   }
                }
           }
       }

         stage('Mail') {
              steps {
                  echo "mail envoye"
              }
          }

  }
   post {
        always {
           // Clean up, for example, you might want to stop the containers
           script {
               sh 'docker compose down'
           }
        }
        success {
            echo 'successfully.'
             emailext body: 'Votre build a réussi. Veuillez consulter Jenkins pour les détails.',
                      subject: 'Jenkins Build Successful',
                      to: 'dorsaf.charfeddine@esprit.tn'
        }
       failure {
           // This block will be executed on a failed build
           echo 'Build failed.'
           emailext body: "Votre build a échoué. Détails de l'erreur :\n${currentBuild.rawBuild.getLog(100)}",
                      subject: 'Jenkins Build Failed',
                      to: 'dorsaf.charfeddine@esprit.tn'
       }
    }
}