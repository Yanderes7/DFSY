pipeline {
    agent any
    environment {
        ANSIBLE_HOSTS = 'ansible/hosts'
    }
    stages {
        stage('拉取代码') {
            steps {
                git branch: '${GIT_BRANCH}', url: 'https://github.com/your-repo/DFSY.git'
            }
        }
        stage('构建项目') {
            steps {
                sh 'mvn clean package'
            }
        }

        stage('部署到生产环境') {
            steps {
                ansiblePlaybook (
                    playbook: 'ansible/deploy.yml',
                    inventory: 'ansible/hosts',
                    extraVars: [
                        target_env: 'production',
                        git_repo: 'https://github.com/your-repo/DFSY.git',
                        git_branch: 'master'
                    ]
                )
            }
        }
    }
}
