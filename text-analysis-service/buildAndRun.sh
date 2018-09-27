#!/bin/sh
mvn clean package && docker build -t com.hkstlr/text-analysis-service .
docker rm -f text-analysis-service || true && docker run -v c:/etc/config:/etc/config -p 8080:8080 -p 4848:4848 --name text-analysis-service com.hkstlr/text-analysis-service 
