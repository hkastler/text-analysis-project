# Build
mvn clean package && docker build -t com.hkstlr/text-analysis-microservice .

# RUN

docker rm -f text-analysis-microservice || true && docker run -d -p 8080:8080 -p 4848:4848 --name text-analysis-microservice com.hkstlr/text-analysis-microservice 