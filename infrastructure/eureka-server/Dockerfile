# Build stage
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app

# Copy the entire repository
COPY . .

# Build the specific module and its dependencies
RUN mvn clean package -pl infrastructure/eureka-server -am -DskipTests

# Run stage
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app
COPY --from=build /app/infrastructure/eureka-server/target/eureka-server.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]


