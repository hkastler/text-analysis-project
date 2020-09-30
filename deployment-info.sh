#!/bin/sh
HOST=www.hkstlr.com
ADMIN="$1"
ADMIN_AUTH="$2"
WILDFLY_MANAGEMENT_URL=http://${ADMIN}:${ADMIN_AUTH}@${HOST}:9990
if [ "$PROJECTS" == "" ]; then
PROJECTS=text-analysis-service
fi
IFS=","
for PROJECT in $PROJECTS
do
PROJECT_HOME=$LOCAL_HOME/$PROJECT
WAR_NAME=${PROJECT}.war
echo "-> Deployment Info '$WAR_NAME' at '$WILDFLY_MANAGEMENT_URL'"
result=`curl -s --digest ${WILDFLY_MANAGEMENT_URL}/management --header "Content-Type: application/json" -d '{"operation":"read-attribute","name":"status","address":[{"deployment":"'"${WAR_NAME}"'"}], "json.pretty":0}' | perl -pe 's/^.*"outcome"\s*:\s*"(.*)".*$/$1/' `
echo $result 
done