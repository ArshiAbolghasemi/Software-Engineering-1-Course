FROM maven:3.8.3-openjdk-17-slim AS build

WORKDIR /app

COPY . .

RUN mvn dependency:go-offline
RUN mvn package

ENV JAR_FILE=messaging-jms-0.0.1-SNAPSHOT.jar

FROM openjdk:17-jdk-slim

WORKDIR /app

COPY --from=build /app/target/${JAR_FILE} .

CMD ["java", "-jar", "${JAR_FILE}"]
