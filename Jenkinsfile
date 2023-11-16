pipeline {
    agent any

    stages {
        stage('Git') {
            steps {
                script {
                    checkout([$class: 'GitSCM', branches: [[name: 'eya_bouthouri_5twin3']], userRemoteConfigs: [[url: 'https://github.com/MariemBenKhlifaa/Achat-Devops.git']]])
                }
            }
        }

        stage(' maven build ') {
            steps{
                script {
                    sh 'mvn clean install'
                }
            }
        }

        stage('test') {
            steps {
                script {
                    sh 'mvn test'
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

        stage('Nexus') {
            steps {
                script {
                    sh 'mvn deploy'
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    def dockerImageName = 'eyaay/achat-devops'
                    def dockerImageTag = 'latest'
                    sh "docker build -t ${dockerImageName}:${dockerImageTag} ."
                }
            }
        }

        stage('Push Docker Image') {
            steps {
                script {
                    withCredentials([usernamePassword(credentialsId: 'docker-hub-credentials', usernameVariable: 'DOCKERHUB_USERNAME', passwordVariable: 'DOCKERHUB_PASSWORD')]) {
                        sh 'echo $DOCKERHUB_PASSWORD | docker login -u $DOCKERHUB_USERNAME --password-stdin'
                        sh "docker push eyaay/achat-devops:latest"
                    }
                }
            }
        }

        stage('Docker Compose') {
            steps {
                script {
                    sh 'docker-compose up -d'
                }
            }
        }
           stage('Grafana') {
               steps {
                   script {
                       def grafanaUrl = 'http://192.168.1.26:3000/d/haryan-jenkins/jenkins3a-performance-and-health-overview'
                       withCredentials([usernamePassword(credentialsId: 'GrafanaCredentialsId', usernameVariable: 'GRAFANA_USERNAME', passwordVariable: 'GRAFANA_PASSWORD')]) {
                           def curlCommand = "curl -X GET -u ${GRAFANA_USERNAME}:${GRAFANA_PASSWORD} -H 'Content-Type: application/json' ${grafanaUrl}"
                           sh curlCommand
                       }
                   }
               }
           }

            stage('Mailing') {
                         steps {
                             echo "mail success"
                         }
                     }

           }



    post {
        always {
            script {
                sh 'docker-compose down'
            }
        }
        success {
            echo 'The process completed successfully.'
             emailext to: 'eya.bouthouri@esprit.tn',
                       subject: "Succès du Pipeline",
                       body: "Le pipeline a été exécuté avec succès."
        }
        failure {
            echo 'The process failed.'
            emailext to: 'eya.bouthouri@esprit.tn',
             subject: "Échec du Pipeline",
                       body: "Il y a eu un problème avec l'exécution du pipeline."


        }
    }
}
