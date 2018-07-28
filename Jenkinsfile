pipeline {
  agent {
    docker {
      image 'maven:3.5-jdk-8'
    }
    
  }
  stages {
    stage('Test') {
      steps {
        sh 'mvn clean test -Drun.profiles=test'
        archive "target/**/*"
        junit 'target/surefire-reports/*.xml'
      }
    }
    stage('Coverage') {
      steps {
        sh 'mvn clean test jacoco:report coveralls:report -Drun.profiles=test'
        archive "target/**/*"
        junit 'target/surefire-reports/*.xml'
      }
    }
    stage('Integration Tests') {
      steps {
        sh 'mvn integration-test -Drun.profiles=test'
        archive "target/**/*"
        junit 'target/surefire-reports/*.xml'
      }
    }
    stage('Package') {
      steps {
        sh 'mvn package'
      }
    }
    stage('Create Docker Image') {
      steps {
        sh 'mvn dockerfile:build dockerfile:push'
      }
    }
    stage('Cleanup') {
      steps {
        cleanWs(cleanWhenAborted: true, cleanWhenFailure: true, cleanWhenNotBuilt: true, cleanWhenSuccess: true, cleanWhenUnstable: true, cleanupMatrixParent: true, deleteDirs: true)
      }
    }
  }
}