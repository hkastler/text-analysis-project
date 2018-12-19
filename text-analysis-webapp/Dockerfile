FROM openjdk:jre-alpine

ADD target/text-analysis-webapp-thorntail.jar /opt/thorntail.jar

ENTRYPOINT ["java", "-jar", "/opt/thorntail.jar"]