FROM jboss/wildfly:latest
COPY text-analysis-service/target/text-analysis-service.war /opt/jboss/wildfly/standalone/deployments/
COPY text-analysis-webapp/target/text-analysis-webapp.war /opt/jboss/wildfly/standalone/deployments/
RUN rm -f /opt/jboss/wildfly/welcome-content/*.*
COPY text-analysis-webapp/src/main/webapp /opt/jboss/wildfly/welcome-content/
ADD standalone-custom.xml /opt/jboss/wildfly/standalone/configuration/
RUN /opt/jboss/wildfly/bin/add-user.sh admin admin0099 --silent
CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-c", "standalone-custom.xml", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0"]