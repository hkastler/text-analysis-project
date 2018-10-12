#!/bin/sh
mvn clean install -f ../text-analysis/pom.xml
mvn clean install -f ../text-analysis-twitter/pom.xml
mvn clean install && docker build -t com.hkstlr/text-analysis-service .
OPT_PATH=c:/etc/opt/text-analysis-project
WEBCLIENT_PATH=c:/Users/henry.kastler/Documents/GitHub/text-analysis-project/text-analysis-webclient
docker rm -f text-analysis-service || true && docker run -v $OPT_PATH:/etc/opt/text-analysis-project -v $WEBCLIENT_PATH:/app/default -p 8080:8080 -p 8443:8443 --name text-analysis-service com.hkstlr/text-analysis-service 
