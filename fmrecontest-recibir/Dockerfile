FROM openjdk:8-alpine

LABEL maintainer="eduardo_gd@hotmail.com"

VOLUME /tmp

ADD target/fmrecontest-recibir*.jar recibir.jar

WORKDIR /

ENTRYPOINT ["java","-jar", "/recibir.jar"]
