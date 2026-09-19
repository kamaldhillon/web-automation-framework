pipeline {
    agent any
    tools { jdk 'jdk17'; maven 'maven3' }
    stages {
        stage('Checkout') { steps { checkout scm } }
        stage('Regression') {
            steps { sh 'mvn -B clean test -Dheadless=true' }
        }
    }
    post {
        always {
            junit allowEmptyResults: false, testResults: 'target/surefire-reports/TEST-*.xml'
            archiveArtifacts allowEmptyArchive: true, artifacts: 'target/extent-report/**,target/screenshots/**'
        }
    }
}

