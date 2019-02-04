FROM openliberty/open-liberty:javaee8

#Dockerfile in the root to access target dirs
COPY text-analysis-service/target/text-analysis-service.war /opt/ol/wlp/usr/servers/defaultServer/dropins
COPY text-analysis-webapp/target/text-analysis-webapp.war /opt/ol/wlp/usr/servers/defaultServer/dropins
