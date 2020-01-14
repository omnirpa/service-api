#!groovy

node {

    load "$JENKINS_HOME/jobvars.env"

    stage('Checkout') {
        checkout scm
    }
//    stage('Test') {
//        sh './gradlew clean test --full-stacktrace'
//    }
//    stage('Build') {
//        sh './gradlew build'
//    }
//    stage('Docker image') {
//        sh "./gradlew buildDocker -P dockerServerUrl=$DOCKER_HOST"
//    }

    stage('Build') {
        docker.withServer("$DOCKER_HOST") {
            stage('Build Docker Image') {
                sh 'docker build -f docker/Dockerfile-develop -t reportportal-dev-5-1/service-api .'
            }

            stage('Deploy container') {
                sh "docker-compose -p reportportal51 -f $COMPOSE_FILE_RP_5_1 up -d --force-recreate api"
            }
        }
    }
}