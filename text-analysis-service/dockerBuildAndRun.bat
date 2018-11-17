call docker build -t com.hkstlr/text-analysis-service .
SET OPT_PATH=c:/etc/opt/text-analysis-project
call docker rm -f text-analysis-service & docker run -v %OPT_PATH%:/etc/opt/text-analysis-project -p 8080:8080 -p 8443:8443 -p 9990:9990 --name text-analysis-service com.hkstlr/text-analysis-service