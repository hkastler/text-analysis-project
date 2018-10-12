FROM jboss/wildfly:latest
COPY ./target/text-analysis-service.war /opt/jboss/wildfly/standalone/deployments/
ADD standalone-custom.xml /opt/jboss/wildfly/standalone/configuration/
CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-c", "standalone-custom.xml", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0"]