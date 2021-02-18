FROM openjdk:8-alpine

LABEL maintainer="eduardo_gd@hotmail.com"

VOLUME /tmp

ADD target/fmrecontest-api*.jar recibir.jar
 
EXPOSE 8080

WORKDIR /

ENTRYPOINT ["java","-jar", "/recibir.jar"]
