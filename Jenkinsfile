pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                checkout([$class: 'GitSCM', branches: [[name: '*/master']], userRemoteConfigs: [[url: 'https://github.com/ioanaii/Licenta_1/']]])
            }
        }

        stage('Build and Test') {
            steps {
                sh 'mvn clean install test -Dtestngfile="TestNG3.xml" -Dmaven.test.failure.ignore=true'
            }
        }
    }

    post {
        always {
            junit 'testng-results.xml'
        }
    }
}

