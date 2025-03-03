pipeline {
    agent any
    environment {
        ANSIBLE_HOSTS = 'ansible/hosts'
        SH_PRIVATE_KEY = '/var/jenkins_home/.ssh/id_rsa'
    }
    parameters {
        string(name: 'GIT_BRANCH', defaultValue: 'master', description: 'Git branch to build')
    }
//     tools {
//         jdk 'jdk8'  // 声明使用的 JDK
//         maven 'Maven'  // 声明使用的 Maven
//     }
    stages {
        stage('查看环境变量') {
            steps {
                    sh 'printenv'  // 打印所有环境变量
            }
        }
        stage('拉取代码') {
            steps {
                git branch: params.GIT_BRANCH, url: 'https://github.com/Yanderes7/DFSY.git'
            }
        }
        stage('构建项目') {
            steps {
                sh 'mvn clean package'
                stash name: 'jar', includes: 'target/*.jar'
            }
        }

//         stage('Deploy') {
//             steps {
//                 sh '''
//                     ansible-playbook ansible/deploy.yml --private-key /var/jenkins_home/.ssh/id_rsa -u root -e "key1=value1 key2=value2"
//                 '''
//             }
//         }


        stage('部署到生产环境') {
            steps {
                sh '''
                    # 使用外部私钥文件运行 Ansible
                    ansible-playbook ansible/deploy.yml -i ansible/hosts --private-key $SSH_PRIVATE_KEY -u root -e
                '''
                unstash 'jar'
                ansiblePlaybook (
                    playbook: 'ansible/deploy.yml',
                    inventory: 'ansible/hosts',
//                     credentialsId: '4421c334-c7a8-4baa-83ad-6da212a71da9',
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
