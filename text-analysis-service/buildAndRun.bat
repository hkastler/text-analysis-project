
call mvn clean install -f ../text-analysis/pom.xml %1
call mvn clean install -f ../text-analysis-twitter/pom.xml %1
call mvn clean install -f ../text-analysis-webclient/pom.xml %1
call mvn clean install %1
call dockerBuildAndRun.bat
 
