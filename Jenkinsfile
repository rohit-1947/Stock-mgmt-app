// Jenkinsfile (root of your project)
pipeline {
agent any

```
environment {
    DOCKER_HUB_CREDENTIALS = credentials('dockerhub-credentials')
    DOCKER_HUB_USERNAME    = 'rohit1947'

    IMAGE_BACKEND  = "${DOCKER_HUB_USERNAME}/saas-multi-tenant-app"
    IMAGE_FRONTEND = "${DOCKER_HUB_USERNAME}/saas-multi-tenant-app-ui"
    IMAGE_TAG      = "${BUILD_NUMBER}"
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

                docker push ${IMAGE_BACKEND}:${IMAGE_TAG}
                docker push ${IMAGE_FRONTEND}:${IMAGE_TAG}

                docker tag ${IMAGE_BACKEND}:${IMAGE_TAG} ${IMAGE_BACKEND}:latest
                docker tag ${IMAGE_FRONTEND}:${IMAGE_TAG} ${IMAGE_FRONTEND}:latest

                docker push ${IMAGE_BACKEND}:latest
                docker push ${IMAGE_FRONTEND}:latest
            """
        }
    }

    stage('Update GitOps Repo') {
        steps {
            echo 'Updating Kubernetes manifests...'

            withCredentials([
                string(credentialsId: 'github-token', variable: 'GH_TOKEN')
            ]) {

                sh """
                    rm -rf saas-gitops

                    git clone https://${GH_TOKEN}@github.com/rohit-1947/saas-gitops.git

                    cd saas-gitops

                    sed -i.bak 's|image: .*saas-multi-tenant-app.*|image: ${IMAGE_BACKEND}:${IMAGE_TAG}|g' manifests/backend/deployment.yaml || true

                    sed -i.bak 's|image: .*saas-multi-tenant-app-ui.*|image: ${IMAGE_FRONTEND}:${IMAGE_TAG}|g' manifests/frontend/deployment.yaml || true

                    rm -f manifests/backend/*.bak
                    rm -f manifests/frontend/*.bak

                    git config user.email "jenkins@ci.com"
                    git config user.name "Jenkins"

                    git add .

                    git commit -m "Update image tags to build ${IMAGE_TAG}" || true

                    git push origin main
                """
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
        sh """
            docker rmi ${IMAGE_BACKEND}:${IMAGE_TAG} || true
            docker rmi ${IMAGE_FRONTEND}:${IMAGE_TAG} || true
        """
    }
}
```

}
}