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
             // Utilisez votre nom d'utilisateur Docker Hub ici
             def dockerImageName = 'eyaay/achat-devops'
             def dockerImageTag = 'latest'

             // Construction de l'image Docker
             sh "docker build -t ${dockerImageName}:${dockerImageTag} ."
           }
         }
       }

       stage('Push Docker Image') {

         steps {
          script {
                // Utilise les identifiants stockés pour se connecter au Docker Hub
                docker.withRegistry('https://registry.hub.docker.com', 'docker-hub-credentials')
 {
                  def dockerImageName = 'eyaay/achat-devops'
                  def dockerImageTag = 'latest'
                  // Pousser l'image Docker
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
