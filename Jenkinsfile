pipeline {
    agent any

    environment {
        DOCKER_HUB_CREDENTIALS = credentials('dockerhub-credentials')
        DOCKER_HUB_USERNAME    = 'rks1947'
        IMAGE_BACKEND          = "${DOCKER_HUB_USERNAME}/saas-multi-tenant-app"
        IMAGE_FRONTEND         = "${DOCKER_HUB_USERNAME}/saas-multi-tenant-app-ui"
        IMAGE_TAG              = "${BUILD_NUMBER}"
    }

    stages {

        stage('Checkout') {
            steps {
                echo 'Checking out source code...'
                checkout scm
            }
        }

        stage('Test Backend') {
            steps {
                echo 'Running backend tests...'
                dir('saas-multi-tenant-app') {
                    sh 'mvn test'
                }
            }
        }

        stage('Build Backend') {
            steps {
                echo 'Building Spring Boot application...'
                dir('saas-multi-tenant-app') {
                    sh 'mvn clean package -DskipTests'
                }
            }
        }

        stage('Build Docker Images') {
            steps {
                echo 'Building Docker images...'
                sh """
                    docker build \
                        -t ${IMAGE_BACKEND}:${IMAGE_TAG} \
                        -f docker/backend/Dockerfile .

                    docker build \
                        -t ${IMAGE_FRONTEND}:${IMAGE_TAG} \
                        -f docker/frontend/Dockerfile .
                """
            }
        }

        stage('Push Docker Images') {
            steps {
                echo 'Pushing images to Docker Hub...'
                sh """
                    echo ${DOCKER_HUB_CREDENTIALS_PSW} | \
                        docker login -u ${DOCKER_HUB_CREDENTIALS_USR} --password-stdin