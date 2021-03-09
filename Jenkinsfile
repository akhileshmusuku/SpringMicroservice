pipeline {
    agent any
    triggers {
        pollSCM('* * * * *')
    }
    stages {
        stage('Checkout') {
            steps {
                git branch: 'master', url: 'https://github.com/akhileshmusuku/SpringMicroservice.git'
            }
        }
        stage ('Build') {
            steps {
                sh './gradlew assemble'
            }
        }
         stage ('Test') {
            steps {
                sh './gradlew test'
            }
        }
          
    }
}
