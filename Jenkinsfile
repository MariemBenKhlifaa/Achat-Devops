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
                    sh 'docker compose up -d'
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
         stage('Grafana Import Dashboard') {
            steps {
                script {
                    // Add steps to import the Jenkins dashboard in Grafana
                    // Use the Grafana API to import the dashboard with ID 9964
                    sh 'curl -X POST -u admin:grafana -H "Content-Type: application/json" -d \'{"dashboard": {"id": 9964},"inputs": [{"name": "DS_PROMETHEUS","type": "datasource","pluginId": "prometheus","value": "Prometheus"}]}}\' http://192.168.1.82:3000/api/dashboards/import'
                }
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
        }
        failure {
            echo 'Failed'
        }
    }
}