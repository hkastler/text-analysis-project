#payara
FROM  payara/server-full

#Dockerfile in the root to access target dirs
COPY text-analysis-service/target/text-analysis-service.war $DEPLOY_DIR
COPY text-analysis-webapp/target/text-analysis-webapp.war $DEPLOY_DIR
