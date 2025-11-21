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
        bat "${env.MVN_CMD}"
      }
    }

   stage('Deploy to Tomcat') {
  steps {
    bat """
      echo Stopping Tomcat...
      "${env.TOMCAT_HOME}\\bin\\shutdown.bat"

      echo Waiting for Tomcat to stop...
      timeout /t 5

      echo Removing old WAR and folder...
      del /F /Q "${env.TOMCAT_HOME}\\webapps\\tom.war"
      rmdir /S /Q "${env.TOMCAT_HOME}\\webapps\\tom" 2>nul

      echo Copying new WAR...
      copy target\\Tom-0.0.1-SNAPSHOT.war "${env.TOMCAT_HOME}\\webapps\\tom.war"

      echo Starting Tomcat...
      "${env.TOMCAT_HOME}\\bin\\startup.bat"
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
