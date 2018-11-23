if "%2"=="" (
    set webclient=webclient-angular
) else (
    set webclient=%2
)
call mvn clean install -f ../text-analysis/pom.xml %1
call mvn clean install -f ../text-analysis-twitter/pom.xml %1
call mvn clean install -f ../%webclient%/pom.xml %1
call mvn clean install -f pom.xml -P %webclient%  %1 
call dockerBuildAndRun.bat
 
