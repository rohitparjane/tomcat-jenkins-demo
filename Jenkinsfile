pipeline {
    agent any

    environment {
        TOMCAT_HOME = "D:\\Rohit\\pr2\\Tomcat\\apache-tomcat-10.1.48-windows-x64\\apache-tomcat-10.1.48"
        WAR_SOURCE = "target\\Tom-0.0.1-SNAPSHOT.war"
        WAR_DEPLOY = "tom.war"
        MVN_CMD = "mvn -B clean package -DskipTests"
    }

    stages {

        stage('Checkout') {
            steps {
                git branch: 'main',
                    url: 'https://github.com/rohitparjane/tomcat-jenkins-demo.git'
            }
        }

        stage('Build') {
            steps {
                bat "${MVN_CMD}"
            }
        }

        stage('Deploy to Tomcat') {
            steps {
                bat """
                    echo ------------------------------
                    echo Stopping Tomcat...
                    echo ------------------------------
                    "${TOMCAT_HOME}\\bin\\shutdown.bat"

                    echo Waiting for Tomcat to fully stop...
                    timeout /t 5 /nobreak

                    echo ------------------------------
                    echo Cleaning OLD deployment files
                    echo ------------------------------
                    del /F /Q "${TOMCAT_HOME}\\webapps\\${WAR_DEPLOY}" 2>nul
                    rmdir /S /Q "${TOMCAT_HOME}\\webapps\\tom" 2>nul

                    echo ------------------------------
                    echo Copying new WAR
                    echo ------------------------------
                    copy "${WAR_SOURCE}" "${TOMCAT_HOME}\\webapps\\${WAR_DEPLOY}"

                    echo ------------------------------
                    echo Starting Tomcat...
                    echo ------------------------------
                    "${TOMCAT_HOME}\\bin\\startup.bat"

                    echo Deployment Done.
                """
            }
        }
    }

    post {
        success {
            echo "🎉 Deployment completed successfully!"
        }
        failure {
            echo "❌ Build or Deployment failed!"
        }
    }
}
