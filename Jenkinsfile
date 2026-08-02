pipeline {
    agent any

    tools {
     
        maven 'maven3'
    }

    environment {
        TOMCAT_IP = "3.7.71.43"
        APP_NAME = "ecommerce-app"
    }

    stages {

        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/parimallpradhan/ecommerce-app.git'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean package'
            }
        }

        stage('Deploy to Tomcat') {
            steps {
                sshagent(['tomcat-ssh-key']) {
                    sh """
                    #scp target/${APP_NAME}.war ubuntu@${TOMCAT_IP}:/home/ubuntu/apache-tomcat-9.0.120/webapps/
           scp -o StrictHostKeyChecking=no target/${APP_NAME}.war ubuntu@${TOMCAT_IP}:/home/ubuntu/apache-tomcat-9.0.120/webapps/
                    ssh ubuntu@${TOMCAT_IP} '
                    cd /home/ubuntu/apache-tomcat-9.0.120/bin &&
                    ./shutdown.sh || true &&
                    ./startup.sh
                    '
                    """
                }
            }
        }
    }
}
