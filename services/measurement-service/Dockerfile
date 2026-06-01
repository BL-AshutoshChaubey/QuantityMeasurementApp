# Build stage
FROM maven:3.8.5-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY services/measurement-service/pom.xml services/measurement-service/
RUN mvn -pl services/measurement-service dependency:go-offline

COPY services/measurement-service/src services/measurement-service/src
RUN mvn -pl services/measurement-service clean package -DskipTests

# Run stage
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app
COPY --from=build /app/services/measurement-service/target/*.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]
