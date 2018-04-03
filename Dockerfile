FROM openjdk:8-jre-alpine
WORKDIR /root/sttDB-server

ADD ./target/transcriptoma.db-*.jar ./app.jar

ENV FILES_DIR /root/server-files

EXPOSE 8080
CMD java -Dserver.port=8080 -Dspring.profiles.active=production -jar app.jar
