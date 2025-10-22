pipeline {
    agent any

    // Définir les outils à utiliser
    tools {
        jdk 'jdk21'      // <- nom exact de ton JDK 21 dans Jenkins
        maven 'Maven3'   // <- nom exact de ton Maven configuré
    }

    stages {
        stage('Checkout') {
            steps {
                // Récupération du code depuis Git
                checkout scm
            }
        }

        stage('Build') {
            steps {
                // Vérification du JDK utilisé
                sh 'java -version'
                sh 'mvn clean install'
            }
        }

        stage('Test') {
            steps {
                // Exécution des tests Maven si tu en as
                sh 'mvn test'
            }
        }

        stage('Package') {
            steps {
                // Packaging final
                sh 'mvn package'
            }
        }
    }

    post {
        always {
            // Nettoyage ou messages après le pipeline
            echo 'Pipeline terminé'
        }
    }
}
