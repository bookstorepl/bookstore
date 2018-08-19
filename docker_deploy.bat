@echo off
set WIN_LOG_PATH="C:\dockers\logs\ApiGateway"
set DCKER_LOG_PATH="/var/log"
call mvn package
call mvn install dockerfile:build
call docker run --rm -d -p 8080:8080 -v %WIN_LOG_PATH%:%DCKER_LOG_PATH% products-demo/products-demo
echo "The program has completed"