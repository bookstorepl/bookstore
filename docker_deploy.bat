call mvn package
call mvn install dockerfile:build
call docker run --rm -d -p 8080:8080 -v C:\dockers\logs:/var/log products-demo/products-demo