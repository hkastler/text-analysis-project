
call mvn clean install -f ../text-analysis/pom.xml
call mvn clean install -f ../text-analysis-twitter/pom.xml
call mvn clean install -f ../text-analysis-webclient/pom.xml
call mvn clean install
call docker build -t com.hkstlr/text-analysis-service .
SET OPT_PATH=c:/etc/opt/text-analysis-project
SET WEBCLIENT_PATH=c:/Users/henry.kastler/Documents/GitHub/text-analysis-project/text-analysis-webclient
call docker rm -f text-analysis-service & docker run -v %OPT_PATH%:/etc/opt/text-analysis-project -v %WEBCLIENT_PATH%:/app/default -p 8080:8080 -p 8443:8443 -p 9990:9990 --name text-analysis-service com.hkstlr/text-analysis-service 
