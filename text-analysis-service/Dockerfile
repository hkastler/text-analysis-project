FROM openjdk:jre-alpine

ADD target/text-analysis-service-thorntail.jar /opt/thorntail.jar


ENTRYPOINT ["java", "-jar", "/opt/thorntail.jar"]