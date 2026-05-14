# Build stage - copy full multi-module project
FROM maven:3.8.4-openjdk-17-slim AS build
WORKDIR /app

# Copy all POM files first for dependency caching
COPY pom.xml .
COPY infrastructure/eureka-server/pom.xml infrastructure/eureka-server/
COPY infrastructure/api-gateway/pom.xml infrastructure/api-gateway/
COPY services/measurement-service/pom.xml services/measurement-service/
COPY services/identity-service/pom.xml services/identity-service/

# Download dependencies
RUN mvn dependency:go-offline -pl infrastructure/api-gateway -am

# Copy source code
COPY infrastructure/api-gateway/src infrastructure/api-gateway/src

# Build only this module (with parent resolution)
RUN mvn clean package -pl infrastructure/api-gateway -am -DskipTests

# Run stage
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/infrastructure/api-gateway/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
