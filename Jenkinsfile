pipeline {
    agent any
    triggers {
        pollSCM('* * * * *')
    }
    stages {
        stage ('Build') {
            steps {
                sh './gradlew clean build'
            }
        }
         stage ('Test') {
            steps {
                sh './gradlew test'
            }
        }
          
    }
    
    post {
        always {
            junit allowEmptyResults: true, testResults: '**/test-results/*.xml'
        }
    }
    
}
