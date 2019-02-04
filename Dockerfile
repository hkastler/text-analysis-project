FROM tomee:latest

#Dockerfile in the root to access target dirs
COPY ./text-analysis-service/target/text-analysis-service.war /usr/local/tomee/webapps/
COPY ./text-analysis-webapp/target/text-analysis-webapp.war /usr/local/tomee/webapps/
COPY ./app-server/tomee/logging.properties /usr/local/tomee/conf/.
