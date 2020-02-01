FROM openjdk:8-jdk-alpine
LABEL maintainer="eduardo_gd@hotmail.com"
VOLUME /tmp
#EXPOSE 8080
ARG JAR_FILE=fmrecontest-recibir/target/recibir*.jar
ADD ${JAR_FILE} recibir.jar

CMD java -v
ARG MYSQL_DB_URL=$MYSQL_DB_URL
CMD echo ${MYSQL_DB_URL}

ENTRYPOINT ["java", "-jar", "/recibir.jar"]
