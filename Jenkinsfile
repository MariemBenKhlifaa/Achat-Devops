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

        stage('Junit') {
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
                    // Use the Jenkins credentials binding plugin to inject the API key
                    withCredentials([string(credentialsId: 'grafana-key', variable: 'GRAFANA_API_KEY')]) {
                        // Use the API key in the curl command
                        sh '''
                        curl -X POST -H "Content-Type: application/json" \
                             -H "Authorization: Bearer $GRAFANA_API_KEY" \
                             -d @your_json_file_here \
                             "http://192.168.1.26:3000/api/dashboards/db"
                        '''
                    }
                }
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
        }
        failure {
            echo 'The process failed.'
        }
    }
}
