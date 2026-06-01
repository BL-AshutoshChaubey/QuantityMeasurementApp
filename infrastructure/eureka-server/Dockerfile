# Build stage
FROM maven:3.8.5-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY infrastructure/eureka-server/pom.xml infrastructure/eureka-server/
COPY infrastructure/api-gateway/pom.xml infrastructure/api-gateway/
COPY services/measurement-service/pom.xml services/measurement-service/
COPY services/identity-service/pom.xml services/identity-service/
RUN mvn -pl infrastructure/eureka-server dependency:go-offline

COPY infrastructure/eureka-server/src infrastructure/eureka-server/src
RUN mvn -pl infrastructure/eureka-server clean package -DskipTests

# Run stage
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app
COPY --from=build /app/infrastructure/eureka-server/target/*.jar app.jar
EXPOSE 8761
ENTRYPOINT ["java", "-jar", "app.jar"]
