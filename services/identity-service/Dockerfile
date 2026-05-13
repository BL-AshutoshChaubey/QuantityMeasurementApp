# Build stage
FROM maven:3.8.4-openjdk-17-slim AS build
WORKDIR /app
COPY pom.xml .
COPY services/identity-service/pom.xml services/identity-service/
RUN mvn -pl services/identity-service dependency:go-offline

COPY services/identity-service/src services/identity-service/src
RUN mvn -pl services/identity-service clean package -DskipTests

# Run stage
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/services/identity-service/target/*.jar app.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "app.jar"]
