pipeline {
  agent any

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

       stage('Publish to Nexus') {
                   steps {
                       script {
                           // Vous pouvez utiliser mvn deploy pour publier dans Nexus, en assurant que
                           // le pom.xml est bien configuré pour Nexus repository
                           sh 'mvn deploy'
                       }
                   }
}
    stage('Build Docker Image') {
      steps {
        script {
          // Change this to your DockerHub username if you want to push to DockerHub
          // Or to the path of your image if you use a private registry
          def dockerImageName = 'eyaay/alpine'
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
            def dockerImageName = 'eyaay/alpine'
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
