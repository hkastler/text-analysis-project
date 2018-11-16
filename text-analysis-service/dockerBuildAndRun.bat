call docker build -t com.hkstlr/text-analysis-service .
SET OPT_PATH=c:/etc/opt/text-analysis-project
rem SET WEBCLIENT_PATH=c:/Users/henry.kastler/Documents/GitHub/text-analysis-project/webclient-angular/dist/webclient-angular
SET WEBCLIENT_PATH=c:/etc/opt/text-analysis-project/default-app
call docker rm -f text-analysis-service & docker run -v %OPT_PATH%:/etc/opt/text-analysis-project -v %WEBCLIENT_PATH%:/app/default -p 8080:8080 -p 8443:8443 -p 9990:9990 --name text-analysis-service com.hkstlr/text-analysis-service