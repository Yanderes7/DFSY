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


        stage('停老服务') {
            steps {
                script {
                    // 停止旧服务
                    sh 'pkill -f "java -jar /opt/vehicle-management-system/synu_xh-0.0.1-SNAPSHOT.jar" || true'
                }
            }
        }


       stage('检查老服务是否停止') {
                   steps {
                       script {
                           // 检查旧服务是否已停止
                           def serviceStatus = sh(script: 'pgrep -f "java -jar /opt/vehicle-management-system/synu_xh-0.0.1-SNAPSHOT.jar"', returnStatus: true)
                           if (serviceStatus == 0) {
                               error("旧服务未成功停止，请手动处理")
                           }
                       }
                   }
               }



        stage('部署到生产环境') {
            steps {
                sh '''
                    # 使用外部私钥文件运行 Ansible
                    ansible-playbook ansible/deploy.yml -i ansible/hosts --private-key /var/jenkins_home/.ssh/id_rsa -u root -e "target_env=production" -vvv
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


       stage('检查服务是否启动') {
                   steps {
                       script {
                           // 检查新服务是否成功启动
                           sleep(time: 8, unit: "SECONDS")
                           def serviceStatus = sh(script: 'ps aux | grep "java -jar /opt/vehicle-management-system/synu_xh-0.0.1-SNAPSHOT.jar"| grep -v grep', returnStatus: true)
                           if (serviceStatus != 0) {
                               error("新服务未成功启动，请检查日志：/opt/vehicle-management-system/service.log")
                           }
                           echo "新服务已成功启动"
                       }
                   }
               }




    }


    post {
        failure {
            // 构建失败时发送通知
            echo "构建失败，请检查日志"
            // 可以添加 Slack、Email 或其他通知方式
        }
    }

}
