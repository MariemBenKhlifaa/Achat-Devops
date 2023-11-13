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
         stage('Grafana Monitoring') {
             steps {
                 script {
                     def grafanaUrl = "http://192.168.1.82:3000"
                     def dashboardPath = "/d/haryan-jenkins/jenkins3a-performance-and-health-overview"
                     def orgId = 1
                     def panelId = 15

                     // Construct the Grafana dashboard URL
                     def grafanaDashboardUrl = "${grafanaUrl}${dashboardPath}?orgId=${orgId}"

                     // Use the dashboard URL to construct the API URL
                     def grafanaApiUrl = "${grafanaDashboardUrl}/api/annotations?panelId=${panelId}"

                     echo "Constructing URL: ${grafanaApiUrl}"

                     def response = httpRequest(httpMode: 'GET', url: grafanaApiUrl)

                     echo "Request URL: ${response.request.url}"
                     echo "Request Headers: ${response.request.headers}"
                     echo "Request Body: ${response.request.body}"
                     echo "Response Code: ${response.getStatus()}"
                     echo "Response Content: ${response.getContent()}"
                     echo "Response Headers: ${response.getHeaders()}"

                     def annotations = readJSON text: response.getContent()
                     echo "Grafana Annotations: ${annotations}"
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