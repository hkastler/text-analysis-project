FROM openjdk:jre-alpine

ADD target/text-analysis-service-thorntail.jar /opt/thorntail.jar
ENV JAVA_OPTS="$JAVA_OPTS -Djava.net.preferIPv4Stack=true -Djava.net.preferIPv4Addresses"

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/opt/thorntail.jar"]