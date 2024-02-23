FROM maven:3.8.3-openjdk-17-slim AS build

WORKDIR /app

COPY . .

RUN mvn dependency:go-offline
RUN mvn package

FROM openjdk:17-jdk-slim

WORKDIR /app

COPY --from=build /app/target/messaging-jms-0.0.1-SNAPSHOT.jar ./app.jar

CMD ["java", "-jar", "app.jar"]
