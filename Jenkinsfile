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

  stage('Deploy Spring Boot Application') {
    // TODO: Add deployment step
  }

}