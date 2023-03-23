#
# Package stage
#
FROM openjdk:11-jre-slim
COPY ./target/*-SNAPSHOT.jar /home/app/server.jar
ENTRYPOINT ["java","-jar","/home/app/server.jar"]