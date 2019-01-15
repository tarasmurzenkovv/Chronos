 pipeline {
 agent any
    stages {
        stage('Checkout from Azure Git') {
            steps {
                checkout([$class: 'GitSCM', branches: [[name: '*/develop']],
                        userRemoteConfigs: [[credentialsId: 'azure_git_credentials_id', url: 'SyngentaProjects-EAME01@vs-ssh.visualstudio.com:v3/SyngentaProjects-EAME01/Chronos/Chronos']]])
            }
        }
        stage('Build Application') {
            steps {
                bat 'gradlew build'
            }
        }
         stage('Apply DB Changes') {
            steps {
                bat 'gradlew applyDbChanges -Puser_name=syngenta -Ppassword= update'
            }
        }
        stage('Deploy') {
            steps {
                bat 'gradlew deploy -Phost=139.59.210.123 -Phost.user=syngenta -Phost.password= -Phost.id_rsa.file.path=./id_rsa'
            }
        }
    }
}
