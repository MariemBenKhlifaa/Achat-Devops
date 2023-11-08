pipeline {
  agent any
   environment {
         MVN_PATH = '/usr/share/maven'
         NEXUS_REPOSITORY = 'nexus-releases'
         NEXUS_URL = 'http://192.168.1.25:8081/repository/maven-releases/'
         NEXUS_CREDENTIALS_ID = 'aa10f176-1eb7-4b38-aefc-99a98d5b40b6'
     }
  stages {
    stage('Get source Code') {
      steps {
        script {
          checkout([$class: 'GitSCM', branches: [[name: 'eya_bouthouri_5twin3']], userRemoteConfigs: [[url: 'https://github.com/MariemBenKhlifaa/Achat-Devops.git']]])
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

    stage('sonarqube') {
      steps {
        withSonarQubeEnv('SonarQube') {
          sh 'mvn org.sonarsource.scanner.maven:sonar-maven-plugin:3.9.0.2155:sonar'
        }
      }
    }
   stage(' Nexus') {
            steps {
                script {
                    sh "${MVN_PATH} deploy -DskipTests -P release --settings .m2/settings.xml"
                }
            }
    stage('Build Docker Image') {
      steps {
        script {
          // Change this to your DockerHub username if you want to push to DockerHub
          // Or to the path of your image if you use a private registry
          def dockerImageName = 'eyaay/achat-devops'
          def dockerImageTag = 'latest'

          // Building the Docker image
          sh "docker build -t ${dockerImageName}:${dockerImageTag} ."
        }
      }
    }

    stage('Push Docker Image') {
      when {
        branch 'main' // Only push images for the 'main' branch
      }
      steps {
        script {
          // Ensure credentials are stored in Jenkins and use them
          docker.withRegistry('https://registry.hub.docker.com', 'docker-hub-credentials') {
            def dockerImageName = 'eyaay/achat-devops'
            def dockerImageTag = 'latest'

            // Pushing the Docker image
            sh "docker push ${dockerImageName}:${dockerImageTag}"
          }
        }
      }
    }
  }

  post {
    success {
      echo 'The process completed successfully.'
    }
    failure {
      echo 'The process failed.'
    }
  }
}
