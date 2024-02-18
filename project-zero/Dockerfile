FROM openjdk:17-jre-slim

RUN apt-get update && \
    apt-get install -y maven

WORKDIR /app

COPY ./src /app/src
COPY pom.xml /app/pom.xml

RUN mvn clean install
RUN mvn compile
RUN mvn package

CMD ["java", "-cp", "target/project-zero-1.0-SNAPSHOT.jar ut.app.App"]
