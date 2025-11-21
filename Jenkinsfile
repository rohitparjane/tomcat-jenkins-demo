pipeline {
  agent any

  environment {
    TOMCAT_HOME = "D:\\Rohit\\pr2\\Tomcat\\apache-tomcat-10.1.48-windows-x64\\apache-tomcat-10.1.48"
           // change to your tomcat path
    WAR_NAME = "tom.war"              // change if different
    MVN_CMD = "mvn -B clean package -DskipTests"
  }

  stages {
    stage('Checkout') {
      steps {
        checkout(scm: [$class: 'GitSCM',
          branches: [[name: 'refs/heads/main']],
          userRemoteConfigs: [[url: 'https://github.com/rohitparjane/tomcat-jenkins-demo.git']]
        ])
      }
    }

    stage('Build') {
      steps {
        sh "${env.MVN_CMD}"
      }
    }

    stage('Deploy to Tomcat') {
      steps {
        // If Jenkins and Tomcat are on the same machine and Jenkins user has permissions:
        sh """
          set -e
          echo 'Stopping Tomcat...'
          ${env.TOMCAT_HOME}/bin/shutdown.sh || true
          sleep 2
          rm -rf ${env.TOMCAT_HOME}/webapps/tom* || true
          cp target/*.war ${env.TOMCAT_HOME}/webapps/${env.WAR_NAME}
          echo 'Starting Tomcat...'
          ${env.TOMCAT_HOME}/bin/startup.sh
        """
      }
    }
  }

  post {
    success {
      echo "Deployed to Tomcat successfully"
    }
    failure {
      echo "Build or deploy failed"
    }
  }
}
