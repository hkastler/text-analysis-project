if "%1"=="" (
    set profile=default
) else (
    set profile=%1
)

call mvn clean install -f ./pom.xml -P %profile% %2

call dockerBuildAndRun.bat
 
