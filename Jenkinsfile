pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                // Get the code from Github
                git 'https://github.com/murphj42/joespetitions.git'
            }
        }

        stage('Build') {
            steps {
                echo "starting build..."
                sh 'mvn clean:clean'
                sh 'mvn dependency:copy-dependencies'
                sh 'mvn compiler:compile'
            }
        }

        stage('Run Tests') {
            steps {
                // Run test cases
                sh 'mvn test'
            }
        }

        stage('Package') {
            steps {
                // Package as a War file
                sh 'mvn package'
            }
        }

        stage('Archive War') {
            steps {
                // Archive the War file
                archiveArtifacts 'target/joespetitions.war'
            }
        }
    }
}