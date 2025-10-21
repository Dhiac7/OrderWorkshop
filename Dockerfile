# Stage 1: Build the JavaFX app using Maven 3.8.7 and JDK 22
FROM 3.9.11-eclipse-temurin-11-alpine AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Run the packaged app using JDK 22
FROM eclipse-temurin:22-jdk
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
