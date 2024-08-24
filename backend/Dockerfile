
FROM maven:3.9.8-eclipse-temurin-21 AS build

WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src
 RUN mvn clean package


FROM openjdk:21-jdk-slim

WORKDIR /app


COPY --from=build /app/target/HotelEase-0.0.1-SNAPSHOT.jar .

EXPOSE 8080

# Update the ENTRYPOINT to match the new path if necessary
ENTRYPOINT ["java","-jar","HotelEase-0.0.1-SNAPSHOT.jar"]
