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

        stage('Build Backend') {
            steps {
                dir('saas-multi-tenant-app') {
                    sh 'mvn clean package -DskipTests'
                }
            }
        }

        stage('Build Docker Images') {
            steps {
                sh "docker build -t ${IMAGE_BACKEND}:${IMAGE_TAG} -f docker/backend/Dockerfile ."
                sh "docker build -t ${IMAGE_FRONTEND}:${IMAGE_TAG} -f docker/frontend/Dockerfile ."
            }
        }

        stage('Push Docker Images') {
            steps {
                sh "echo ${DOCKER_HUB_CREDENTIALS_PSW} | docker login -u ${DOCKER_HUB_CREDENTIALS_USR} --password-stdin"
                sh "docker push ${IMAGE_BACKEND}:${IMAGE_TAG}"
                sh "docker push ${IMAGE_FRONTEND}:${IMAGE_TAG}"
                sh "docker tag ${IMAGE_BACKEND}:${IMAGE_TAG} ${IMAGE_BACKEND}:latest"
                sh "docker tag ${IMAGE_FRONTEND}:${IMAGE_TAG} ${IMAGE_FRONTEND}:latest"
                sh "docker push ${IMAGE_BACKEND}:latest"
                sh "docker push ${IMAGE_FRONTEND}:latest"
            }
        }

        stage('Update GitOps Repo') {
            steps {
                withCredentials([string(credentialsId: 'github-token', variable: 'GH_TOKEN')]) {
                    sh "rm -rf saas-gitops"
                    sh "git clone https://${GH_TOKEN}@github.com/rohit-1947/saas-gitops.git"
                    sh "sed -i.bak 's|image: .*saas-multi-tenant-app:.*|image: ${IMAGE_BACKEND}:${IMAGE_TAG}|g' saas-gitops/manifests/backend/deployment.yaml"
                    sh "sed -i.bak 's|image: .*saas-multi-tenant-app-ui:.*|image: ${IMAGE_FRONTEND}:${IMAGE_TAG}|g' saas-gitops/manifests/frontend/deployment.yaml"
                    sh "cd saas-gitops && git config user.email 'jenkins@ci.com' && git config user.name 'Jenkins' && git add . && git commit -m 'Update image tags' || true && git push origin main"
                }
            }
        }
    }

    post {
        success {
            echo 'Pipeline completed successfully!'
        }
        failure {
            echo 'Pipeline failed!'
        }
        always {
            sh "docker rmi ${IMAGE_BACKEND}:${IMAGE_TAG} || true"
            sh "docker rmi ${IMAGE_FRONTEND}:${IMAGE_TAG} || true"
        }
    }
}