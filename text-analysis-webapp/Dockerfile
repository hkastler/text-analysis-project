FROM openjdk:jre-alpine

ADD target/text-analysis-webapp-thorntail.jar /opt/thorntail.jar

ENV thorntail.http.port=8080
ENV JAVA_OPTS="$JAVA_OPTS -Djava.net.preferIPv4Stack=true -Djava.net.preferIPv4Addresses"

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/opt/thorntail.jar", "-Djava.net.preferIPv4Stack=true", "-Djava.net.preferIPv4Addresses=true"]