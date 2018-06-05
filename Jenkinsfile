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
    sh "scp build/libs/*.war iljaskevic@loyaltyone.ljaskevic.com:/home/iljaskevic/deployments/"
    sh "export WAR=\$(find build/libs/ -type f -printf '%f\n') && ssh iljaskevic@loyaltyone.ljaskevic.com \"sudo ./deploy.sh \$WAR\""
  }

}