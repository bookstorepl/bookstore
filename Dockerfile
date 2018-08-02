# Start with a base image containing Java runtime
FROM openjdk:8-jdk-alpine

# Add Maintainer Info
MAINTAINER Pawel Wilkosz <pawel.wilkosz@gmail.com>

# Add a volume pointing to /tmp
VOLUME /tmp

# Make port 8080 available to the world outside this container
EXPOSE 8080

# The application's jar file
ARG JAR_FILE=out/artifacts/products_demo_jar/products-demo.jar

# Add the application's jar to the container
ADD ${JAR_FILE} products-demo.jar

EXPOSE 5432

# Run the jar file
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/products-demo.jar"]