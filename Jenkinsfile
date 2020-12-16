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

                script {
                    pom = readMavenPom file: 'pom.xml'
                    pom_version_num = pom.version
                }
            }
        }
        stage('buildImage') {
            steps {
                script {
                    jar_dir_final = packaged_jar_dir+'/target/'
                    jar_file_final = jar_dir_final+'meals-calendar-backend.jar'
                }

                sh "cp ${jar_file_final} ."

                sh "docker build -t repo.treescale.com/tgcoding/meals-calendar-backend:${pom_version_num}-${env.GIT_COMMIT} -t repo.treescale.com/tgcoding/meals-calendar-backend:latest ."
            }
        }
        stage('pushImage') {
            steps {
                sh "echo $TREESCALE_PASSWORD | docker login -u tgcoding --password-stdin repo.treescale.com"
                sh "docker push repo.treescale.com/tgcoding/meals-calendar-backend:${pom_version_num}-${env.GIT_COMMIT}"
                sh "docker push repo.treescale.com/tgcoding/meals-calendar-backend:latest"
            }
        }
    }
}