pipeline {
    agent any

    tools {
        jdk 'jdk21'      // nom exact de ton JDK 21 configuré dans Jenkins
        maven 'maven3'   // nom exact de ton Maven configuré dans Jenkins
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean install'
            }
        }

        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }

        stage('Package') {
            steps {
                sh 'mvn package'
            }
        }
    }
}

