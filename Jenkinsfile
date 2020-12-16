def packaged_jar_dir
def jar_dir_final
def jar_file_final
def pom_version_num

pipeline {

    agent {
        label 'master'
    }

    environment {
        TREESCALE_PASSWORD = credentials('treescale-password')
    }

    stages {
        stage('packageJar') {
            agent {
                docker {
                    image 'maven:3-adoptopenjdk-14'
                }
            }
            steps {
                sh 'mvn package -Dmaven.test.skip=true'
                script {
                    packaged_jar_dir = "${env.WORKSPACE}"
                }
                sh 'echo package: packaged_jar_dir'
                echo "$packaged_jar_dir"

                script {
                    pom = readMavenPom file: 'pom.xml'
                    pom_version_num = pom.version
                }
            }
        }
        stage('buildImage') {
            /*environment {
                JAR_FILE = ''
            }*/
            steps {
                sh 'echo push: packaged_jar_dir'
                echo "$packaged_jar_dir"
                script {
                    jar_dir_final = packaged_jar_dir+'/target/'
                    jar_file_final = jar_dir_final+'meals-calendar-backend.jar'
                }

                sh 'echo push: jar_dir_final'
                echo "$jar_dir_final"
                sh "ls -la ${jar_dir_final}"

                sh 'echo push: jar_file_final'
                echo "$jar_file_final"
                sh "ls -la $jar_file_final"

                sh "cp ${jar_file_final} ."
                sh "ls -la"

                echo 'Version:Hash'
                echo "${pom_version_num}:${env.GIT_COMMIT}"

                sh "docker build -t repo.treescale.com/tgcoding/meals-calendar-backend:${pom_version_num}-${env.GIT_COMMIT} -t repo.treescale.com/tgcoding/meals-calendar-backend:latest ."
            }
        }
        stage('pushImage') {
            steps {
                sh "echo $TREESCALE_PASSWORD | docker login -u tgcoding --password-stdin repo.treescale.com"
                sh "docker push repo.treescale.com/tgcoding/meals-calendar-backend:${pom_version_num}-${env.GIT_COMMIT}"
                sh "docker push repo.treescale.com/tgcoding/meals-calendar-backend:latest"

                sh 'docker ps'
            }
        }
    }
}