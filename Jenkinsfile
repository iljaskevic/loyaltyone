node {

  stage('Checkout') {
    checkout scm
  }

  stage('Build') {
    sh "./gradlew clean build"
  }

  stage('Test') {
    sh "./gradlew test"
  }

  stage('SonarQube') {
    withSonarQubeEnv('SonarQube') {
      sh './gradlew --info sonarqube'
    }
  }

  stage('Deploy Spring Boot Application') {
    // TODO: Add deployment step
    sh "scp 'build/libs/*.{war}' iljaskevic@loyaltyone.ljaskevic.com:/home/iljaskevic/deployments/"
    sh "ssh iljaskevic@loyaltyone.ljaskevic.com './home/iljaskevic/deploy.sh'"
  }

}