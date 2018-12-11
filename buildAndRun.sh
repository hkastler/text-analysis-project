#!/bin/sh
mvn clean install -f ./pom.xml -P default
docker-compose up --build
