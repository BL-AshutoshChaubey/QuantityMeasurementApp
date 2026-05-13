# Build stage
FROM maven:3.8.4-openjdk-17-slim AS build
WORKDIR /app
COPY pom.xml .
COPY infrastructure/api-gateway/pom.xml infrastructure/api-gateway/
RUN mvn -pl infrastructure/api-gateway dependency:go-offline

COPY infrastructure/api-gateway/src infrastructure/api-gateway/src
RUN mvn -pl infrastructure/api-gateway clean package -DskipTests

# Run stage
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/infrastructure/api-gateway/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
