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
            withCredentials([usernamePassword(credentialsId: 'jira-credentials', usernameVariable: 'JIRA_USER', passwordVariable: 'JIRA_PASSWORD')]) {
                jiraUpdate([
                    jiraIssueSelector(selector: [$class: 'KeyJqlSelector', jql: 'issue = LIC-3']),
                    site: 'ii.atlassian.net',
                    credentialsId: '6de42fb3-b7da-450f-bafa-f0d1e234b73e',
                    username: "${JIRA_USER}",
                    password: "${JIRA_PASSWORD}"
                ])
            
                jiraNewIssue([
                    site: 'ioanai-licenta.atlassian.net',
                    credentialsId: '6de42fb3-b7da-450f-bafa-f0d1e234b73e',
                    username: "${JIRA_USER}",
                    password: "${JIRA_PASSWORD}",
                    fields: [
                        summary: 'Test failure issue',
                        project: [
                            key: 'LIC'
                        ],
                        issuetype: [
                            name: 'Bug'
                        ],
                        description: 'Test failed in the build.'
                    ]
                ])
            }
            
            junit 'testng-results.xml'
        }
    }
}
