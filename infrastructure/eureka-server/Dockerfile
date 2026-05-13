# Build stage
FROM maven:3.8.4-openjdk-17-slim AS build
WORKDIR /app
COPY pom.xml .
COPY infrastructure/eureka-server/pom.xml infrastructure/eureka-server/
# Download dependencies for the specific module
RUN mvn -pl infrastructure/eureka-server dependency:go-offline

COPY infrastructure/eureka-server/src infrastructure/eureka-server/src
RUN mvn -pl infrastructure/eureka-server clean package -DskipTests

# Run stage
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/infrastructure/eureka-server/target/*.jar app.jar
EXPOSE 8761
ENTRYPOINT ["java", "-jar", "app.jar"]
