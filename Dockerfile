# Use local build wildfly 15
# see docker-build.* in wildfly directory
FROM wildfly.15.0.0.final:latest

#Dockerfile in the root to access target dirs
COPY text-analysis-service/target/text-analysis-service.war /opt/jboss/wildfly/standalone/deployments/
COPY text-analysis-webapp/target/text-analysis-webapp.war /opt/jboss/wildfly/standalone/deployments/
RUN rm -f /opt/jboss/wildfly/welcome-content/*.*
COPY text-analysis-webapp/src/main/webapp/index.html /opt/jboss/wildfly/welcome-content/index.html
ADD standalone-custom.xml /opt/jboss/wildfly/standalone/configuration/
RUN /opt/jboss/wildfly/bin/add-user.sh admin admin0099 --silent

ENV JAEGER_REPORTER_LOG_SPANS=true
ENV JAEGER_SAMPLER_TYPE=const
ENV JAEGER_SAMPLER_PARAM=1
ENV JAEGER_AGENT_HOST=jaeger
ENV JAEGER_AGENT_PORT=6831
CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-c", "standalone-custom.xml", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0"]