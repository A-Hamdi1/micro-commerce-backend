pipeline {
    agent any
    
    environment {
        DOCKER_REGISTRY = 'your-docker-registry'
        JAVA_HOME = tool 'JDK17'
        NODE_HOME = tool 'Node16'
    }
    
    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        
        stage('Build Backend') {
            steps {
                sh '''
                    chmod +x build-all.ps1
                    ./build-all.ps1
                '''
            }
        }
        
        stage('Build Frontend') {
            steps {
                dir('Mini_Project') {
                    sh '''
                        npm install
                        npm run build
                    '''
                }
            }
        }
        
        stage('Unit Tests') {
            parallel {
                stage('Backend Tests') {
                    steps {
                        sh './mvnw test'
                    }
                }
                stage('Frontend Tests') {
                    steps {
                        dir('Mini_Project') {
                            sh 'npm test'
                        }
                    }
                }
            }
        }
        
        stage('Build Docker Images') {
            steps {
                script {
                    def services = [
                        'config-service',
                        'eureka-discoveryservice',
                        'gatewayservice',
                        'authentificationservice',
                        'client-service',
                        'produit-service',
                        'factureservice',
                        'reglement-service'
                    ]
                    
                    services.each { service ->
                        docker.build("${DOCKER_REGISTRY}/${service}:${BUILD_NUMBER}", "./${service}")
                    }
                    
                    // Build frontend image
                    docker.build("${DOCKER_REGISTRY}/frontend:${BUILD_NUMBER}", "./Mini_Project")
                }
            }
        }
        
        stage('Push Docker Images') {
            steps {
                script {
                    withCredentials([usernamePassword(credentialsId: 'docker-registry-credentials', 
                                                    passwordVariable: 'DOCKER_PASSWORD', 
                                                    usernameVariable: 'DOCKER_USERNAME')]) {
                        sh '''
                            docker login ${DOCKER_REGISTRY} -u ${DOCKER_USERNAME} -p ${DOCKER_PASSWORD}
                            
                            # Push all service images
                            docker push ${DOCKER_REGISTRY}/config-service:${BUILD_NUMBER}
                            docker push ${DOCKER_REGISTRY}/eureka-discoveryservice:${BUILD_NUMBER}
                            docker push ${DOCKER_REGISTRY}/gatewayservice:${BUILD_NUMBER}
                            docker push ${DOCKER_REGISTRY}/authentificationservice:${BUILD_NUMBER}
                            docker push ${DOCKER_REGISTRY}/client-service:${BUILD_NUMBER}
                            docker push ${DOCKER_REGISTRY}/produit-service:${BUILD_NUMBER}
                            docker push ${DOCKER_REGISTRY}/factureservice:${BUILD_NUMBER}
                            docker push ${DOCKER_REGISTRY}/reglement-service:${BUILD_NUMBER}
                            docker push ${DOCKER_REGISTRY}/frontend:${BUILD_NUMBER}
                        '''
                    }
                }
            }
        }
        
        stage('Deploy to Staging') {
            when {
                branch 'develop'
            }
            steps {
                script {
                    withCredentials([sshUserPrivateKey(credentialsId: 'staging-server-ssh', 
                                                     keyFileVariable: 'SSH_KEY')]) {
                        sh '''
                            # Update docker-compose.yml with new image versions
                            sed -i "s|image: .*|image: ${DOCKER_REGISTRY}/config-service:${BUILD_NUMBER}|g" docker-compose.yml
                            # Repeat for other services...
                            
                            # Deploy to staging server
                            scp -i ${SSH_KEY} docker-compose.yml user@staging-server:/app/
                            ssh -i ${SSH_KEY} user@staging-server "cd /app && docker-compose pull && docker-compose up -d"
                        '''
                    }
                }
            }
        }
        
        stage('Deploy to Production') {
            when {
                branch 'main'
            }
            steps {
                script {
                    withCredentials([sshUserPrivateKey(credentialsId: 'production-server-ssh', 
                                                     keyFileVariable: 'SSH_KEY')]) {
                        sh '''
                            # Update docker-compose.yml with new image versions
                            sed -i "s|image: .*|image: ${DOCKER_REGISTRY}/config-service:${BUILD_NUMBER}|g" docker-compose.yml
                            # Repeat for other services...
                            
                            # Deploy to production server
                            scp -i ${SSH_KEY} docker-compose.yml user@production-server:/app/
                            ssh -i ${SSH_KEY} user@production-server "cd /app && docker-compose pull && docker-compose up -d"
                        '''
                    }
                }
            }
        }
    }
    
    post {
        always {
            cleanWs()
        }
        success {
            emailext (
                subject: "Pipeline Successful: ${currentBuild.fullDisplayName}",
                body: "Check console output at ${env.BUILD_URL}",
                recipientProviders: [[$class: 'DevelopersRecipientProvider']]
            )
        }
        failure {
            emailext (
                subject: "Pipeline Failed: ${currentBuild.fullDisplayName}",
                body: "Check console output at ${env.BUILD_URL}",
                recipientProviders: [[$class: 'DevelopersRecipientProvider']]
            )
        }
    }
} 