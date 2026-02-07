FROM openjdk:11.0.16-jdk

LABEL maintainer="eduardo_gd@hotmail.com"

VOLUME /tmp

ADD target/fmrecontest-recibir*.jar recibir.jar

WORKDIR /

ENTRYPOINT ["java","-jar", "/recibir.jar"]
