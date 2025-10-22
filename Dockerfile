# Stage 1: Build the JavaFX app using Maven 3.9.6 and JDK 22
FROM maven:3.9.6-eclipse-temurin-22 AS build
WORKDIR /app

# Copy project files
COPY pom.xml .
COPY src ./src

# Build the jar
RUN mvn clean package -DskipTests

# Stage 2: Run the packaged app using JDK 22
FROM eclipse-temurin:22-jdk-alpine
WORKDIR /app

# Copy the jar from build stage
COPY --from=build /app/target/*.jar app.jar

# Expose port
EXPOSE 8080

# Run the jar
CMD ["java", "-jar", "app.jar"]
