pipeline {
    agent any
    environment {
        ANSIBLE_HOSTS = 'ansible/hosts'
    }
    stages {
        stage('查看环境变量') {
            steps {
                    sh 'printenv'  // 打印所有环境变量
            }
        }
        stage('拉取代码') {
            steps {
                git branch: '${GIT_BRANCH}', url: 'https://github.com/Yanderes7/DFSY.git'
            }
        }
        stage('构建项目') {
            steps {
                sh 'mvn clean package'
                stash name: 'jar', includes: 'target/*.jar'
            }
        }

        stage('部署到生产环境') {
            steps {
                unstash 'jar'
                ansiblePlaybook (
                    playbook: 'ansible/deploy.yml',
                    inventory: 'ansible/hosts',
                    extraVars: [
                        target_env: 'production'
//                         git_repo: 'https://github.com/Yanderes7/DFSY.git',
//                         git_branch: 'master'
                    ]
                )
            }
        }
    }
}
