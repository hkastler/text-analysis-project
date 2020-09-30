#!/bin/sh
HOST=www.hkstlr.com
ADMIN="$1"
ADMIN_AUTH="$2"
WILDFLY_MANAGEMENT_URL=http://${ADMIN}:${ADMIN_AUTH}@${HOST}:9990
PROJECTS="$3"
if [ "$PROJECTS" == "" ]; then
PROJECTS=text-analysis-service
fi
IFS=","
for PROJECT in $PROJECTS
do
PROJECT_HOME=$LOCAL_HOME/$PROJECT
WAR_NAME=${PROJECT}.war
echo "PROJECT is $PROJECT"
cp ${PROJECT}/target/${WAR_NAME} .
echo "Deploying '$WAR_NAME' to '$WILDFLY_MANAGEMENT_URL'"

echo '-------------------'
echo "-> Undeploy old war"
curl -sS -H "content-Type: application/json" -d '{"operation":"undeploy", "address":[{"deployment":"'"${WAR_NAME}"'"}]}' --digest ${WILDFLY_MANAGEMENT_URL}/management
echo ""

echo "-> Remove old war"
curl -sS -H "content-Type: application/json" -d '{"operation":"remove", "address":[{"deployment":"'"${WAR_NAME}"'"}]}' --digest ${WILDFLY_MANAGEMENT_URL}/management
echo ""

echo "-> Upload new war"
bytes_value=`curl -sF "file=@./${WAR_NAME}" --digest ${WILDFLY_MANAGEMENT_URL}/management/add-content | perl -pe 's/^.*"BYTES_VALUE"\s*:\s*"(.*)".*$/$1/'`
echo $bytes_value

json_string_start='{"content":[{"hash": {"BYTES_VALUE" : "'
json_string_end='"}}], "address": [{"deployment":"'"${WAR_NAME}"'"}], "operation":"add", "enabled":"true"}'
json_string="$json_string_start$bytes_value$json_string_end"

echo "-> Deploy new war"
result=`curl -sS -H "Content-Type: application/json" -d "$json_string" --digest ${WILDFLY_MANAGEMENT_URL}/management | perl -pe 's/^.*"outcome"\s*:\s*"(.*)".*$/$1/'`
echo $result

rm ${WAR_NAME}

if [ "$result" != "success" ]; then
  exit -1
fi
done