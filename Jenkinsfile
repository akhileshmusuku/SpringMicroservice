pipeline {
    agent any
    triggers {
        pollSCM('* * * * *')
    }
    stages {
        stage('Checkout') {
            steps {
                git branch: 'master', url: 'ssh://git@bitbucket.org:company/repo.git'
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
