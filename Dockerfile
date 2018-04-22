FROM maven:3.5-alpine as build
COPY ./ ./
RUN mvn clean
RUN mvn install -DskipTests


FROM openjdk:8-jre-alpine
WORKDIR /root/sttDB-server
ENV FILES_DIR /root/server-files
EXPOSE 8080

COPY --from=build ./target/transcriptoma.db-*.jar ./app.jar
CMD java -Dserver.port=8080 -Dspring.profiles.active=production -jar app.jar
