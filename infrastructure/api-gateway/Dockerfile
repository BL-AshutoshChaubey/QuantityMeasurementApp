# Build stage
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app

# Copy the entire repository to ensure all parent/child relationships are intact
COPY . .

# Build the specific module and its dependencies
RUN mvn clean package -pl infrastructure/api-gateway -am -DskipTests

# Run stage
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app
COPY --from=build /app/infrastructure/api-gateway/target/api-gateway.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]

