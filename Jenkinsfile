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
                bat 'gradlew build -PprojVersion='
            }
        }
        stage('Apply DB Changes') {
            steps {
                bat 'gradlew applyDbChanges -Puser_name=syngenta -Ppassword= -PdbUrl= update'
            }
        }
        stage('Deploy') {
            steps {
                bat 'gradlew deploy -PprojVersion=prod -PserviceName=chronos_web_app_prod.service -Phost= -Phost.user= -Phost.password= -Phost.id_rsa.file.path=./id_rsa'
            }
        }
    }
}
